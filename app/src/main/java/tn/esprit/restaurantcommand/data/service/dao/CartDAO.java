package tn.esprit.restaurantcommand.data.service.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import tn.esprit.restaurantcommand.data.models.Cart;

@Dao
public interface CartDAO {


    @Insert
    void insert(Cart cart);

    @Query("select * from cart_table where user_id=:id")
    LiveData<List<Cart>>  getAllCarts(String id);

    @Update
    void update(Cart cart);

    //below line is use to delete a specific course in our database.
    @Delete
    void delete(Cart cart);

    //on below line we are making query to delete all courses from our databse.
    @Query("DELETE FROM cart_table where user_id=:id")
    void deleteAllCarts(String id);
}
