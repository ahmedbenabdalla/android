package tn.esprit.restaurantcommand.ui.view.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import tn.esprit.restaurantcommand.R;
import tn.esprit.restaurantcommand.data.utils.AppConstant;
import tn.esprit.restaurantcommand.data.utils.PreferencesUtils;
import tn.esprit.restaurantcommand.ui.view.IntroActvity;
import tn.esprit.restaurantcommand.ui.view.client.fragments.CartFragment;
import tn.esprit.restaurantcommand.ui.view.client.fragments.HomeFragment;

public class HomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        FrameLayout frag =findViewById(R.id.frag);
        getSupportFragmentManager().beginTransaction().replace(R.id.frag,new HomeFragment()).commit();
        bottomNavigation();
        Toast.makeText(HomeActivity.this, "Home", Toast.LENGTH_SHORT).show();
    }

    private void bottomNavigation() {
        FloatingActionButton floatingActionButton = findViewById(R.id.card_btn);
        LinearLayout homeBtn = findViewById(R.id.homeBtn);
        ImageView logoutBtn=findViewById(R.id.logoutBtn);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frag,new CartFragment()).commit();

            }
        });
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frag,new HomeFragment()).commit();

            }
        });
        logoutBtn.setOnClickListener(v->{
            PreferencesUtils.remove(AppConstant.TOKEN,HomeActivity.this);
            PreferencesUtils.remove(AppConstant.UserID,HomeActivity.this);
            PreferencesUtils.remove(AppConstant.KEY_EMAIL,HomeActivity.this);
            PreferencesUtils.remove(AppConstant.KEY_PASSWORD,HomeActivity.this);
            startActivity(new Intent(HomeActivity.this, IntroActvity.class));
        });
    }
}