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
import com.android.app.currency.exchange.rates.adapters.GlobalAdapter;
import com.android.app.currency.exchange.rates.items.GlobalItem;

import java.util.ArrayList;

public class GlobalFragment extends Fragment implements GlobalAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private GlobalAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<GlobalItem> globalList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_global, container, false);
        if (globalList != null || globalList.size() > 0) {
            globalList.clear();
        }
        assert getArguments() != null;
        String[] arrayGlobalCurrencyList = getArguments().getStringArray("global");
        for (String s : arrayGlobalCurrencyList) {
            globalList.add(new GlobalItem(R.drawable.ic_baseline_euro_24, s.split(";")[0], s.split(";")[1], s.split(";")[2].substring(0, 3)));
        }
        recyclerView = fragmentView.findViewById(R.id.global_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        adapter = new GlobalAdapter(globalList, this);
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
//                intent = new Intent(getActivity(), CryptoActivity.class);
//                intent.putExtra("some_object", "something_else");
//                startActivity(intent);
//                break;
//        }
    }

}
