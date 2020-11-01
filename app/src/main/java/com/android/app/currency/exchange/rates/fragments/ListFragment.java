package com.android.app.currency.exchange.rates.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.app.currency.exchange.rates.VolleySingleton;
import com.android.app.currency.exchange.rates.activities.CryptoActivity;
import com.android.app.currency.exchange.rates.activities.CurrencyActivity;
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

import static android.content.ContentValues.TAG;

public class ListFragment extends Fragment implements OptionAdapter.OnItemClickListener {
    public static final String API_CURRENCY_RESPONSE = "com.android.app.currency.exchange.rates.fragments.API_CURRENCY_RESPONSE";
    private RequestQueue requestQueue;
    private RecyclerView recyclerView;
    private OptionAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<OptionItem> optionList = new ArrayList<>();
    private ArrayList<String> currencyList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_list, container, false);
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
        Intent intent;
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
//                Log.d(TAG, "onItemClick: clicked.");
//                intent = new Intent(getActivity(), GoldActivity.class);
//                intent.putExtra("some_object", "something_else");
//                startActivity(intent);
                break;
            case 2:
                Log.d(TAG, "onItemClick: clicked.");
                intent = new Intent(getActivity(), CryptoActivity.class);
                intent.putExtra("some_object", "something_else");
                startActivity(intent);
                break;
        }
    }

    private String validateDateString() {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        String dateString = "";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            calendar.add(Calendar.DATE, -1);
            date = calendar.getTime();
            dateString = dateFormat.format(date);
        } else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            calendar.add(Calendar.DATE, -2);
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
                JSONObject ratesList = response.getJSONObject(0);
                JSONArray rates = ratesList.getJSONArray("rates");
                for (int i = 0; i < rates.length(); i++) {
                    JSONObject rate = rates.getJSONObject(i);
                    String currency = rate.getString("currency");
                    String code = rate.getString("code");
                    double mid = rate.getDouble("mid");
                    currencyList.add(i, currency + ";" + code + ";" + mid);
                }
                Log.d(TAG, "onItemClick: clicked.");
                Intent intent;
                intent = new Intent(getActivity(), CurrencyActivity.class);
                intent.putExtra("currency", currencyList);
                startActivity(intent);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, Throwable::printStackTrace);
    }

    private JsonArrayRequest returnJsonGoldArrayRequest(String URL) {
        return new JsonArrayRequest(Request.Method.GET, URL, null, response -> {
            try {
                JSONObject ratesList = response.getJSONObject(0);
                JSONArray rates = ratesList.getJSONArray("rates");
                for (int i = 0; i < rates.length(); i++) {
                    JSONObject rate = rates.getJSONObject(i);
                    String currency = rate.getString("currency");
                    String code = rate.getString("code");
                    double mid = rate.getDouble("mid");
                    currencyList.add(i, currency + ";" + code + ";" + mid);
                }
                Log.d(TAG, "onItemClick: clicked.");
                Intent intent;
                intent = new Intent(getActivity(), CurrencyActivity.class);
                intent.putExtra("currency", currencyList);
                startActivity(intent);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, Throwable::printStackTrace);
    }
}
