package com.android.app.currency.exchange.rates;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ListFragment extends Fragment {
    private TextView textView;
    private RequestQueue requestQueue;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_list, container, false);
        ArrayList<OptionItem> optionList = new ArrayList<>();
        optionList.add(new OptionItem(R.drawable.ic_baseline_euro_24, "Kursy walut", "Waluty", "Opis"));
        optionList.add(new OptionItem(R.drawable.ic_baseline_star_24, "Kursy złota", "Złoto", "Opis"));
        optionList.add(new OptionItem(R.drawable.ic_baseline_monetization_on_24, "Kursy kryptowalut", "Kryptowaluty", "Opis"));
        recyclerView = (RecyclerView) fragmentView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        adapter = new OptionAdapter(optionList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        //        textView = (TextView) fragmentView.findViewById(R.id.rates_view_result);
//        requestQueue = Volley.newRequestQueue(getActivity());
//        jsonParseRates();
        return fragmentView;
    }

    private void jsonParseRates() {
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
        String URL = "https://api.nbp.pl/api/exchangerates/tables/A/" + dateString + "/" + "?format=json";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL, null, response -> {
            try {
                JSONObject ratesList = response.getJSONObject(0);
                JSONArray rates = ratesList.getJSONArray("rates");
                for (int i = 0; i < rates.length(); i++) {
                    JSONObject rate = rates.getJSONObject(i);
                    String currency = rate.getString("currency");
                    String code = rate.getString("code");
                    double mid = rate.getDouble("mid");
                    textView.append(currency + " | " + code + " | " + mid + "\n\n");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, error -> error.printStackTrace());

        requestQueue.add(jsonArrayRequest);
    }
}
