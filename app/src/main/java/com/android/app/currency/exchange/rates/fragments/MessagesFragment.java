package com.android.app.currency.exchange.rates.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.app.currency.exchange.rates.R;
import com.android.app.currency.exchange.rates.adapters.MessageAdapter;
import com.android.app.currency.exchange.rates.items.MessageItem;

import java.util.ArrayList;

public class MessagesFragment extends Fragment implements MessageAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private MessageAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<MessageItem> messageList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_notifications, container, false);
        if (messageList != null || messageList.size() > 0) {
            messageList.clear();
        }
        messageList.add(new MessageItem(R.drawable.ic_baseline_fiber_new_24, "29.11.2020", "23:51:00", "Cyber Monday w x-kom.pl", "Za kilka minut rusza wielka wyprzedaż w sklepie internetowym x-kom.pl", "https://www.x-kom.pl"));
        messageList.add(new MessageItem(R.drawable.ic_baseline_fiber_new_24, "29.11.2020", "23:51:00", "Cyber Monday w x-kom.pl", "Za kilka minut rusza wielka wyprzedaż w sklepie internetowym x-kom.pl", "https://www.x-kom.pl"));
        messageList.add(new MessageItem(R.drawable.ic_baseline_fiber_new_24, "29.11.2020", "23:51:00", "Cyber Monday w x-kom.pl", "Za kilka minut rusza wielka wyprzedaż w sklepie internetowym x-kom.pl", "https://www.x-kom.pl"));
        messageList.add(new MessageItem(R.drawable.ic_baseline_fiber_new_24, "29.11.2020", "23:51:00", "Cyber Monday w x-kom.pl", "Za kilka minut rusza wielka wyprzedaż w sklepie internetowym x-kom.pl", "https://www.x-kom.pl"));
        messageList.add(new MessageItem(R.drawable.ic_baseline_fiber_new_24, "29.11.2020", "23:51:00", "Cyber Monday w x-kom.pl", "Za kilka minut rusza wielka wyprzedaż w sklepie internetowym x-kom.pl", "https://www.x-kom.pl"));
        messageList.add(new MessageItem(R.drawable.ic_baseline_fiber_new_24, "29.11.2020", "23:51:00", "Cyber Monday w x-kom.pl", "Za kilka minut rusza wielka wyprzedaż w sklepie internetowym x-kom.pl", "https://www.x-kom.pl"));
        RecyclerView recyclerView = fragmentView.findViewById(R.id.message_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        MessageAdapter adapter = new MessageAdapter(messageList, this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        return fragmentView;
    }

    @Override
    public void onItemClick(int position) {

    }
}
