package com.android.app.currency.exchange.rates.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.app.currency.exchange.rates.VolleySingleton;
import com.android.app.currency.exchange.rates.items.OptionItem;
import com.android.app.currency.exchange.rates.R;
import com.android.app.currency.exchange.rates.adapters.OptionAdapter;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class ListFragment extends Fragment implements OptionAdapter.OnItemClickListener {

    private RequestQueue requestQueue;
    private RecyclerView recyclerView;
    private OptionAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<OptionItem> optionList = new ArrayList<>();
    private ArrayList<String> currencyList = new ArrayList<>();
    private ArrayList<String> goldList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_list, container, false);
        if (optionList != null || optionList.size() > 0) {
            optionList.clear();
        }
        optionList.add(new OptionItem(R.drawable.ic_baseline_euro_24, "Kursy walut", "Waluty", "Opis"));
        optionList.add(new OptionItem(R.drawable.ic_baseline_star_24, "Kursy złota", "Złoto", "Opis"));
        optionList.add(new OptionItem(R.drawable.ic_baseline_monetization_on_24, "Kursy kryptowalut", "Kryptowaluty", "Opis"));
        recyclerView = fragmentView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        adapter = new OptionAdapter(optionList, this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        return fragmentView;
    }

    @Override
    public void onItemClick(int position) {
        String dateString, URL;
        JsonArrayRequest jsonArrayRequest;
        switch (position) {
            case 0:
                requestQueue = VolleySingleton.getInstance(getContext()).getRequestQueue();
                dateString = validateDateString();
                URL = "https://api.nbp.pl/api/exchangerates/tables/A/" + dateString + "/" + "?format=json";
                jsonArrayRequest = returnJsonCurrencyArrayRequest(URL);
                requestQueue.add(jsonArrayRequest);
                break;
            case 1:
                requestQueue = VolleySingleton.getInstance(getContext()).getRequestQueue();
                dateString = validateDateString();
                URL = "https://api.nbp.pl/api/cenyzlota/" + dateString + "/" + "?format=json";
                jsonArrayRequest = returnJsonGoldArrayRequest(URL);
                requestQueue.add(jsonArrayRequest);
                break;
            case 2:
                CryptoFragment cryptoFragment = new CryptoFragment();
//                Bundle bundle = new Bundle();
//                String arrayGoldString = goldList.get(0);
//                bundle.putString("crypto", arrayGoldString);
//                cryptoFragment.setArguments(bundle);
                FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_second_container, cryptoFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
        }
    }

    private String validateDateString() {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        String dateString = "";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat sdfHour = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String hour = sdfHour.format(calendar.getTime());
        String start = "00:00";
        String stop = "12:00";
        boolean b = hour.compareTo(start) >= 0 && hour.compareTo(stop) <= 0;
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            calendar.add(Calendar.DATE, -1);
            date = calendar.getTime();
            dateString = dateFormat.format(date);
        } else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            calendar.add(Calendar.DATE, -2);
            date = calendar.getTime();
            dateString = dateFormat.format(date);
        } else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY && (b)) {
            calendar.add(Calendar.DATE, -3);
            date = calendar.getTime();
            dateString = dateFormat.format(date);
        } else if ((calendar.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) && (b)) {
            calendar.add(Calendar.DATE, -1);
            date = calendar.getTime();
            dateString = dateFormat.format(date);
        } else {
            dateString = dateFormat.format(date);
        }
        return dateString;
    }

    private JsonArrayRequest returnJsonCurrencyArrayRequest(String URL) {
        return new JsonArrayRequest(Request.Method.GET, URL, null, response -> {
            try {
                if (currencyList != null || currencyList.size() > 0) {
                    currencyList.clear();
                }
                JSONObject ratesList = response.getJSONObject(0);
                JSONArray rates = ratesList.getJSONArray("rates");
                for (int i = 0; i < rates.length(); i++) {
                    JSONObject rate = rates.getJSONObject(i);
                    String currency = rate.getString("currency");
                    String code = rate.getString("code");
                    double mid = rate.getDouble("mid");
                    currencyList.add(i, currency + ";" + code + ";" + mid);
                }
                CurrencyFragment currencyFragment = new CurrencyFragment();
                Bundle bundle = new Bundle();
                String[] arrayCurrencyList = currencyList.toArray(new String[0]);
                bundle.putStringArray("currency", arrayCurrencyList);
                currencyFragment.setArguments(bundle);
                FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_second_container, currencyFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, Throwable::printStackTrace);
    }

    private JsonArrayRequest returnJsonGoldArrayRequest(String URL) {
        return new JsonArrayRequest(Request.Method.GET, URL, null, response -> {
            try {
                if (goldList != null || goldList.size() > 0) {
                    goldList.clear();
                }
                JSONObject goldResponse = response.getJSONObject(0);
                String goldDate = goldResponse.getString("data");
                String goldPrice = goldResponse.getString("cena");
                goldList.add(0, goldDate + ";" + goldPrice);
                GoldFragment goldFragment = new GoldFragment();
                Bundle bundle = new Bundle();
                String arrayGoldString = goldList.get(0);
                bundle.putString("gold", arrayGoldString);
                goldFragment.setArguments(bundle);
                FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_second_container, goldFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, Throwable::printStackTrace);
    }
}
