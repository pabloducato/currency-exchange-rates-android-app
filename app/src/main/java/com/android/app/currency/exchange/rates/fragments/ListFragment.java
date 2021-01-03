package com.android.app.currency.exchange.rates.fragments;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.app.currency.exchange.rates.R;
import com.android.app.currency.exchange.rates.VolleySingleton;
import com.android.app.currency.exchange.rates.adapters.InternetExceptionAdapter;
import com.android.app.currency.exchange.rates.adapters.OptionAdapter;
import com.android.app.currency.exchange.rates.items.InternetExceptionItem;
import com.android.app.currency.exchange.rates.items.OptionItem;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
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

    private final ArrayList<OptionItem> optionList = new ArrayList<>();
    private final ArrayList<String> currencyAList = new ArrayList<>();
    private final ArrayList<String> currencyBList = new ArrayList<>();
    private final ArrayList<String> currencyCList = new ArrayList<>();
    private final ArrayList<String> goldList = new ArrayList<>();
    private final ArrayList<String> cryptoList = new ArrayList<>();
    private final ArrayList<String> globalCurrencyList = new ArrayList<>();
    private final ArrayList<InternetExceptionItem> internetExceptionList = new ArrayList<>();
    SimpleDateFormat time_format = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_list, container, false);
        RecyclerView recyclerView = fragmentView.findViewById(R.id.recycler_view);
        AnimationDrawable animationDrawable = (AnimationDrawable) recyclerView.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();
        if (optionList != null || optionList.size() > 0) {
            optionList.clear();
        }

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        if (isNetworkAvailable(Objects.requireNonNull(getContext()))) {
            try {
                optionList.add(new OptionItem(R.drawable.ic_baseline_euro_24, "Kursy walut NBP tabela A w PLN", "Waluty", "Żródło: NBP Web API"));
                optionList.add(new OptionItem(R.drawable.ic_baseline_euro_24, "Kursy walut NBP tabela B w PLN", "Waluty", "Żródło: NBP Web API"));
                optionList.add(new OptionItem(R.drawable.ic_baseline_euro_24, "Kursy walut NBP tabela C w PLN", "Waluty", "Żródło: NBP Web API"));
                optionList.add(new OptionItem(R.drawable.ic_baseline_star_24, "Kursy złota", "Złoto", "Żródło: NBP Web API"));
                optionList.add(new OptionItem(R.drawable.ic_baseline_monetization_on_24, "Kursy kryptowalut", "Kryptowaluty", "Żródło: CoinCap API 2.0"));
                optionList.add(new OptionItem(R.drawable.ic_baseline_euro_24, "Kursy walut globalnych w USD", "Waluty", "Żródło: CoinCap API 2.0"));
                OptionAdapter adapter = new OptionAdapter(optionList, this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
        String dateString, URL;
        JsonArrayRequest jsonArrayRequest;
        JsonObjectRequest jsonObjectRequest;
        RequestQueue requestQueue;
        switch (position) {
            case 0:
                requestQueue = VolleySingleton.getInstance(getContext()).getRequestQueue();
                dateString = validateDateString();
                URL = "https://api.nbp.pl/api/exchangerates/tables/A/" + dateString + "/" + "?format=json";
                jsonArrayRequest = returnJsonCurrencyTableAArrayRequest(URL);
                jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                        2000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(jsonArrayRequest);
                break;
            case 1:
                requestQueue = VolleySingleton.getInstance(getContext()).getRequestQueue();
                dateString = validateDateTableBString();
                URL = "https://api.nbp.pl/api/exchangerates/tables/B/" + dateString + "/" + "?format=json";
                jsonArrayRequest = returnJsonCurrencyTableBArrayRequest(URL);
                jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                        2000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(jsonArrayRequest);
                break;
            case 2:
                requestQueue = VolleySingleton.getInstance(getContext()).getRequestQueue();
                dateString = validateDateString();
                URL = "https://api.nbp.pl/api/exchangerates/tables/C/" + dateString + "/" + "?format=json";
                jsonArrayRequest = returnJsonCurrencyTableCArrayRequest(URL);
                jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                        2000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(jsonArrayRequest);
                break;
            case 3:
                requestQueue = VolleySingleton.getInstance(getContext()).getRequestQueue();
                dateString = validateDateString();
                URL = "https://api.nbp.pl/api/cenyzlota/" + dateString + "/" + "?format=json";
                jsonArrayRequest = returnJsonGoldArrayRequest(URL);
                jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                        2000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(jsonArrayRequest);
                break;
            case 4:
                requestQueue = VolleySingleton.getInstance(getContext()).getRequestQueue();
                URL = "https://api.coincap.io/v2/assets/?format=json";
                jsonObjectRequest = returnJsonCryptoObjectRequest(URL);
                jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                        2000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(jsonObjectRequest);
                break;
            case 5:
                requestQueue = VolleySingleton.getInstance(getContext()).getRequestQueue();
                URL = "https://api.coincap.io/v2/rates/?format=json";
                jsonObjectRequest = returnJsonGlobalCurrencyObjectRequest(URL);
                jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                        2000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(jsonObjectRequest);
                break;
            default:
                Toast.makeText(getContext(), "W tej chwili zawartość nie jest dostępna", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private String validateDateTableBString() {
        Calendar calendar = Calendar.getInstance();
        Date date;
        String dateString;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat sdfHour = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String hour = sdfHour.format(calendar.getTime());
        String start = "00:00";
        String stop = "12:00";
        boolean b = hour.compareTo(start) >= 0 && hour.compareTo(stop) <= 0;
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            calendar.add(Calendar.DATE, -2);
            date = calendar.getTime();
            dateString = dateFormat.format(date);
        } else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            calendar.add(Calendar.DATE, -3);
            date = calendar.getTime();
            dateString = dateFormat.format(date);
        } else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY && (b)) {
            calendar.add(Calendar.DATE, -3);
            date = calendar.getTime();
            dateString = dateFormat.format(date);
        } else if ((calendar.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) && (b)) {
            calendar.add(Calendar.DATE, -2);
            date = calendar.getTime();
            dateString = dateFormat.format(date);
        } else {
            calendar.add(Calendar.DATE, -1);
            date = calendar.getTime();
            dateString = dateFormat.format(date);
        }
        return dateString;
    }

    private String validateDateString() {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        String dateString;
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

    @NotNull
    @Contract("_ -> new")
    private JsonArrayRequest returnJsonCurrencyTableAArrayRequest(String URL) {
        return (JsonArrayRequest) new JsonArrayRequest(Request.Method.GET, URL, null, response -> {
            try {
                if (currencyAList != null || currencyAList.size() > 0) {
                    currencyAList.clear();
                }
                JSONObject ratesList = response.getJSONObject(0);
                JSONArray rates = ratesList.getJSONArray("rates");
                for (int i = 0; i < rates.length(); i++) {
                    JSONObject rate = rates.getJSONObject(i);
                    String currency = rate.getString("currency");
                    String code = rate.getString("code");
                    double mid = rate.getDouble("mid");
                    if (mid < 0.1) mid = 0.01;
                    currencyAList.add(i, currency + ";" + code + ";" + mid);
                }
                CurrencyAFragment currencyAFragment = new CurrencyAFragment();
                Bundle bundle = new Bundle();
                String[] arrayCurrencyList = currencyAList.toArray(new String[0]);
                bundle.putStringArray("currency", arrayCurrencyList);
                currencyAFragment.setArguments(bundle);
                FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_second_container, currencyAFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, Throwable::printStackTrace).setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    private JsonArrayRequest returnJsonCurrencyTableBArrayRequest(String URL) {
        return (JsonArrayRequest) new JsonArrayRequest(Request.Method.GET, URL, null, response -> {
            try {
                if (currencyBList != null || currencyBList.size() > 0) {
                    currencyBList.clear();
                }
                JSONObject ratesList = response.getJSONObject(0);
                JSONArray rates = ratesList.getJSONArray("rates");
                for (int i = 0; i < rates.length(); i++) {
                    JSONObject rate = rates.getJSONObject(i);
                    String currency = rate.getString("currency");
                    String code = rate.getString("code");
                    double mid = rate.getDouble("mid");
                    if (mid < 0.1) mid = 0.01;
                    currencyBList.add(i, currency + ";" + code + ";" + mid);
                }
                CurrencyBFragment currencyBFragment = new CurrencyBFragment();
                Bundle bundle = new Bundle();
                String[] arrayCurrencyList = currencyBList.toArray(new String[0]);
                bundle.putStringArray("currencyTableB", arrayCurrencyList);
                currencyBFragment.setArguments(bundle);
                FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_second_container, currencyBFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, Throwable::printStackTrace).setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    private JsonArrayRequest returnJsonCurrencyTableCArrayRequest(String URL) {
        return (JsonArrayRequest) new JsonArrayRequest(Request.Method.GET, URL, null, response -> {
            try {
                if (currencyCList != null || currencyCList.size() > 0) {
                    currencyCList.clear();
                }
                JSONObject ratesList = response.getJSONObject(0);
                JSONArray rates = ratesList.getJSONArray("rates");
                for (int i = 0; i < rates.length(); i++) {
                    JSONObject rate = rates.getJSONObject(i);
                    String currency = rate.getString("currency");
                    String code = rate.getString("code");
                    double bid = rate.getDouble("bid");
                    if (bid < 0.1) bid = 0.01;
                    double ask = rate.getDouble("ask");
                    if (ask < 0.1) ask = 0.01;
                    currencyCList.add(i, currency + ";" + code + ";" + bid + ";" + ask);
                }
                CurrencyCFragment currencyCFragment = new CurrencyCFragment();
                Bundle bundle = new Bundle();
                String[] arrayCurrencyList = currencyCList.toArray(new String[0]);
                bundle.putStringArray("currencyTableC", arrayCurrencyList);
                currencyCFragment.setArguments(bundle);
                FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_second_container, currencyCFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, Throwable::printStackTrace).setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    private JsonArrayRequest returnJsonGoldArrayRequest(String URL) {
        return (JsonArrayRequest) new JsonArrayRequest(Request.Method.GET, URL, null, response -> {
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
        }, Throwable::printStackTrace).setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    private JsonObjectRequest returnJsonCryptoObjectRequest(String URL) {
        return (JsonObjectRequest) new JsonObjectRequest(Request.Method.GET, URL, null, response -> {
            try {
                if (cryptoList != null || cryptoList.size() > 0) {
                    cryptoList.clear();
                }
                JSONArray cryptoCurrencies = response.getJSONArray("data");
                for (int i = 0; i < cryptoCurrencies.length(); i++) {
                    JSONObject crypto = cryptoCurrencies.getJSONObject(i);
                    String name = crypto.getString("name");
                    String symbol = crypto.getString("symbol");
                    double priceUsd = crypto.getDouble("priceUsd");
                    double changePercent24Hr = crypto.getDouble("changePercent24Hr");
                    cryptoList.add(i, name + ";" + symbol + ";" + priceUsd + ";" + changePercent24Hr);
                }
                CryptoFragment cryptoFragment = new CryptoFragment();
                Bundle bundle = new Bundle();
                String[] arrayCryptoString = cryptoList.toArray(new String[0]);
                bundle.putStringArray("crypto", arrayCryptoString);
                cryptoFragment.setArguments(bundle);
                FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_second_container, cryptoFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, Throwable::printStackTrace).setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    private JsonObjectRequest returnJsonGlobalCurrencyObjectRequest(String URL) {
        return (JsonObjectRequest) new JsonObjectRequest(Request.Method.GET, URL, null, response -> {
            try {
                if (globalCurrencyList != null || globalCurrencyList.size() > 0) {
                    globalCurrencyList.clear();
                }
                JSONArray globalCurrencies = response.getJSONArray("data");
                for (int i = 0; i < globalCurrencies.length(); i++) {
                    JSONObject globalCurrency = globalCurrencies.getJSONObject(i);
                    String id = globalCurrency.getString("id");
                    String symbol = globalCurrency.getString("symbol");
                    double rateUsd = globalCurrency.getDouble("rateUsd");
                    String currencySymbol = globalCurrency.getString("currencySymbol");
                    globalCurrencyList.add(i, id + ";" + symbol + ";" + rateUsd + ";" + currencySymbol);
                }
                GlobalFragment globalFragment = new GlobalFragment();
                Bundle bundle = new Bundle();
                String[] arrayGlobalString = globalCurrencyList.toArray(new String[0]);
                bundle.putStringArray("global", arrayGlobalString);
                globalFragment.setArguments(bundle);
                FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_second_container, globalFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, Throwable::printStackTrace).setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
