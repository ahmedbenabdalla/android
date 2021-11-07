package tn.esprit.restaurantcommand.ui.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;

import tn.esprit.restaurantcommand.R;

public class IntroActvity extends AppCompatActivity {

    private ConstraintLayout startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_actvity);
        startBtn=findViewById(R.id.startbtn);
        startBtn.setOnClickListener(v->{
            startActivity(new Intent(IntroActvity.this,SignInActivity.class));
        });
    }
}