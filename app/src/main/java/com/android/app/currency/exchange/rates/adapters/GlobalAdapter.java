package com.android.app.currency.exchange.rates.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.app.currency.exchange.rates.R;
import com.android.app.currency.exchange.rates.items.GlobalItem;

import java.util.ArrayList;

public class GlobalAdapter extends RecyclerView.Adapter<GlobalAdapter.GlobalViewHolder> {

    private ArrayList<GlobalItem> mGlobalList;
    private GlobalAdapter.OnItemClickListener mOnItemClickListener;

    public GlobalAdapter(ArrayList<GlobalItem> items, GlobalAdapter.OnItemClickListener onItemClickListener) {
        this.mGlobalList = items;
        this.mOnItemClickListener = onItemClickListener;

    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public static class GlobalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imageView;
        public TextView rateAbbreviation;
        public TextView rateValue;
        public TextView rateDescription;
        GlobalAdapter.OnItemClickListener onItemClickListener;

        public GlobalViewHolder(@NonNull View itemView, GlobalAdapter.OnItemClickListener onItemClickListener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.global_image_view);
            rateAbbreviation = itemView.findViewById(R.id.global_rate_abbreviation);
            rateValue = itemView.findViewById(R.id.global_rate_value);
            rateDescription = itemView.findViewById(R.id.global_rate_description);
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
    public GlobalAdapter.GlobalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.global_item, parent, false);
        return new GlobalAdapter.GlobalViewHolder(view, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull GlobalAdapter.GlobalViewHolder holder, int position) {
        GlobalItem currentItem = mGlobalList.get(position);
        holder.imageView.setImageResource(currentItem.getImageResource());
        holder.rateAbbreviation.setText(currentItem.getRateAbbreviation());
        holder.rateValue.setText(currentItem.getRateValue());
        holder.rateDescription.setText(currentItem.getRateDescription());
    }

    @Override
    public int getItemCount() {
        return mGlobalList.size();
    }
}
