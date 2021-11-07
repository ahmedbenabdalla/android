package tn.esprit.restaurantcommand.ui.adapters;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;

import java.util.List;

import tn.esprit.restaurantcommand.R;
import tn.esprit.restaurantcommand.data.models.Cart;
import tn.esprit.restaurantcommand.data.service.dao.ViewCart;

public class CartClientAdapter extends ListAdapter<Cart, CartClientAdapter.ViewHolder> {
    //creating a variable for on item click listner.
    private OnItemClickListener listener;
    private Context context;
    //creating a constructor class for our adapter class.
    public CartClientAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context=context;
    }

    //creating a call back for item of recycler view.
    private static final DiffUtil.ItemCallback<Cart> DIFF_CALLBACK = new DiffUtil.ItemCallback<Cart>() {
        @Override
        public boolean areItemsTheSame(Cart oldItem, Cart newItem) {
            return oldItem.get_id() == newItem.get_id();
        }

        @Override
        public boolean areContentsTheSame(Cart oldItem, Cart newItem) {
            //below line is to check the course name, description and course duration.
            return oldItem.getName().equals(newItem.getName()) &&
                    oldItem.getImageUrl().equals(newItem.getImageUrl()) &&
                    oldItem.getPrice().equals(newItem.getPrice());
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_card, parent, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cart cart = getCartAt(position);
        holder.title.setText(cart.getName());
        holder.priceEachItem.setText(cart.getPrice());
        holder.totalEachItem.setText(String.valueOf (Double.valueOf(cart.getPrice()) * Integer.valueOf(cart.getQuantity())));
        Toast.makeText(context, String.valueOf(cart.getQuantity()), Toast.LENGTH_SHORT).show();
        holder.num.setText(String.valueOf(cart.getQuantity()));
        String image=cart.getImageUrl();
        Glide.with(holder.pic.getContext()).load(image).into(holder.pic);
        ViewCart  viewCart = ViewModelProviders.of((FragmentActivity) context).get(ViewCart.class);
        holder.minusItem.setOnClickListener(v->{
            Cart cartU=new Cart(cart.getUserId(),cart.getDishId(),cart.getName(),cart.getQuantity()-1,cart.getPrice(),cart.getImageUrl());
            cartU.set_id(cart.get_id());
            viewCart.update(cartU);
            notifyItemChanged(position);
            notifyDataSetChanged();
        });
        holder.plusItem.setOnClickListener(v->{
            Cart cartU=new Cart(cart.getUserId(),cart.getDishId(),cart.getName(),cart.getQuantity()+1,cart.getPrice(),cart.getImageUrl());
            cartU.set_id(cart.get_id());
            viewCart.update(cartU);
            notifyItemChanged(position);
            notifyDataSetChanged();
        });
        holder.clear.setOnClickListener(v->{
            viewCart.delete(cart);
            notifyItemRemoved(position);
            notifyDataSetChanged();
        });
    }

    public Cart getCartAt(int position) {
        return getItem(position);
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, priceEachItem;
        ImageView pic, plusItem, minusItem,clear;
        TextView totalEachItem, num;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title2Txt);
            priceEachItem = itemView.findViewById(R.id.priceEachItem);
            pic = itemView.findViewById(R.id.picCard);
            totalEachItem = itemView.findViewById(R.id.totalEachItem);
            num = itemView.findViewById(R.id.numberItems);
            plusItem = itemView.findViewById(R.id.plusCardBtn);
            minusItem = itemView.findViewById(R.id.minusCardBtn);
            clear=itemView.findViewById(R.id.clear);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Cart model);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
