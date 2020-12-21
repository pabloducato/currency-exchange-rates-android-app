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
import com.android.app.currency.exchange.rates.adapters.CurrencyCAdapter;
import com.android.app.currency.exchange.rates.adapters.InternetExceptionAdapter;
import com.android.app.currency.exchange.rates.items.CurrencyCItem;
import com.android.app.currency.exchange.rates.items.InternetExceptionItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class CurrencyCFragment extends Fragment implements CurrencyCAdapter.OnItemClickListener {

    private final ArrayList<CurrencyCItem> currencyList = new ArrayList<>();
    private final ArrayList<InternetExceptionItem> internetExceptionList = new ArrayList<>();
    SimpleDateFormat time_format = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_currency_c, container, false);
        RecyclerView recyclerView = fragmentView.findViewById(R.id.currency_c_recycler_view);
        AnimationDrawable animationDrawable = (AnimationDrawable) recyclerView.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();
        if (currencyList != null || currencyList.size() > 0) {
            currencyList.clear();
        }
        assert getArguments() != null;
        String[] arrayCurrencyList = getArguments().getStringArray("currencyTableC");

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        if (isNetworkAvailable(Objects.requireNonNull(getContext())) && arrayCurrencyList != null) {
            try {
                for (String s : arrayCurrencyList) {
                    currencyList.add(new CurrencyCItem(R.drawable.ic_baseline_euro_24, s.split(";")[0], s.split(";")[1], s.split(";")[2].length() == 4 ? s.split(";")[2].substring(0, 4) : s.split(";")[2].substring(0, 3) + "0", s.split(";")[2].length() == 4 ? s.split(";")[2].substring(0, 4) : s.split(";")[2].substring(0, 3) + "0"));
                }
                CurrencyCAdapter adapter1 = new CurrencyCAdapter(currencyList, this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            internetExceptionList.add(new InternetExceptionItem(R.drawable.ic_baseline_info_24, formatter.format(Calendar.getInstance().getTime()), time_format.format(Calendar.getInstance().getTime()), "Brak połączenia z Internetem"));
            InternetExceptionAdapter adapter = new InternetExceptionAdapter(internetExceptionList, this::onItemClick);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }

        return fragmentView;
    }

    @Override
    public void onItemClick(int position) {
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
