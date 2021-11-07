package tn.esprit.restaurantcommand.data.service.dao;

import android.app.Application;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import java.util.List;

import tn.esprit.restaurantcommand.data.models.Cart;
import tn.esprit.restaurantcommand.data.utils.AppConstant;
import tn.esprit.restaurantcommand.data.utils.PreferencesUtils;

public class CartRepository {

    private CartDAO cartDAO;
    private LiveData<List<Cart>> allCart;
    private String userId;

    //creating a constructor for our variables and passing the variables to it.
    public CartRepository(Application application) {
        CartDatabase database = CartDatabase.getInstance(application);
        cartDAO = database.cartDAO();
        Toast.makeText(application,"test"+PreferencesUtils.getSaved(application, AppConstant.UserID) , Toast.LENGTH_SHORT).show();
        allCart = cartDAO.getAllCarts( PreferencesUtils.getSaved(application, AppConstant.UserID));
    }
    //creating a method to insert the data to our database.
    public void insert(Cart model) {
        new InsertCartAsyncTask(cartDAO).execute(model);
    }

    //creating a method to update data in database.
    public void update(Cart model) {
        new UpdateCartAsyncTask(cartDAO).execute(model);
    }

    //creating a method to delete the data in our database.
    public void delete(Cart model) {
        new DeleteCartAsyncTask(cartDAO).execute(model);
    }

    //below is the method to delete all the courses.
    public void deleteAllCarts() {
        new DeleteAllCartsAsyncTask(cartDAO,userId).execute();
    }

    //below method is to read all the courses.
    public LiveData<List<Cart>> getAllCarts() {
        return allCart;
    }

    //we are creating a async task method to insert new course.
    private static class InsertCartAsyncTask extends AsyncTask<Cart, Void, Void> {
        private CartDAO cartDAO;

        private InsertCartAsyncTask(CartDAO cartDAO) {
            this.cartDAO = cartDAO;

        }

        @Override
        protected Void doInBackground(Cart... model) {
            //below line is use to insert our modal in dao.
            cartDAO.insert(model[0]);
            return null;
        }
    }

    //we are creating a async task method to update our course.
    private static class UpdateCartAsyncTask extends AsyncTask<Cart, Void, Void> {
        private CartDAO cartDAO;

        private UpdateCartAsyncTask(CartDAO cartDAO) {
            this.cartDAO = cartDAO;
        }

        @Override
        protected Void doInBackground(Cart... models) {
            //below line is use to update our modal in dao.
            cartDAO.update(models[0]);
            return null;
        }
    }

    //we are creating a async task method to delete course.
    private static class DeleteCartAsyncTask extends AsyncTask<Cart, Void, Void> {
        private CartDAO cartDAO;

        private DeleteCartAsyncTask(CartDAO cartDAO) {
            this.cartDAO = cartDAO;
        }

        @Override
        protected Void doInBackground(Cart... models) {
            //below line is use to delete our course modal in dao.
            cartDAO.delete(models[0]);
            return null;
        }
    }

    //we are creating a async task method to delete all courses.
    private static class DeleteAllCartsAsyncTask extends AsyncTask<Void, Void, Void> {
        private CartDAO cartDAO;
        private String userId;

        private DeleteAllCartsAsyncTask(CartDAO cartDAO,String userId) {
            this.cartDAO = cartDAO;
            this.userId=userId;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //on below line calling method to delete all courses.
            cartDAO.deleteAllCarts(userId);
            return null;
        }
    }
}
