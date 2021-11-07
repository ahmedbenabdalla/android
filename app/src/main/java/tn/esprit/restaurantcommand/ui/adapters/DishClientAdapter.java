package tn.esprit.restaurantcommand.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import tn.esprit.restaurantcommand.R;
import tn.esprit.restaurantcommand.data.models.Dish;
import tn.esprit.restaurantcommand.ui.view.client.fragments.CartFragment;
import tn.esprit.restaurantcommand.ui.view.client.fragments.ShowDetailFragment;


public class DishClientAdapter extends RecyclerView.Adapter<DishClientAdapter.DishClientViewHolder> {


    List<Dish> Dishs;
    Context context;

    public DishClientAdapter(List<Dish> Dishs,Context context) {
        this.Dishs = Dishs;
        this.context=context;
    }

    @NonNull
    @Override
    public DishClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_dish, parent, false);

        return new DishClientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DishClientViewHolder holder, int position) {
        holder.title.setText(Dishs.get(position).getTitle());
        holder.price.setText(Dishs.get(position).getPrice());
        holder.cookingTime.setText(Dishs.get(position).getCookingTime());
        String image=Dishs.get(position).getImageUrl();
        Glide.with(holder.resDish.getContext()).load(image).into(holder.resDish);
        holder.addBtn.setOnClickListener(v->{
            Bundle bundle = new Bundle();
            Fragment shdF=new ShowDetailFragment();
            shdF.setArguments(bundle);
            bundle.putParcelable("Dish",Dishs.get(position));
            ((FragmentActivity)context).getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frag,shdF)
                    .commit();

        });
    }



    @Override
    public int getItemCount() {
        return Dishs.size();
    }
    public class  DishClientViewHolder extends RecyclerView.ViewHolder{
        TextView title,cookingTime,price,addBtn;
        ImageView resDish;

        public DishClientViewHolder(View view)
        {
            super(view);
            title=view.findViewById(R.id.title);
            cookingTime=view.findViewById(R.id.cookingTime);
            resDish=view.findViewById(R.id.pic);
            price=view.findViewById(R.id.price);
            addBtn=view.findViewById(R.id.addBtn);
        }
    }
}
