package tn.esprit.restaurantcommand.ui.adapters;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import tn.esprit.restaurantcommand.R;
import tn.esprit.restaurantcommand.data.api.ApiClient;
import tn.esprit.restaurantcommand.data.models.Restaurant;
import tn.esprit.restaurantcommand.data.utils.AppConstant;
import tn.esprit.restaurantcommand.data.utils.PreferencesUtils;
import tn.esprit.restaurantcommand.ui.view.admin.AddRestaurantActivity;
import tn.esprit.restaurantcommand.ui.view.admin.MenuActivity;


public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {

    List<Restaurant> data;
    Context context;



    public RestaurantAdapter(List<Restaurant> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_res_admin, parent, false);

        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        try {

            final String id = data.get(position).getId().toString();
            Toast.makeText(context, "id of res" + id, Toast.LENGTH_LONG).show();
            holder.menu.setOnClickListener(v->{
                context.startActivity(new Intent(context, MenuActivity.class).putExtra("resId",id));
            });
            holder.deleteRestaurant.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Are you sure?")
                            .setMessage("This action can't be undone!")
                            .setCancelable(true)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                    Call<String> call = ApiClient.getInstance().getRestaurantRepo().deleteRestaurant(PreferencesUtils.getSaved(context.getApplicationContext(), AppConstant.TOKEN), id);
                                    call.enqueue(new Callback<String>() {
                                        @Override
                                        public void onResponse(Call<String> call, Response<String> response) {
                                            Toast.makeText(context, "Itew is deleted", Toast.LENGTH_LONG).show();
                                            data.remove(position);
                                            notifyDataSetChanged();
                                        }
                                        @Override
                                        public void onFailure(Call<String> call, Throwable t) {
                                            Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                                            data.remove(position);
                                            notifyDataSetChanged();
                                        }
                                    });
                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    })
                            .show();

                    notifyDataSetChanged();
                }
            });
            holder.editRestaurant.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivities(new Intent[]{new Intent((Activity) context, AddRestaurantActivity.class).putExtra("resToEdit",data.get(position))});

                }

            });

            holder.name.setText(data.get(position).getName());
            holder.address.setText(data.get(position).getAddress());
            String imgurl = data.get(position).getImageUrl();
            Glide.with(holder.restaurantImage.getContext()).load(imgurl).into(holder.restaurantImage);


        } catch (NullPointerException e) {
            System.out.println(e);
        }

    }


    @Override
    public int getItemCount() {
        return data.size();
    }


    public class RestaurantViewHolder extends RecyclerView.ViewHolder {
        ImageView restaurantImage, deleteRestaurant ,editRestaurant;
        TextView address, name;
        Button menu;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            restaurantImage = itemView.findViewById(R.id.resImage);
            address = itemView.findViewById(R.id.addressRestaurant);
            name = itemView.findViewById(R.id.restaurantName);
            deleteRestaurant = itemView.findViewById(R.id.imageDeleteRestaurant);
            editRestaurant=itemView.findViewById(R.id.imageEditRestaurant);
            menu=itemView.findViewById(R.id.btnMenu);

        }


    }





}






