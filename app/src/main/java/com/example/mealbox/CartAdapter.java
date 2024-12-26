package com.example.mealbox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CartAdapter extends BaseAdapter {

    public interface OnRemoveClickListener {
        void onRemoveClick(CartItem item);
    }

    private Context context;
    private List<CartItem> cartItems;
    private OnRemoveClickListener listener;

    public CartAdapter(Context context, List<CartItem> cartItems, OnRemoveClickListener listener) {
        this.context = context;
        this.cartItems = cartItems;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return cartItems.size();
    }

    @Override
    public Object getItem(int position) {
        return cartItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_cart_item, parent, false);
        }

        CartItem item = cartItems.get(position);

        TextView itemNameTextView = convertView.findViewById(R.id.itemNameTextView);
        TextView itemPriceTextView = convertView.findViewById(R.id.itemPriceTextView);
        ImageView itemImageView = convertView.findViewById(R.id.itemImageView);
        Button removeItemButton = convertView.findViewById(R.id.removeItemButton);

        itemNameTextView.setText(item.getName());
        itemPriceTextView.setText(String.format("$%.2f", item.getPrice()));
        itemImageView.setImageResource(item.getImageRes());

        removeItemButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onRemoveClick(item);
            }
        });

        return convertView;
    }

    public void updateData(List<CartItem> newCartItems) {
        this.cartItems = newCartItems;
        notifyDataSetChanged();
    }
}