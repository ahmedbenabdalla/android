package tn.esprit.restaurantcommand.data.service;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import tn.esprit.restaurantcommand.data.models.Order;

public interface OrderService {

    @POST("/order/postOrder")
    public Call<Order> makeOrder(@Header("Authorization") String auth ,@Body HashMap<String,Object> map);
}
