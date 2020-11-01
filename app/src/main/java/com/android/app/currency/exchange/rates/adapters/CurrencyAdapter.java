package com.android.app.currency.exchange.rates.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.app.currency.exchange.rates.R;
import com.android.app.currency.exchange.rates.items.CurrencyItem;

import java.util.ArrayList;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder> {

    private ArrayList<CurrencyItem> mCurrencyList;
    private CurrencyAdapter.OnItemClickListener mOnItemClickListener;

    public CurrencyAdapter(ArrayList<CurrencyItem> items, CurrencyAdapter.OnItemClickListener onItemClickListener) {
        this.mCurrencyList = items;
        this.mOnItemClickListener = onItemClickListener;

    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public static class CurrencyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imageView;
        public TextView rateAbbreviation;
        public TextView rateValue;
        public TextView rateDescription;
        public TextView rateBigValue;
        CurrencyAdapter.OnItemClickListener onItemClickListener;

        public CurrencyViewHolder(@NonNull View itemView, CurrencyAdapter.OnItemClickListener onItemClickListener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.currency_image_view);
            rateAbbreviation = itemView.findViewById(R.id.currency_rate_abbreviation);
            rateValue = itemView.findViewById(R.id.currency_rate_value);
            rateDescription = itemView.findViewById(R.id.currency_rate_description);
            rateBigValue = itemView.findViewById(R.id.currency_rate_big_value);
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
    public CurrencyAdapter.CurrencyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.currency_item, parent, false);
        return new CurrencyAdapter.CurrencyViewHolder(view, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyAdapter.CurrencyViewHolder holder, int position) {
        CurrencyItem currentItem = mCurrencyList.get(position);
        holder.imageView.setImageResource(currentItem.getImageResource());
        holder.rateAbbreviation.setText(currentItem.getRateAbbreviation());
        holder.rateValue.setText(currentItem.getRateValue());
        holder.rateDescription.setText(currentItem.getRateDescription());
        holder.rateBigValue.setText(currentItem.getRateBigValue());
    }

    @Override
    public int getItemCount() {
        return mCurrencyList.size();
    }
}
