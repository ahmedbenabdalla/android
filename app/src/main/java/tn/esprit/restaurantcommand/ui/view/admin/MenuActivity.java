package tn.esprit.restaurantcommand.ui.view.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tn.esprit.restaurantcommand.R;
import tn.esprit.restaurantcommand.data.api.ApiClient;
import tn.esprit.restaurantcommand.data.models.Dish;
import tn.esprit.restaurantcommand.data.utils.AppConstant;
import tn.esprit.restaurantcommand.data.utils.PreferencesUtils;
import tn.esprit.restaurantcommand.ui.adapters.MenuAdapter;


public class MenuActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton addDish;
    static String idRes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Bundle bundle=getIntent().getExtras();
        try {
            idRes = bundle.get("resId").toString();
            Toast.makeText(this, idRes, Toast.LENGTH_LONG).show();
        }catch (NullPointerException e)
        {

        }
        Toast.makeText(this, "res id"+idRes, Toast.LENGTH_LONG).show();
        recyclerView=findViewById(R.id.menuRecyclerView);
        addDish=findViewById(R.id.addDishfloat);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if(dy>0)
                {
                    addDish.hide();
                }
                else {
                    addDish.show();
                }
            }
        });
        addDish.setOnClickListener(v->{
            startActivity(new Intent(MenuActivity.this,AddDishActivity.class).putExtra("resId",idRes));
        });
        processData();
    }


    private void processData() {
        Call<List<Dish>> call= ApiClient.getInstance().getDishService().getDishes(PreferencesUtils.getSaved(this, AppConstant.TOKEN),idRes);
        call.enqueue(new Callback<List<Dish>>() {
            @Override
            public void onResponse(Call<List<Dish>> call, Response<List<Dish>> response) {
                List<Dish> data=response.body();
                MenuAdapter menuAdapter=new MenuAdapter(data,MenuActivity.this);
                recyclerView.setAdapter(menuAdapter);
            }

            @Override
            public void onFailure(Call<List<Dish>> call, Throwable t) {
                Toast.makeText(MenuActivity.this,"invalid ",Toast.LENGTH_LONG).show();
            }
        });
    }


}
