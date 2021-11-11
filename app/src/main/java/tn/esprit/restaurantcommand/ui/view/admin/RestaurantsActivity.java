package tn.esprit.restaurantcommand.ui.view.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tn.esprit.restaurantcommand.R;
import tn.esprit.restaurantcommand.data.api.ApiClient;
import tn.esprit.restaurantcommand.data.models.Restaurant;
import tn.esprit.restaurantcommand.data.utils.AppConstant;
import tn.esprit.restaurantcommand.data.utils.PreferencesUtils;
import tn.esprit.restaurantcommand.ui.adapters.RestaurantAdapter;

public class RestaurantsActivity extends AppCompatActivity {


    FloatingActionButton addResfloat;
    RecyclerView recyclerView;
    Button dashBoard;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);
        initView();
        processData();
    }

    private void initView() {
        dashBoard=findViewById(R.id.buttonDashBoard);
        addResfloat=findViewById(R.id.addResfloat);
        recyclerView=findViewById(R.id.recyclerViewResAdmin);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        //start new AddRestaurantActivity
        dashBoard.setOnClickListener(view -> {
            startActivity(new Intent(RestaurantsActivity.this,DashboardActivity.class));
        });
        addResfloat.setOnClickListener(view -> {
            startActivity(new Intent(RestaurantsActivity.this,AddRestaurantActivity.class));
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if(dy>0)
                {
                    addResfloat.hide();
                }
                else {
                    addResfloat.show();
                }
            }
        });
    }
    public void processData()
    {
        Call<List<Restaurant>> call= ApiClient.getInstance().getRestaurantRepo().getRestaurants(PreferencesUtils.getSaved(this, AppConstant.TOKEN));
        call.enqueue(new Callback<List<Restaurant>>() {
            @Override
            public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
                List<Restaurant> data=response.body();
                RestaurantAdapter restaurantAdapter=new RestaurantAdapter(data,RestaurantsActivity.this);
                recyclerView.setAdapter(restaurantAdapter);
            }
            @Override
            public void onFailure(Call<List<Restaurant>> call, Throwable t) {
                Toast.makeText(RestaurantsActivity.this,"invalid ",Toast.LENGTH_LONG).show();
            }
        });
    }
}