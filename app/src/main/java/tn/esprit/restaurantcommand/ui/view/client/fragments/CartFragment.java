package tn.esprit.restaurantcommand.ui.view.client.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tn.esprit.restaurantcommand.R;
import tn.esprit.restaurantcommand.data.api.ApiClient;
import tn.esprit.restaurantcommand.data.models.Cart;
import tn.esprit.restaurantcommand.data.models.Item;
import tn.esprit.restaurantcommand.data.models.Order;
import tn.esprit.restaurantcommand.data.service.dao.ViewCart;
import tn.esprit.restaurantcommand.data.utils.AppConstant;
import tn.esprit.restaurantcommand.data.utils.PreferencesUtils;
import tn.esprit.restaurantcommand.ui.adapters.CartClientAdapter;

public class CartFragment extends Fragment {

    private CartClientAdapter adapter;
    private RecyclerView recyclerViewList;
    private TextView totalTxt,ConfirmOrder;
    private ScrollView scrollView;
    ViewCart viewCart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_cart, container, false);
        initView(view);
        initList(view);
        return view;
    }
    private void initView(View view) {
        recyclerViewList = view.findViewById(R.id.recyclerview);
        totalTxt = view.findViewById(R.id.totalTxt);
        scrollView = view.findViewById(R.id.scrollView4);
        ConfirmOrder=view.findViewById(R.id.ConfirmOrder);
    }
    private void initList(View view) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewList.findViewById(R.id.recyclerview);
        recyclerViewList.setLayoutManager(linearLayoutManager);
        adapter = new CartClientAdapter(this.getContext());
        //setting adapter class for recycler view.
        recyclerViewList.setAdapter(adapter);
        //passing a data from view modal.
        viewCart = ViewModelProviders.of(this).get(ViewCart.class);
        //below line is use to get all the courses from view modal.
        viewCart.getAllCarts().observe(this.getActivity(), new Observer<List<Cart>>() {
            @Override
            public void onChanged(List<Cart> models) {
                //when the data is changed in our models we are adding that list to our adapter class.
                adapter.submitList(models);
                Double total=0.0;
                for (Cart cart : models) {
                    total += Double.valueOf(cart.getPrice());
                }
                totalTxt.setText(String.valueOf(total));
            }
        });
        ConfirmOrder.setOnClickListener(v-> {

            HashMap<String, Object> order = new HashMap<>();
            Double total = 0.0;
            List<HashMap<String, String>> items = new ArrayList<>();
            Double tot = 0.0;
            viewCart = ViewModelProviders.of(this).get(ViewCart.class);
            LiveData<List<Cart>> models=viewCart.getAllCarts();
            for (Cart cart : models.getValue()) {
                tot += Double.valueOf(cart.getPrice());
                HashMap<String, String> map = new HashMap<>();
                map.put("dishId", cart.getDishId());
                map.put("name", cart.getName());
                map.put("quantity", String.valueOf(cart.getQuantity()));
                map.put("price", cart.getPrice());
                items.add(map);
            }
            order.put("items", items);
            order.put("userId", PreferencesUtils.getSaved(getContext(), AppConstant.UserID));
            order.put("bill", String.valueOf(tot));
            Call<Order> call = ApiClient.getInstance().getOrderService().makeOrder(PreferencesUtils.getSaved(getContext(), AppConstant.TOKEN), order);
            call.enqueue(new Callback<Order>() {
                @Override
                public void onResponse(Call<Order> call, Response<Order> response) {
                    Toast.makeText(getContext(), "success", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<Order> call, Throwable t) {
                    Toast.makeText(getContext(), "fail", Toast.LENGTH_LONG).show();
                }
            });
        });


    }


}