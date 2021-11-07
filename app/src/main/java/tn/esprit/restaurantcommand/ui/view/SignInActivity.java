package tn.esprit.restaurantcommand.ui.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tn.esprit.restaurantcommand.R;
import tn.esprit.restaurantcommand.data.api.ApiClient;
import tn.esprit.restaurantcommand.data.models.User;
import tn.esprit.restaurantcommand.data.utils.AppConstant;
import tn.esprit.restaurantcommand.data.utils.PreferencesUtils;
import tn.esprit.restaurantcommand.ui.view.admin.DashboardActivity;
import tn.esprit.restaurantcommand.ui.view.client.HomeActivity;

public class SignInActivity extends AppCompatActivity {

    TextInputLayout email, password;
    Button callSignIn,callSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        callSignIn=findViewById(R.id.buttonSignInUser);
        email=findViewById(R.id.emailUserSignIn);
        password=findViewById(R.id.passwordUserSignIn);

        callSignUp=findViewById(R.id.buttonSignUpUser);
        if(PreferencesUtils.getSaved(SignInActivity.this, AppConstant.TOKEN)!=null)
        {
            Call<User> call= ApiClient.getInstance().getUserService().getUser(PreferencesUtils.getSaved(SignInActivity.this,AppConstant.TOKEN));
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    Toast.makeText(SignInActivity.this, "sign in ", Toast.LENGTH_SHORT).show();
                    if(response.body().getRole().equals("client"))
                    {
                        startActivity(new Intent(SignInActivity.this, HomeActivity.class));
                    }else{
                        startActivity(new Intent(SignInActivity.this, DashboardActivity.class));
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });
            //startActivity(new Intent(SignInActivity.this,GetRestaurantActivity.class));
        }else {
            callSignIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HashMap<String, String> map = new HashMap<>();
                    String mail = email.getEditText().getText().toString();
                    String pwd = password.getEditText().getText().toString();
                    map.put("email", mail);
                    map.put("password", pwd);

                    Call<User> call = ApiClient.getInstance().getUserService().executeSignIn(map);
                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            Toast.makeText(getApplicationContext(),response.body().get_id(),Toast.LENGTH_LONG).show();
                                PreferencesUtils.save(AppConstant.UserID, String.valueOf(response.body().get_id()), getApplicationContext());
                                PreferencesUtils.save(AppConstant.KEY_EMAIL, mail, getApplicationContext());
                                PreferencesUtils.save(AppConstant.KEY_PASSWORD, pwd, getApplicationContext());
                                PreferencesUtils.save(AppConstant.TOKEN, response.body().getToken(), getApplicationContext());
                                if(response.body().getRole().equals("client"))
                                {
                                    startActivity(new Intent(SignInActivity.this, HomeActivity.class));
                                }else {
                                    startActivity(new Intent(SignInActivity.this, DashboardActivity.class));
                                }

                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast.makeText(SignInActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
        }

        callSignUp.setOnClickListener(v->{
            startActivity(new Intent(SignInActivity.this,SignUpActivity.class));
        });
    }
}