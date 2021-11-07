package tn.esprit.restaurantcommand.data.service;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import tn.esprit.restaurantcommand.data.models.User;


public interface UserService {

    @POST("/user/login")
    public Call<User> executeSignIn(@Body HashMap<String,String> map);

    @POST("/user/register")
    public Call<User> executeSignUp(@Body HashMap<String,String> map);

    @GET("/user/getUser")
    public Call<User> getUser(@Header("Authorization") String auth);


}
