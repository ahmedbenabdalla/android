package tn.esprit.restaurantcommand.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import tn.esprit.restaurantcommand.R;
import tn.esprit.restaurantcommand.data.models.Restaurant;

public class RestaurantClientAdapter extends RecyclerView.Adapter<RestaurantClientAdapter.RestaurantClientViewHolder> {


    List<Restaurant> restaurants;

    public RestaurantClientAdapter(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    @NonNull
    @Override
    public RestaurantClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_res, parent, false);

        return new RestaurantClientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantClientViewHolder holder, int position) {
        holder.nameRes.setText(restaurants.get(position).getName());
        holder.address.setText(restaurants.get(position).getAddress());
        String image=restaurants.get(position).getImageUrl();
        Glide.with(holder.resImage.getContext()).load(image).into(holder.resImage);

    }



    @Override
    public int getItemCount() {
        return restaurants.size();
    }
    public class  RestaurantClientViewHolder extends RecyclerView.ViewHolder{
        TextView nameRes,address;
        ImageView resImage;
        ConstraintLayout homeLayout;
        public RestaurantClientViewHolder(View view)
        {
            super(view);
            nameRes=view.findViewById(R.id.resName);
            address=view.findViewById(R.id.resadd);
            resImage=view.findViewById(R.id.resPic);
            homeLayout=view.findViewById(R.id.homeLayout);
        }
    }
}
