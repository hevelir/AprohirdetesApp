package com.example.mobwebhazi.adapter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.mobwebhazi.R;
import com.example.mobwebhazi.data.Item;
import com.example.mobwebhazi.data.ItemDao;
import com.example.mobwebhazi.data.ItemListDatabase;
import com.example.mobwebhazi.fragment.DetailsDialogFragment;
import com.example.mobwebhazi.fragment.NewItemDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter
        extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private final List<Item> items;

    private boolean others = false;

    private ItemClickListener listener;

    public MyAdapter(ItemClickListener listener) {
        this.listener = listener;
        items = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Item item = items.get(position);
        holder.nameTextView.setText(item.name);
//        holder.descriptionTextView.setText(item.description);
        holder.categoryTextView.setText(item.category.name());
        holder.priceTextView.setText(item.price + " Ft");
        holder.iconImageView.setImageResource(getImageResource(item.category));
        //holder.isBoughtCheckBox.setChecked(item.isBought);
        holder.item = item;
        holder.detailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.detailsClicked(item);
            }
        });
    }

    private @DrawableRes int getImageResource(Item.Category category) {
        @DrawableRes int ret;
        switch (category) {
            case INGATLAN:
                ret = R.drawable.ingatlan;
                break;
            case JÁRMŰ:
                ret = R.drawable.jarmu;
                break;
            case HÁZTARTÁS:
                ret = R.drawable.haztartas;
                break;
            case ELEKTRONIKA:
                ret = R.drawable.elektronika;
                break;
            case SPORT:
                ret = R.drawable.sport;
                break;
            case RUHÁZAT:
                ret = R.drawable.ruhazat;
                break;
            case ÜZLET:
                ret = R.drawable.uzlet;
                break;

            default:
                ret = 0;
        }
        return ret;
    }

    public void addItem(Item item) {
        items.add(item);
        notifyItemInserted(items.size() - 1);
    }

    public void update(List<Item> Items) {
        items.clear();
        items.addAll(Items);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface ItemClickListener{
        void detailsClicked(Item item);
        void onItemChanged(Item item);
        void onDelete(Item item);
    }

    public void remove(Item item) {
        items.remove(item);
    }

    public void changeState() {
        others = !others;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView iconImageView;
        TextView nameTextView;
       // TextView descriptionTextView;
        TextView categoryTextView;
        TextView priceTextView;
        Button buyButton;
        Button detailsButton;

        Item item;

        MyViewHolder(View itemView) {
            super(itemView);
            iconImageView = itemView.findViewById(R.id.ItemIconImageView);
            nameTextView = itemView.findViewById(R.id.ItemNameTextView);
            //descriptionTextView = itemView.findViewById(R.id.ItemDescriptionTextView);
            categoryTextView = itemView.findViewById(R.id.ItemCategoryTextView);
            priceTextView = itemView.findViewById(R.id.ItemPriceTextView);
            buyButton = itemView.findViewById(R.id.BuyItem);
            detailsButton = itemView.findViewById(R.id.details);

            buyButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    listener.onDelete(item);
                    notifyDataSetChanged();
                }
            });




        }




    }
}