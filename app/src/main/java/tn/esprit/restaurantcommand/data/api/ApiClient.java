package tn.esprit.restaurantcommand.data.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import tn.esprit.restaurantcommand.data.service.DishService;
import tn.esprit.restaurantcommand.data.service.OrderService;
import tn.esprit.restaurantcommand.data.service.RestaurantService;
import tn.esprit.restaurantcommand.data.service.UserService;

public class ApiClient {
    private static final String BASE_URL="http://10.0.2.2:5000/";
    private static ApiClient clientobject;
    private static Retrofit retrofit;

    public ApiClient() {
        retrofit=new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static synchronized ApiClient getInstance(){
        if(clientobject==null)
            clientobject=new ApiClient();
        return clientobject;
    }
    public RestaurantService getRestaurantRepo(){
        return retrofit.create(RestaurantService.class);
    }

    public UserService getUserService()
    {
        return retrofit.create(UserService.class);
    }

    public DishService getDishService(){return  retrofit.create(DishService.class);}

    public OrderService getOrderService(){return  retrofit.create(OrderService.class);}
}
