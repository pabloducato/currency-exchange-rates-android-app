package com.android.app.currency.exchange.rates.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.app.currency.exchange.rates.items.OptionItem;
import com.android.app.currency.exchange.rates.R;

import java.util.ArrayList;

public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.OptionViewHolder> {

    private ArrayList<OptionItem> mOptionList;
    private OnItemClickListener mOnItemClickListener;

    public OptionAdapter(ArrayList<OptionItem> items, OnItemClickListener onItemClickListener) {
        this.mOptionList = items;
        this.mOnItemClickListener = onItemClickListener;

    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public static class OptionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imageView;
        public TextView rateAbbreviation;
        public TextView rateValue;
        public TextView rateDescription;
        OnItemClickListener onItemClickListener;

        public OptionViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            rateAbbreviation = itemView.findViewById(R.id.rate_abbreviation);
            rateValue = itemView.findViewById(R.id.rate_value);
            rateDescription = itemView.findViewById(R.id.rate_description);
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
    public OptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.option_item, parent, false);
        return new OptionViewHolder(view, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull OptionViewHolder holder, int position) {
        OptionItem currentItem = mOptionList.get(position);
        holder.imageView.setImageResource(currentItem.getImageResource());
        holder.rateAbbreviation.setText(currentItem.getRateAbbreviation());
        holder.rateValue.setText(currentItem.getRateValue());
        holder.rateDescription.setText(currentItem.getRateDescription());
    }

    @Override
    public int getItemCount() {
        return mOptionList.size();
    }
}
