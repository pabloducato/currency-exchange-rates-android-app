package com.android.app.currency.exchange.rates.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.app.currency.exchange.rates.R;
import com.android.app.currency.exchange.rates.items.AuthorItem;

import java.util.ArrayList;

public class AuthorAdapter extends RecyclerView.Adapter<AuthorAdapter.AuthorViewHolder> {

    private final ArrayList<AuthorItem> mAuthorList;
    private final AuthorAdapter.OnItemClickListener mOnItemClickListener;

    public AuthorAdapter(ArrayList<AuthorItem> items, AuthorAdapter.OnItemClickListener onItemClickListener) {
        this.mAuthorList = items;
        this.mOnItemClickListener = onItemClickListener;

    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public static class AuthorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imageView;
        public TextView authorTitle;
        public TextView authorDescription;
        public TextView authorLink;

        AuthorAdapter.OnItemClickListener onItemClickListener;

        public AuthorViewHolder(@NonNull View itemView, AuthorAdapter.OnItemClickListener onItemClickListener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.author_image_view);
            authorTitle = itemView.findViewById(R.id.author_title);
            authorDescription = itemView.findViewById(R.id.author_description);
            authorLink = itemView.findViewById(R.id.author_link);
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
    public AuthorAdapter.AuthorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.author_item, parent, false);
        return new AuthorAdapter.AuthorViewHolder(view, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AuthorAdapter.AuthorViewHolder holder, int position) {
        AuthorItem currentItem = mAuthorList.get(position);
        holder.imageView.setImageResource(currentItem.getImageResource());
        holder.authorTitle.setText(currentItem.getAuthorTitle());
        holder.authorDescription.setText(currentItem.getAuthorDescription());
        holder.authorLink.setText(currentItem.getAuthorLink());
    }

    @Override
    public int getItemCount() {
        return mAuthorList.size();
    }


}
