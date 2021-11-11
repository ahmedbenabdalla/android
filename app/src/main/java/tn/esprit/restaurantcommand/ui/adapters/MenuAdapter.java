package tn.esprit.restaurantcommand.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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
import tn.esprit.restaurantcommand.data.models.Dish;
import tn.esprit.restaurantcommand.data.utils.AppConstant;
import tn.esprit.restaurantcommand.data.utils.PreferencesUtils;
import tn.esprit.restaurantcommand.ui.view.admin.AddDishActivity;

public class MenuAdapter extends  RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    List<Dish> dishes;
    Context context;

    public MenuAdapter(List<Dish> dishes, Context context)
    {
        this.context=context;
        this.dishes=dishes;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_sample,parent,false);

        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        final String idDish=dishes.get(position).get_id();
        holder.deleteDish.setOnClickListener(v-> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Are you sure?")
                    .setMessage("This action can't be undone!")
                    .setCancelable(true)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            Call<String> call = ApiClient.getInstance().getDishService().deleteDish(PreferencesUtils.getSaved(context.getApplicationContext(), AppConstant.TOKEN), idDish);
                            call.enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    Toast.makeText(context, "Itew is deleted", Toast.LENGTH_LONG).show();
                                    dishes.remove(position);
                                    notifyDataSetChanged();
                                }
                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
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
        });
        holder.isAvaible.setOnClickListener(v-> {
                    Call<Dish> call = ApiClient.getInstance().getDishService().isAvaible(PreferencesUtils.getSaved(context.getApplicationContext(), AppConstant.TOKEN), idDish);
                    call.enqueue(new Callback<Dish>() {
                        @Override
                        public void onResponse(Call<Dish> call, Response<Dish> response) {
                                  Toast.makeText(context,"Updated",Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Call<Dish> call, Throwable t) {

                        }

                    });

                });

        holder.editDish.setOnClickListener(v->
                context.startActivities(new Intent[]{new Intent((Activity) context, AddDishActivity.class).putExtra("dishToEdit",dishes.get(position))}));

        holder.dishName.setText(dishes.get(position).getTitle());
        holder.dishPrice.setText(dishes.get(position).getPrice());
        holder.dishDescription.setText(dishes.get(position).getDescription());
        holder.isAvaible.setChecked(dishes.get(position).getAvaible());
        String imgurl = dishes.get(position).getImageUrl();
        Glide.with(holder.dishImage.getContext()).load(imgurl).into(holder.dishImage);

    }

    @Override
    public int getItemCount() {
        return dishes.size();
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder{

        ImageView dishImage,deleteDish,editDish;
        TextView dishPrice,dishName,dishDescription;
        CheckBox isAvaible;


        public MenuViewHolder(@NonNull View itemView)
        {
            super(itemView);
            dishImage=itemView.findViewById(R.id.menuImage);
            deleteDish=itemView.findViewById(R.id.dishBtnDel);
            editDish=itemView.findViewById(R.id.dishBtnEdi);
            dishPrice=itemView.findViewById(R.id.dishPrice);
            dishName=itemView.findViewById(R.id.dishTitle);
            dishDescription=itemView.findViewById(R.id.dishDesc);
            isAvaible=itemView.findViewById(R.id.dishAvaible);
        }

    }

}
