package tn.esprit.restaurantcommand.ui.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tn.esprit.restaurantcommand.R;
import tn.esprit.restaurantcommand.data.api.ApiClient;
import tn.esprit.restaurantcommand.data.models.User;
import tn.esprit.restaurantcommand.data.utils.AppConstant;

public class SignUpActivity extends AppCompatActivity {
    TextInputLayout firstName,lastName,email,phone,password;
    RadioGroup grp;
    RadioButton role;
    Button signUp,buttonLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        buttonLogin=findViewById(R.id.buttonLogin);
        firstName=findViewById(R.id.firstNameUser);
        lastName=findViewById(R.id.lastNameUser);
        email=findViewById(R.id.emailUser);
        phone=findViewById(R.id.phoneUser);
        password=findViewById(R.id.passwordUser);
        grp=findViewById(R.id.grp);
        signUp=findViewById(R.id.buttonRegister);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int r = grp.getCheckedRadioButtonId();
                role = findViewById(r);
                //role.getText().toString()
                HashMap<String, String> map = new HashMap<>();
                String fName = firstName.getEditText().getText().toString();
                String lName = lastName.getEditText().getText().toString();
                String tel = phone.getEditText().getText().toString();
                String adMail = email.getEditText().getText().toString();
                String pwd = password.getEditText().getText().toString();
                map.put("firstName", fName);
                map.put("lastName", lName);
                map.put("phone", tel);
                map.put("email", adMail);
                map.put("password", pwd);
                map.put("role", role.getText().toString().isEmpty()?null:role.getText().toString());
                if(validator(map))
                {
                    Call<User> call = ApiClient.getInstance().getUserService().executeSignUp(map);
                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (response.code() == 200)
                                Toast.makeText(SignUpActivity.this, "success", Toast.LENGTH_LONG).show();
                            else if (response.code() == 500) {
                                Toast.makeText(SignUpActivity.this, "Internal server error", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(SignUpActivity.this, "email already exist", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast.makeText(SignUpActivity.this, "failure", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this,SignInActivity.class));
            }
        });
    }
    public boolean validator(Map map) {
        if (map.get("firstName").toString().trim().isEmpty()) {
            alert("first name field is required");
            return false;
        }else if(map.get("lastName").toString().trim().isEmpty()) {
            alert("last name field is required");
            return false;
//        } else if(AppConstant.VALID_EMAIL_ADDRESS_REGEX.matcher(map.get("email").toString()).find()) {
//            alert("email field should be a valid email");
//            return false;
//        }
        }else if(map.get("role").toString().trim().isEmpty()) {
            alert("select your role please");
            return false;
        }else if(map.get("password").toString().trim().length()<6) {
            alert("your password should be at least 6 characters");
            return false;
        }
        return true;
    }
    private void alert(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert!!!")
                .setMessage(msg)
                .setCancelable(true)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).show();

    }
}