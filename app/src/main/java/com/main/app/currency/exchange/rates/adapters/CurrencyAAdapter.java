package com.main.app.currency.exchange.rates.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.main.app.currency.exchange.rates.R;
import com.main.app.currency.exchange.rates.items.CurrencyAItem;

import java.util.ArrayList;

public class CurrencyAAdapter extends RecyclerView.Adapter<CurrencyAAdapter.CurrencyAViewHolder> {

    private ArrayList<CurrencyAItem> mCurrencyList;
    private CurrencyAAdapter.OnItemClickListener mOnItemClickListener;

    public CurrencyAAdapter(ArrayList<CurrencyAItem> items, CurrencyAAdapter.OnItemClickListener onItemClickListener) {
        this.mCurrencyList = items;
        this.mOnItemClickListener = onItemClickListener;

    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public static class CurrencyAViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imageView;
        public TextView rateAbbreviation;
        public TextView rateValue;
        public TextView rateDescription;
        public TextView rateBigValue;
        CurrencyAAdapter.OnItemClickListener onItemClickListener;

        public CurrencyAViewHolder(@NonNull View itemView, CurrencyAAdapter.OnItemClickListener onItemClickListener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.currency_a_image_view);
            rateAbbreviation = itemView.findViewById(R.id.currency_a_rate_abbreviation);
            rateValue = itemView.findViewById(R.id.currency_a_rate_value);
            rateDescription = itemView.findViewById(R.id.currency_a_rate_description);
            rateBigValue = itemView.findViewById(R.id.currency_a_rate_big_value);
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
    public CurrencyAAdapter.CurrencyAViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.currency_a_item, parent, false);
        return new CurrencyAAdapter.CurrencyAViewHolder(view, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyAAdapter.CurrencyAViewHolder holder, int position) {
        CurrencyAItem currentItem = mCurrencyList.get(position);
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
