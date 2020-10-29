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
import com.android.app.currency.exchange.rates.activities.CurrencyActivity;
import com.android.app.currency.exchange.rates.adapters.CurrencyAdapter;
import com.android.app.currency.exchange.rates.items.CurrencyItem;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class CurrencyFragment extends Fragment implements CurrencyAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private CurrencyAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<CurrencyItem> currencyList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_currency, container, false);
        currencyList.add(new CurrencyItem(R.drawable.ic_baseline_euro_24, "Kursy walut", "Waluty", "Opis"));
        currencyList.add(new CurrencyItem(R.drawable.ic_baseline_star_24, "Kursy złota", "Złoto", "Opis"));
        currencyList.add(new CurrencyItem(R.drawable.ic_baseline_monetization_on_24, "Kursy kryptowalut", "Kryptowaluty", "Opis"));
        recyclerView = (RecyclerView) fragmentView.findViewById(R.id.currency_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        adapter = new CurrencyAdapter(currencyList, this);
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
                intent = new Intent(getActivity(), CurrencyActivity.class);
                intent.putExtra("some_object", "something_else");
                startActivity(intent);
                break;
        }
    }
}
