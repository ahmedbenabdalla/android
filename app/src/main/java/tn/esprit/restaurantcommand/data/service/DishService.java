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
import tn.esprit.restaurantcommand.data.models.Dish;

public interface DishService {
    @GET("/restaurant/dish/getDishs/{id}")
    public Call<List<Dish>> getDishes(@Header("Authorization") String auth, @Path("id") String id);
    @GET("/restaurant/dish/getAllDishs")
    public Call<List<Dish>> getAllDishes(@Header("Authorization") String auth);
    @POST("/restaurant/dish/addDish")
    public Call<Dish> addDish(@Header("Authorization") String auth , @Body HashMap<String,String> map);
    @DELETE("/restaurant/dish/deleteDish/{id}")
    public Call<String> deleteDish(@Header("Authorization") String auth,@Path("id")  String id);
    @PUT("/restaurant/dish/isAvaible/{id}")
    public Call<Dish> isAvaible(@Header("Authorization") String auth,@Path("id") String id);
    @PUT("/restaurant/dish/editDish/{id}")
    public Call<Dish> updateDish(@Header("Authorization") String auth,@Body HashMap<String,String> map,@Path("id") String id);

}
