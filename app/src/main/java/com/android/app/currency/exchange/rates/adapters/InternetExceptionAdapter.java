package com.android.app.currency.exchange.rates.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.app.currency.exchange.rates.R;
import com.android.app.currency.exchange.rates.items.InternetExceptionItem;

import java.util.ArrayList;

public class InternetExceptionAdapter extends RecyclerView.Adapter<InternetExceptionAdapter.InternetExceptionViewHolder> {

    private final ArrayList<InternetExceptionItem> mInternetExceptionList;
    private final InternetExceptionAdapter.OnItemClickListener mOnItemClickListener;

    public InternetExceptionAdapter(ArrayList<InternetExceptionItem> items, InternetExceptionAdapter.OnItemClickListener onItemClickListener) {
        this.mInternetExceptionList = items;
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public static class InternetExceptionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imageView;
        public TextView internetExceptionDate;
        public TextView internetExceptionTime;
        public TextView internetExceptionMessage;

        InternetExceptionAdapter.OnItemClickListener onItemClickListener;

        public InternetExceptionViewHolder(@NonNull View itemView, InternetExceptionAdapter.OnItemClickListener onItemClickListener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.internet_exception_image_view);
            internetExceptionDate = itemView.findViewById(R.id.internet_exception_date);
            internetExceptionTime = itemView.findViewById(R.id.internet_exception_time);
            internetExceptionMessage = itemView.findViewById(R.id.internet_exception_message);
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
    public InternetExceptionAdapter.InternetExceptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.internet_exception_item, parent, false);
        return new InternetExceptionAdapter.InternetExceptionViewHolder(view, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull InternetExceptionAdapter.InternetExceptionViewHolder holder, int position) {
        InternetExceptionItem currentItem = mInternetExceptionList.get(position);
        holder.imageView.setImageResource(currentItem.getImageResource());
        holder.internetExceptionDate.setText(currentItem.getInternetExceptionDate());
        holder.internetExceptionTime.setText(currentItem.getInternetExceptionTime());
        holder.internetExceptionMessage.setText(currentItem.getInternetExceptionMessage());
    }

    @Override
    public int getItemCount() {
        return mInternetExceptionList.size();
    }

}
