package tn.esprit.restaurantcommand.ui.view.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tn.esprit.restaurantcommand.R;
import tn.esprit.restaurantcommand.data.api.ApiClient;
import tn.esprit.restaurantcommand.data.models.Dish;
import tn.esprit.restaurantcommand.data.utils.AppConstant;
import tn.esprit.restaurantcommand.data.utils.PreferencesUtils;
import tn.esprit.restaurantcommand.data.utils.Uploader;

public class AddDishActivity extends AppCompatActivity {
    static String resId;
    Dish dishToEdit=null;
    ImageView dishImage;
    TextInputLayout title,cookingTime,price,description;
    Button save,cancel;
    Uploader uploader=new Uploader(AddDishActivity.this,AddDishActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dish);
        try {
            Bundle bundle=getIntent().getExtras();
            resId=bundle.get("resId").toString();
        }catch (NullPointerException e){

        }





        title=findViewById(R.id.titleDish);
        cookingTime=findViewById(R.id.cookDishTime);
        price=findViewById(R.id.priceDish);
        description=findViewById(R.id.descriptionDish);
        save=findViewById(R.id.buttonSaveDish);
        cancel=findViewById(R.id.buttonCancelDish);
        dishImage=findViewById(R.id.uploadImageDish);
        Uploader.Image=dishImage;

        try {
            dishToEdit=getIntent().getParcelableExtra("dishToEdit");
            title.getEditText().setText(dishToEdit.getTitle());
            cookingTime.getEditText().setText(dishToEdit.getCookingTime());
            price.getEditText().setText(dishToEdit.getPrice());
            description.getEditText().setText(dishToEdit.getDescription());
            String image=dishToEdit.getImageUrl();
            Glide.with(this).load(image).into(dishImage);
        }catch (NullPointerException e)
        {

        }
        //config the access to cloudinary
        try {
            uploader.initConfig(this);
        }catch (IllegalStateException e)
        { }
        //to open callery the Gallery and choose image
        dishImage.setOnClickListener(view -> {
            uploader.requestPermission();
        });
        //to cancel add
        cancel.setOnClickListener(v -> {
            finish();
        });


        //to save data
        save.setOnClickListener(v -> {
            //Thread.sleep(5000);
            HashMap<String, String> map = new HashMap<>();
            map.put("title", title.getEditText().getText().toString());
            android.content.SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
            map.put("imageUrl", prefs.getString("imageURL",null));
            map.put("isAvaible", "false");
            map.put("cookingTime", cookingTime.getEditText().getText().toString());
            map.put("price",price.getEditText().getText().toString());
            map.put("description",description.getEditText().getText().toString());
            map.put("restaurantId",resId);
            Call<Dish> call;
            if(dishToEdit!=null)
            {
                call = ApiClient.getInstance().getDishService().updateDish(PreferencesUtils.getSaved(this, AppConstant.TOKEN),map,dishToEdit.get_id());

            }else {
                call = ApiClient.getInstance().getDishService().addDish(PreferencesUtils.getSaved(this, AppConstant.TOKEN), map);
            }
            call.enqueue(new Callback<Dish>() {
                @Override
                public void onResponse(Call<Dish> call, Response<Dish> response) {
                    finish();
                }
                @Override
                public void onFailure(Call<Dish> call, Throwable t) {
                    Toast.makeText(AddDishActivity.this,"Failed",Toast.LENGTH_LONG).show();
                }
            });
        });


    }
    @Override
    protected void onActivityResult(int requestcode,int resultcode,Intent data) {
        super.onActivityResult(requestcode, resultcode,data);
        Toast.makeText(this,"from uploader",Toast.LENGTH_LONG).show();
        uploader.setImagePath(data.getData());
        Glide.with(this).load(data.getData()).into(dishImage);

    }


}