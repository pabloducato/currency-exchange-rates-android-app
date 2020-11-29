package com.android.app.currency.exchange.rates.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.app.currency.exchange.rates.R;
import com.android.app.currency.exchange.rates.items.MessageItem;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private ArrayList<MessageItem> mMessageList;
    private MessageAdapter.OnItemClickListener mOnItemClickListener;

    public MessageAdapter(ArrayList<MessageItem> items, MessageAdapter.OnItemClickListener onItemClickListener) {
        this.mMessageList = items;
        this.mOnItemClickListener = onItemClickListener;

    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imageView;
        public TextView messageTitle;
        public TextView messageDescription;
        public TextView messageDate;
        public TextView messageTime;
        public TextView messageLink;

        MessageAdapter.OnItemClickListener onItemClickListener;

        public MessageViewHolder(@NonNull View itemView, MessageAdapter.OnItemClickListener onItemClickListener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.message_image_view);
            messageTitle = itemView.findViewById(R.id.message_title);
            messageDescription = itemView.findViewById(R.id.message_description);
            messageDate = itemView.findViewById(R.id.message_date);
            messageTime = itemView.findViewById(R.id.message_time);
            messageLink = itemView.findViewById(R.id.message_link);
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
    public MessageAdapter.MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false);
        return new MessageAdapter.MessageViewHolder(view, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.MessageViewHolder holder, int position) {
        MessageItem currentItem = mMessageList.get(position);
        holder.imageView.setImageResource(currentItem.getImageResource());
        holder.messageTitle.setText(currentItem.getMessageTitle());
        holder.messageDescription.setText(currentItem.getMessageDescription());
        holder.messageDate.setText(currentItem.getMessageDate());
        holder.messageTime.setText(currentItem.getMessageTime());
        holder.messageLink.setText(currentItem.getMessageLink());
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

}
