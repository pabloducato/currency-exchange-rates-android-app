package com.android.app.currency.exchange.rates.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.app.currency.exchange.rates.R;
import com.android.app.currency.exchange.rates.adapters.CurrencyCAdapter;
import com.android.app.currency.exchange.rates.items.CurrencyCItem;

import java.util.ArrayList;

public class CurrencyCFragment extends Fragment implements CurrencyCAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private CurrencyCAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<CurrencyCItem> currencyList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_currency_c, container, false);
        if (currencyList != null || currencyList.size() > 0) {
            currencyList.clear();
        }
        assert getArguments() != null;
        String[] arrayCurrencyList = getArguments().getStringArray("currencyTableC");
        for (String s : arrayCurrencyList) {
            currencyList.add(new CurrencyCItem(R.drawable.ic_baseline_euro_24, s.split(";")[0], s.split(";")[1], s.split(";")[2].substring(0, 4), s.split(";")[2].substring(0, 4)));
        }
        recyclerView = fragmentView.findViewById(R.id.currency_c_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        adapter = new CurrencyCAdapter(currencyList, this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        return fragmentView;
    }

    @Override
    public void onItemClick(int position) {
//        Intent intent;
//        switch (position) {
//            default:
//                Log.d(TAG, "onItemClick: clicked.");
//                intent = new Intent(getActivity(), CurrencyActivity.class);
//                intent.putExtra("some_object", "something_else");
//                startActivity(intent);
//                break;
//        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
