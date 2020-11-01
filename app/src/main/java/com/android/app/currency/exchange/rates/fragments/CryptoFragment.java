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
import com.android.app.currency.exchange.rates.adapters.CryptoAdapter;
import com.android.app.currency.exchange.rates.items.CryptoItem;

import java.util.ArrayList;

public class CryptoFragment extends Fragment implements CryptoAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private CryptoAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<CryptoItem> cryptoList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_crypto, container, false);
        if (cryptoList != null || cryptoList.size() > 0) {
            cryptoList.clear();
        }
        cryptoList.add(new CryptoItem(R.drawable.ic_baseline_monetization_on_24, "Kursy kryptowalut", "Kryptowaluty", "Opis"));
        recyclerView = fragmentView.findViewById(R.id.crypto_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        adapter = new CryptoAdapter(cryptoList, this);
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
