package com.android.app.currency.exchange.rates.fragments;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
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
import com.android.app.currency.exchange.rates.adapters.InternetExceptionAdapter;
import com.android.app.currency.exchange.rates.items.GoldItem;
import com.android.app.currency.exchange.rates.items.InternetExceptionItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class GoldFragment extends Fragment implements GoldAdapter.OnItemClickListener {

    private final ArrayList<GoldItem> goldList = new ArrayList<>();
    private final ArrayList<InternetExceptionItem> internetExceptionList = new ArrayList<>();
    SimpleDateFormat time_format = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_gold, container, false);
        RecyclerView recyclerView = fragmentView.findViewById(R.id.gold_recycler_view);
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

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        if (isNetworkAvailable(Objects.requireNonNull(getContext())) && price != null) {
            try {
                goldList.add(new GoldItem(R.drawable.ic_baseline_star_24, arrayGoldString.split(";")[0], "Cena 1g (próba 1000)", price, "ZŁ" + price));
            } catch (Exception e) {
                e.printStackTrace();
            }
            GoldAdapter adapter = new GoldAdapter(goldList, this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        } else {
            internetExceptionList.add(new InternetExceptionItem(R.drawable.ic_baseline_info_24, formatter.format(Calendar.getInstance().getTime()), time_format.format(Calendar.getInstance().getTime()), "Brak połączenia z Internetem"));
            InternetExceptionAdapter adapter = new InternetExceptionAdapter(internetExceptionList, this::onItemClick);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }

        super.onCreate(savedInstanceState);
        return fragmentView;
    }

    @Override
    public void onItemClick(int position) {
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
