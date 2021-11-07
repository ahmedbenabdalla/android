package tn.esprit.restaurantcommand.data.service;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import tn.esprit.restaurantcommand.data.models.Restaurant;

public interface RestaurantService {
    @GET("/restaurant/getRestaurants")
    public Call<List<Restaurant>> getRestaurants(@Header("Authorization") String auth);
    @DELETE("/restaurant/deleteRestaurant/{id}")
    public Call<String> deleteRestaurant(@Header("Authorization") String auth,@Path("id")  String id);
    @POST("/restaurant/addRestaurant")
    public Call<Restaurant> addRestaurant(@Header("Authorization") String auth , @Body HashMap<String,String> map);
    @PUT("/restaurant/editRestaurant/{id}")
    public Call<Restaurant> editRestaurant(@Header("Authorization") String auth,@Body HashMap<String,String> map,@Path("id") String id);
}
