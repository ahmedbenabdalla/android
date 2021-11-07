package tn.esprit.restaurantcommand.ui.view.client.fragments;



import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tn.esprit.restaurantcommand.R;
import tn.esprit.restaurantcommand.data.api.ApiClient;
import tn.esprit.restaurantcommand.data.models.Dish;
import tn.esprit.restaurantcommand.data.models.Restaurant;
import tn.esprit.restaurantcommand.data.models.User;
import tn.esprit.restaurantcommand.data.utils.AppConstant;
import tn.esprit.restaurantcommand.data.utils.PreferencesUtils;
import tn.esprit.restaurantcommand.ui.adapters.DishClientAdapter;
import tn.esprit.restaurantcommand.ui.adapters.RestaurantClientAdapter;



public class HomeFragment extends Fragment {

    RecyclerView recyclerViewRestaurantList,recyclerViewDishList;
    RecyclerView.Adapter adapter,adapterD;
    TextView userName;
    ImageView logoutBtn;
    public  HomeFragment()
    {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initView(view);
        recyclerViewRestaurant(view);
        recyclerViewDish(view);
        bottomNavigation(view);
        return view;
    }
    private void bottomNavigation(View view) {

    }

    private void initView(View view) {
        logoutBtn=view.findViewById(R.id.logoutBtn);
        userName=view.findViewById(R.id.userName);
        Call<User> call = ApiClient.getInstance().getUserService().getUser(PreferencesUtils.getSaved(getContext(), AppConstant.TOKEN));
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                String firstName=response.body().getFirstName();
                String lastName=response.body().getLastName();
                userName.setText("Hi "+firstName+" "+lastName);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
    private void recyclerViewDish(View view) {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerViewDishList=view.findViewById(R.id.recyclerViewDish);
        recyclerViewDishList.setLayoutManager(linearLayoutManager);
        List<Dish> dishes=new ArrayList<>();
        Call<List<Dish>> call= ApiClient.getInstance().getDishService().getAllDishes(PreferencesUtils.getSaved(getContext(), AppConstant.TOKEN));
        call.enqueue(new Callback<List<Dish>>() {
            @Override
            public void onResponse(Call<List<Dish>> call, Response<List<Dish>> response) {
                dishes.addAll(response.body());
                adapterD=new DishClientAdapter(dishes,getContext());
                recyclerViewDishList.setAdapter(adapterD);
            }

            @Override
            public void onFailure(Call<List<Dish>> call, Throwable t) {
                Toast.makeText(getContext(), "failed to load dishes", Toast.LENGTH_SHORT).show();


            }
        });


    }

    private void recyclerViewRestaurant(View view) {

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerViewRestaurantList=view.findViewById(R.id.recyclerViewRes);
        recyclerViewRestaurantList.setLayoutManager(linearLayoutManager);

        List<Restaurant> restaurants=new ArrayList<>();
        Call<List<Restaurant>> call= ApiClient.getInstance().getRestaurantRepo().getRestaurants(PreferencesUtils.getSaved(getContext(), AppConstant.TOKEN));
        call.enqueue(new Callback<List<Restaurant>>() {
            @Override
            public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
                restaurants.addAll(response.body());
                adapter=new RestaurantClientAdapter(restaurants);
                recyclerViewRestaurantList.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Restaurant>> call, Throwable t) {
                Toast.makeText(getContext(), "failed to load restaurants", Toast.LENGTH_SHORT).show();


            }
        });


    }

}