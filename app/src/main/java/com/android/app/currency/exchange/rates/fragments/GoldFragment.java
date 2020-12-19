package com.android.app.currency.exchange.rates.fragments;

import android.graphics.drawable.AnimationDrawable;
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
import com.android.app.currency.exchange.rates.adapters.GoldAdapter;
import com.android.app.currency.exchange.rates.items.GoldItem;

import java.util.ArrayList;

public class GoldFragment extends Fragment implements GoldAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private GoldAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<GoldItem> goldList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_gold, container, false);
        recyclerView = fragmentView.findViewById(R.id.gold_recycler_view);
        AnimationDrawable animationDrawable = (AnimationDrawable) recyclerView.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();
        if (goldList != null || goldList.size() > 0) {
            goldList.clear();
        }
        assert getArguments() != null;
        String arrayGoldString = getArguments().getString("gold");
        String price;
        if (arrayGoldString.split(";")[1].length() < 6) {
            price = arrayGoldString.split(";")[1] + "0";
        } else {
            price = arrayGoldString.split(";")[1];
        }
        goldList.add(new GoldItem(R.drawable.ic_baseline_star_24, arrayGoldString.split(";")[0], "Cena 1g (próba 1000)", price, price));
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        adapter = new GoldAdapter(goldList, this);
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
//                intent = new Intent(getActivity(), GoldActivity.class);
//                intent.putExtra("some_object", "something_else");
//                startActivity(intent);
//                break;
//        }
    }
}
