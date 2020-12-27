package com.android.app.currency.exchange.rates.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.app.currency.exchange.rates.R;
import com.android.app.currency.exchange.rates.items.CurrencyBItem;

import java.util.ArrayList;

public class CurrencyBAdapter extends RecyclerView.Adapter<CurrencyBAdapter.CurrencyBViewHolder> {

    private ArrayList<CurrencyBItem> mCurrencyList;
    private CurrencyBAdapter.OnItemClickListener mOnItemClickListener;

    public CurrencyBAdapter(ArrayList<CurrencyBItem> items, CurrencyBAdapter.OnItemClickListener onItemClickListener) {
        this.mCurrencyList = items;
        this.mOnItemClickListener = onItemClickListener;

    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public static class CurrencyBViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imageView;
        public TextView rateAbbreviation;
        public TextView rateValue;
        public TextView rateDescription;
        public TextView rateBigValue;
        CurrencyBAdapter.OnItemClickListener onItemClickListener;

        public CurrencyBViewHolder(@NonNull View itemView, CurrencyBAdapter.OnItemClickListener onItemClickListener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.currency_b_image_view);
            rateAbbreviation = itemView.findViewById(R.id.currency_b_rate_abbreviation);
            rateValue = itemView.findViewById(R.id.currency_b_rate_value);
            rateDescription = itemView.findViewById(R.id.currency_b_rate_description);
            rateBigValue = itemView.findViewById(R.id.currency_b_rate_big_value);
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
    public CurrencyBAdapter.CurrencyBViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.currency_b_item, parent, false);
        return new CurrencyBAdapter.CurrencyBViewHolder(view, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyBAdapter.CurrencyBViewHolder holder, int position) {
        CurrencyBItem currentItem = mCurrencyList.get(position);
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
