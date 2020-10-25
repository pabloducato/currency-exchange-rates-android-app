package com.android.app.currency.exchange.rates;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.OptionViewHolder> {

    private ArrayList<OptionItem> mOptionList;

    public static class OptionViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView rateAbbreviation;
        public TextView rateValue;
        public TextView rateDescription;

        public OptionViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            rateAbbreviation = itemView.findViewById(R.id.rate_abbreviation);
            rateValue = itemView.findViewById(R.id.rate_value);
            rateDescription = itemView.findViewById(R.id.rate_description);
        }
    }

    public OptionAdapter(ArrayList<OptionItem> optionList) {
        mOptionList = optionList;
    }

    @NonNull
    @Override
    public OptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.option_item, parent, false);
        OptionViewHolder optionViewHolder = new OptionViewHolder(view);
        return optionViewHolder;
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
