package tn.esprit.restaurantcommand.data.service.dao;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import tn.esprit.restaurantcommand.data.models.Cart;

@Database(entities = {Cart.class},version = 1, exportSchema = false)
public abstract class CartDatabase extends RoomDatabase {
    private static CartDatabase INSTANCE;
    public abstract CartDAO cartDAO();

    public static synchronized CartDatabase getInstance(Context context) {
        //below line is to check if the instance is null or not.
        if (INSTANCE == null) {
            //if the instance is null we are creating a new instance
            INSTANCE =
                    //for creating a instance for our database we are creating a database builder and passing our database class with our database name.
                    Room.databaseBuilder(context.getApplicationContext(),
                            CartDatabase.class, "restaurant_command")
                            //below line is use to add fall back to destructive migration to our database.
                            .fallbackToDestructiveMigration()
                            //below line is to add callback to our database.
                            .addCallback(roomCallback)
                            //below line is to build our database.
                            .build();
        }
        //after creating an instance we are returning our instance
        return INSTANCE;
    }

    //below line is to create a callback for our room database.
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            //this method is called when database is created and below line is to populate our data.
            new PopulateDbAsyncTask(INSTANCE).execute();
        }
    };

    //we are creating an async task class to perform task in background.
    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        PopulateDbAsyncTask(CartDatabase INSTANCE) {
            CartDAO dao = INSTANCE.cartDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            return null;
        }
    }
}
