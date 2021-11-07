package tn.esprit.restaurantcommand.data.service.dao;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import tn.esprit.restaurantcommand.data.models.Cart;

public class ViewCart extends AndroidViewModel {

    //creating a new variable for course repository.
    private CartRepository repository;
    //below line is to create a variable for live data where all the courses are present.
    private LiveData<List<Cart>> allCarts;

    //constructor for our view modal.
    public ViewCart(@NonNull Application application) {
        super(application);
        repository = new CartRepository(application);
        allCarts = repository.getAllCarts();
    }


    //below method is use to insert the data to our repository.
    public void insert(Cart model) {
        repository.insert(model);
    }

    //below line is to update data in our repository.
    public void update(Cart model) {
        repository.update(model);
    }

    //below line is to delete the data in our repository.
    public void delete(Cart model) {
        repository.delete(model);
    }

    //below method is to delete all the courses in our list.
    public void deleteAllCarts() {
        repository.deleteAllCarts();
    }

    //below method is to get all the courses in our list.
    public LiveData<List<Cart>> getAllCarts() {
        return allCarts;
    }
}
