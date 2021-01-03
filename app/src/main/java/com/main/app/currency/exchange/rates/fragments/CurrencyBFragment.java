package com.main.app.currency.exchange.rates.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.main.app.currency.exchange.rates.R;
import com.main.app.currency.exchange.rates.adapters.CurrencyBAdapter;
import com.main.app.currency.exchange.rates.items.CurrencyBItem;

import java.util.ArrayList;

public class CurrencyBFragment extends Fragment implements CurrencyBAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private CurrencyBAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<CurrencyBItem> currencyList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_currency_b, container, false);
        if (currencyList != null || currencyList.size() > 0) {
            currencyList.clear();
        }
        assert getArguments() != null;
        String[] arrayCurrencyList = getArguments().getStringArray("currencyTableB");
        for (String s : arrayCurrencyList) {
            currencyList.add(new CurrencyBItem(R.drawable.ic_baseline_euro_24, s.split(";")[0], s.split(";")[1], s.split(";")[2].substring(0, 4), "ZŁ" + s.split(";")[2].substring(0, 4)));
        }
        recyclerView = fragmentView.findViewById(R.id.currency_b_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        adapter = new CurrencyBAdapter(currencyList, this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        return fragmentView;
    }

    @Override
    public void onItemClick(int position) {
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
