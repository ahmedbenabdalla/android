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
import tn.esprit.restaurantcommand.data.models.Restaurant;
import tn.esprit.restaurantcommand.data.utils.AppConstant;
import tn.esprit.restaurantcommand.data.utils.PreferencesUtils;
import tn.esprit.restaurantcommand.data.utils.Uploader;

public class AddRestaurantActivity extends AppCompatActivity {
    ImageView resImage;
    Restaurant resToEdit=null;
    TextInputLayout address,resName;
    Button save,cancel;
    Uploader uploader=new Uploader(AddRestaurantActivity.this,AddRestaurantActivity.this);
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //binding
        setContentView(R.layout.activity_add_restaurant);
        resImage = findViewById(R.id.uploadImage);
        address = findViewById(R.id.restauratAddress);
        resName = findViewById(R.id.restaurantName);
        save = findViewById(R.id.buttonSave);
        cancel = findViewById(R.id.buttonCancel);

        Uploader.Image=resImage;
        //config the access to cloudinary
        try {
            uploader.initConfig(this);
        }catch (IllegalStateException e)
        {

        }
        //to open callery the Gallery and choose image
        resImage.setOnClickListener(view -> {
            uploader.requestPermission();

        });
        try {
            resToEdit=getIntent().getParcelableExtra("resToEdit");
            address.getEditText().setText(resToEdit.getAddress());
            resName.getEditText().setText(resToEdit.getName());
            String image=resToEdit.getImageUrl();
            Glide.with(this).load(image).into(resImage);
        }catch (NullPointerException e)
        {

        }



        //to cancel add
        cancel.setOnClickListener(v -> {
            startActivity(new Intent(AddRestaurantActivity.this, RestaurantsActivity.class));
        });
        //to save data
        save.setOnClickListener(v -> {
            //Thread.sleep(5000);
            HashMap<String, String> map = new HashMap<>();
            map.put("name", resName.getEditText().getText().toString());
            map.put("address", address.getEditText().getText().toString());
            android.content.SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
            map.put("imageUrl", prefs.getString("imageURL",null));
            Call<Restaurant> call;
            if(resToEdit!=null){
                call= ApiClient.getInstance().getRestaurantRepo().editRestaurant(PreferencesUtils.getSaved(this, AppConstant.TOKEN),map,resToEdit.getId());
            }else{
                call= ApiClient.getInstance().getRestaurantRepo().addRestaurant(PreferencesUtils.getSaved(this, AppConstant.TOKEN), map);
            }
            call.enqueue(new Callback<Restaurant>() {
                @Override
                public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {
                    startActivity(new Intent(AddRestaurantActivity.this, RestaurantsActivity.class));
                }
                @Override
                public void onFailure(Call<Restaurant> call, Throwable t) {
                    Toast.makeText(AddRestaurantActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });
    }
    @Override
    protected void onActivityResult(int requestcode,int resultcode,Intent data) {
        super.onActivityResult(requestcode, resultcode,data);
        Toast.makeText(this,"from uploader",Toast.LENGTH_LONG).show();
        uploader.setImagePath(data.getData());
        Glide.with(this).load(data.getData()).into(resImage);

    }
}