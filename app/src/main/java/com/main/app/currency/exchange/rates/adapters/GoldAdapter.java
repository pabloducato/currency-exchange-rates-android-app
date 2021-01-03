package com.main.app.currency.exchange.rates.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.main.app.currency.exchange.rates.R;
import com.main.app.currency.exchange.rates.items.GoldItem;

import java.util.ArrayList;

public class GoldAdapter extends RecyclerView.Adapter<GoldAdapter.GoldViewHolder> {

    private ArrayList<GoldItem> mGoldList;
    private GoldAdapter.OnItemClickListener mOnItemClickListener;

    public GoldAdapter(ArrayList<GoldItem> items, GoldAdapter.OnItemClickListener onItemClickListener) {
        this.mGoldList = items;
        this.mOnItemClickListener = onItemClickListener;

    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public static class GoldViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imageView;
        public TextView rateAbbreviation;
        public TextView rateValue;
        public TextView rateDescription;
        public TextView rateBigValue;
        GoldAdapter.OnItemClickListener onItemClickListener;

        public GoldViewHolder(@NonNull View itemView, GoldAdapter.OnItemClickListener onItemClickListener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.gold_image_view);
            rateAbbreviation = itemView.findViewById(R.id.gold_rate_abbreviation);
            rateValue = itemView.findViewById(R.id.gold_rate_value);
            rateDescription = itemView.findViewById(R.id.gold_rate_description);
            rateBigValue = itemView.findViewById(R.id.gold_rate_big_value);
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
    public GoldAdapter.GoldViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gold_item, parent, false);
        return new GoldAdapter.GoldViewHolder(view, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull GoldAdapter.GoldViewHolder holder, int position) {
        GoldItem currentItem = mGoldList.get(position);
        holder.imageView.setImageResource(currentItem.getImageResource());
        holder.rateAbbreviation.setText(currentItem.getRateAbbreviation());
        holder.rateValue.setText(currentItem.getRateValue());
        holder.rateDescription.setText(currentItem.getRateDescription());
        holder.rateBigValue.setText(currentItem.getRateBigValue());
    }

    @Override
    public int getItemCount() {
        return mGoldList.size();
    }
}
