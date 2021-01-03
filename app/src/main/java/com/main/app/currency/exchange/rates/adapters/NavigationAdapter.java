package com.main.app.currency.exchange.rates.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.main.app.currency.exchange.rates.R;
import com.main.app.currency.exchange.rates.items.NavigationItem;

import java.util.ArrayList;

public class NavigationAdapter extends RecyclerView.Adapter<NavigationAdapter.NavigationViewHolder> {

    private ArrayList<NavigationItem> mNavigationList;
    private OnItemClickListener mOnItemClickListener;

    public NavigationAdapter(ArrayList<NavigationItem> items, OnItemClickListener onItemClickListener) {
        this.mNavigationList = items;
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public static class NavigationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imageView;
        public TextView navigationName;
        NavigationAdapter.OnItemClickListener onItemClickListener;

        public NavigationViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.navigation_image_view);
            navigationName = itemView.findViewById(R.id.navigation_name);
            this.onItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public NavigationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int ViewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.navigation_item, parent, false);
        return new NavigationAdapter.NavigationViewHolder(view, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NavigationAdapter.NavigationViewHolder holder, int position) {
        NavigationItem currentItem = mNavigationList.get(position);
        holder.imageView.setImageResource(currentItem.getImageResource());
        holder.navigationName.setText(currentItem.getNavigationName());
    }

    @Override
    public int getItemCount() {
        return mNavigationList.size();
    }

}
