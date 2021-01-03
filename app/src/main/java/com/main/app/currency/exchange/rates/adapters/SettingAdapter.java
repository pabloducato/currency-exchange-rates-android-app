package com.main.app.currency.exchange.rates.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.main.app.currency.exchange.rates.R;
import com.main.app.currency.exchange.rates.items.SettingItem;

import java.util.ArrayList;

public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.SettingViewHolder> {

    private ArrayList<SettingItem> mSettingsList;
    private SettingAdapter.OnItemClickListener mOnItemClickListener;

    public SettingAdapter(ArrayList<SettingItem> items, SettingAdapter.OnItemClickListener onItemClickListener) {
        this.mSettingsList = items;
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public static class SettingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imageView;
        public TextView settingName;
        SettingAdapter.OnItemClickListener onItemClickListener;

        public SettingViewHolder(@NonNull View itemView, SettingAdapter.OnItemClickListener onItemClickListener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.setting_image_view);
            settingName = itemView.findViewById(R.id.setting_name);
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
    public SettingAdapter.SettingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.setting_item, parent, false);
        return new SettingAdapter.SettingViewHolder(view, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SettingAdapter.SettingViewHolder holder, int position) {
        SettingItem currentItem = mSettingsList.get(position);
        holder.imageView.setImageResource(currentItem.getImageResource());
        holder.settingName.setText(currentItem.getSettingName());
    }

    @Override
    public int getItemCount() {
        return mSettingsList.size();
    }

}
