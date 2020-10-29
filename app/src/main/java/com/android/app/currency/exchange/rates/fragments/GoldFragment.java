package com.android.app.currency.exchange.rates.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.app.currency.exchange.rates.R;
import com.android.app.currency.exchange.rates.activities.GoldActivity;
import com.android.app.currency.exchange.rates.adapters.GoldAdapter;
import com.android.app.currency.exchange.rates.items.GoldItem;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class GoldFragment extends Fragment implements GoldAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private GoldAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<GoldItem> goldList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_gold, container, false);
        goldList.add(new GoldItem(R.drawable.ic_baseline_euro_24, "Kursy walut", "Waluty", "Opis"));
        goldList.add(new GoldItem(R.drawable.ic_baseline_star_24, "Kursy złota", "Złoto", "Opis"));
        goldList.add(new GoldItem(R.drawable.ic_baseline_monetization_on_24, "Kursy kryptowalut", "Kryptowaluty", "Opis"));
        recyclerView = (RecyclerView) fragmentView.findViewById(R.id.gold_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        adapter = new GoldAdapter(goldList, this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        return fragmentView;
    }

    @Override
    public void onItemClick(int position) {
        Intent intent;
        switch (position) {
            default:
                Log.d(TAG, "onItemClick: clicked.");
                intent = new Intent(getActivity(), GoldActivity.class);
                intent.putExtra("some_object", "something_else");
                startActivity(intent);
                break;
        }
    }
}
