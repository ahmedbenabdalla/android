package tn.esprit.restaurantcommand.ui.view.client.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;


import tn.esprit.restaurantcommand.R;
import tn.esprit.restaurantcommand.data.models.Cart;
import tn.esprit.restaurantcommand.data.models.Dish;
import tn.esprit.restaurantcommand.data.service.dao.CartDatabase;
import tn.esprit.restaurantcommand.data.service.dao.ViewCart;
import tn.esprit.restaurantcommand.data.utils.AppConstant;
import tn.esprit.restaurantcommand.data.utils.PreferencesUtils;


public class ShowDetailFragment extends Fragment {
    private TextView addToCardBtn;
    private TextView titleTxt, priceTxt, descriptionTxt, numberOrderTxt;
    private ImageView plusBtn, minusBtn, picFood;
    private Dish dish;
    int numberOrder=1;
    ViewCart viewCart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_detail, container, false);
        // Inflate the layout for this fragment

        initView(view);
        getBundle(view);
        return view;

    }

    private void getBundle(View view) {
        dish =this.getArguments().getParcelable("Dish");

        String imageUrl = dish.getImageUrl();

        Glide.with(this)
                .load(imageUrl)
                .into(picFood);
        titleTxt.setText(dish.getTitle());
        priceTxt.setText("$" + dish.getPrice());
        descriptionTxt.setText(dish.getDescription());
        numberOrderTxt.setText(String.valueOf(numberOrder));

        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberOrder = numberOrder + 1;
                numberOrderTxt.setText(String.valueOf(numberOrder));
            }
        });

        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOrder > 1) {
                    numberOrder = numberOrder - 1;
                }
                numberOrderTxt.setText(String.valueOf(numberOrder));
            }
        });

        addToCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), String.valueOf(numberOrder), Toast.LENGTH_SHORT).show();
                int quantity=Integer.valueOf((String) numberOrderTxt.getText());
                viewCart.insert(new Cart(PreferencesUtils.getSaved(getContext(),AppConstant.UserID),dish.get_id(),dish.getTitle(),quantity,dish.getPrice(), dish.getImageUrl()));
            }
        });
    }

    private void initView(View view) {
        addToCardBtn = view.findViewById(R.id.addToCardBtn);
        titleTxt = view.findViewById(R.id.titleTxt);
        priceTxt = view.findViewById(R.id.priceTxt);
        descriptionTxt = view.findViewById(R.id.descriptionTxt);
        numberOrderTxt = view.findViewById(R.id.numberOrderTxt);
        plusBtn = view.findViewById(R.id.plusBtn);
        minusBtn = view.findViewById(R.id.minusBtn);
        picFood = view.findViewById(R.id.foodPic);
        viewCart = ViewModelProviders.of(this).get(ViewCart.class);

    }

}