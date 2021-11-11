package tn.esprit.restaurantcommand.ui.view.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import tn.esprit.restaurantcommand.R;
import tn.esprit.restaurantcommand.data.utils.AppConstant;
import tn.esprit.restaurantcommand.data.utils.PreferencesUtils;
import tn.esprit.restaurantcommand.ui.view.IntroActvity;
import tn.esprit.restaurantcommand.ui.view.client.HomeActivity;

public class DashboardActivity extends AppCompatActivity {

    ImageView GoRes,logout,orders;
    TextView hi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        initView();
    }

    private void initView() {
        hi=findViewById(R.id.hi);
        Bundle extra=getIntent().getExtras();
        if(extra!=null)
        {
            String userName= String.valueOf(extra.get("userName"));
            hi.setText(userName);
        }
        GoRes=findViewById(R.id.GoRes);
        logout=findViewById(R.id.imagelogOut);
        orders=findViewById(R.id.ordersImage);
        GoRes.setOnClickListener(view -> {
            startActivity(new Intent(DashboardActivity.this,RestaurantsActivity.class));
        });
        logout.setOnClickListener(v->{
            PreferencesUtils.remove(AppConstant.TOKEN, DashboardActivity.this);
            PreferencesUtils.remove(AppConstant.UserID,DashboardActivity.this);
            PreferencesUtils.remove(AppConstant.KEY_EMAIL,DashboardActivity.this);
            PreferencesUtils.remove(AppConstant.KEY_PASSWORD,DashboardActivity.this);
            startActivity(new Intent(DashboardActivity.this, IntroActvity.class));
        });

    }
}
