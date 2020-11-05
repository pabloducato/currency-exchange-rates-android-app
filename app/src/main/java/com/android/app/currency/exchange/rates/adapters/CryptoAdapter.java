package com.android.app.currency.exchange.rates.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.app.currency.exchange.rates.R;
import com.android.app.currency.exchange.rates.items.CryptoItem;

import java.util.ArrayList;

public class CryptoAdapter extends RecyclerView.Adapter<CryptoAdapter.CryptoViewHolder> {

    private ArrayList<CryptoItem> mCryptoList;
    private CryptoAdapter.OnItemClickListener mOnItemClickListener;

    public CryptoAdapter(ArrayList<CryptoItem> items, CryptoAdapter.OnItemClickListener onItemClickListener) {
        this.mCryptoList = items;
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public static class CryptoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imageView;
        public TextView rateAbbreviation;
        public TextView rateValue;
        public TextView rateDescription;
        public TextView ratePercentage;
        CryptoAdapter.OnItemClickListener onItemClickListener;

        public CryptoViewHolder(@NonNull View itemView, CryptoAdapter.OnItemClickListener onItemClickListener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.crypto_image_view);
            rateAbbreviation = itemView.findViewById(R.id.crypto_rate_abbreviation);
            rateValue = itemView.findViewById(R.id.crypto_rate_value);
            rateDescription = itemView.findViewById(R.id.crypto_rate_description);
            ratePercentage = itemView.findViewById(R.id.crypto_rate_percentage);
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
    public CryptoAdapter.CryptoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.crypto_item, parent, false);
        return new CryptoAdapter.CryptoViewHolder(view, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CryptoAdapter.CryptoViewHolder holder, int position) {
        CryptoItem currentItem = mCryptoList.get(position);
        holder.imageView.setImageResource(currentItem.getImageResource());
        holder.rateAbbreviation.setText(currentItem.getRateAbbreviation());
        holder.rateValue.setText(currentItem.getRateValue());
        holder.rateDescription.setText(currentItem.getRateDescription());
        holder.ratePercentage.setText(currentItem.getRatePercentage());
    }

    @Override
    public int getItemCount() {
        return mCryptoList.size();
    }
}
