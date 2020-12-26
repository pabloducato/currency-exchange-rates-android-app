package com.android.app.currency.exchange.rates.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.app.currency.exchange.rates.R;
import com.android.app.currency.exchange.rates.items.ScannerItem;

import java.util.ArrayList;

public class ScannerAdapter extends RecyclerView.Adapter<ScannerAdapter.ScannerViewHolder> {

    private ArrayList<ScannerItem> mScannerList;
    private ScannerAdapter.OnItemClickListener mOnItemClickListener;

    public ScannerAdapter(ArrayList<ScannerItem> items, ScannerAdapter.OnItemClickListener onItemClickListener) {
        this.mScannerList = items;
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public static class ScannerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imageView;
        public TextView scannerName;
        ScannerAdapter.OnItemClickListener onItemClickListener;

        public ScannerViewHolder(@NonNull View itemView, ScannerAdapter.OnItemClickListener onItemClickListener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.scanner_image_view);
            scannerName = itemView.findViewById(R.id.scanner_name);
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
    public ScannerAdapter.ScannerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int ViewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scanner_item, parent, false);
        return new ScannerAdapter.ScannerViewHolder(view, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ScannerAdapter.ScannerViewHolder holder, int position) {
        ScannerItem currentItem = mScannerList.get(position);
        holder.imageView.setImageResource(currentItem.getImageResource());
        holder.scannerName.setText(currentItem.getScannerName());
    }

    @Override
    public int getItemCount() {
        return mScannerList.size();
    }

}
