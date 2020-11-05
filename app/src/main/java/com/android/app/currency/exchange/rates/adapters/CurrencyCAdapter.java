package com.android.app.currency.exchange.rates.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.app.currency.exchange.rates.R;
import com.android.app.currency.exchange.rates.items.CurrencyCItem;

import java.util.ArrayList;

public class CurrencyCAdapter extends RecyclerView.Adapter<CurrencyCAdapter.CurrencyCViewHolder> {

    private ArrayList<CurrencyCItem> mCurrencyList;
    private CurrencyCAdapter.OnItemClickListener mOnItemClickListener;

    public CurrencyCAdapter(ArrayList<CurrencyCItem> items, CurrencyCAdapter.OnItemClickListener onItemClickListener) {
        this.mCurrencyList = items;
        this.mOnItemClickListener = onItemClickListener;

    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public static class CurrencyCViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imageView;
        public TextView rateAbbreviation;
        public TextView rateValue;
        public TextView rateDescription;
        public TextView rateBigValue;
        CurrencyCAdapter.OnItemClickListener onItemClickListener;

        public CurrencyCViewHolder(@NonNull View itemView, CurrencyCAdapter.OnItemClickListener onItemClickListener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.currency_c_image_view);
            rateAbbreviation = itemView.findViewById(R.id.currency_c_rate_abbreviation);
            rateValue = itemView.findViewById(R.id.currency_c_rate_value);
            rateDescription = itemView.findViewById(R.id.currency_c_rate_description);
            rateBigValue = itemView.findViewById(R.id.currency_c_rate_big_value);
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
    public CurrencyCAdapter.CurrencyCViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.currency_c_item, parent, false);
        return new CurrencyCAdapter.CurrencyCViewHolder(view, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyCAdapter.CurrencyCViewHolder holder, int position) {
        CurrencyCItem currentItem = mCurrencyList.get(position);
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
