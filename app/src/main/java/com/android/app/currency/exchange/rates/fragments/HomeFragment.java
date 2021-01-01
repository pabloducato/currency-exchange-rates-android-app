package com.android.app.currency.exchange.rates.fragments;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.app.currency.exchange.rates.R;
import com.android.app.currency.exchange.rates.adapters.NavigationAdapter;
import com.android.app.currency.exchange.rates.items.NavigationItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Objects;

public class HomeFragment extends Fragment implements NavigationAdapter.OnItemClickListener {

    public BottomNavigationView bottomNavigationView;
    private final ArrayList<NavigationItem> navigationList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_home, container, false);
        RecyclerView recyclerView = fragmentView.findViewById(R.id.home_recycler_view);
        AnimationDrawable animationDrawable = (AnimationDrawable) recyclerView.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();
        if (navigationList != null || navigationList.size() > 0) {
            navigationList.clear();
        }

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        bottomNavigationView = Objects.requireNonNull(getActivity()).findViewById(R.id.bottom_navigation);

        navigationList.add(new NavigationItem(R.drawable.ic_baseline_qr_code_scanner_24, "Skaner kodów QR"));
        navigationList.add(new NavigationItem(R.drawable.ic_baseline_calculate_24, "Kalkulator"));

        NavigationAdapter adapter = new NavigationAdapter(navigationList, this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        super.onCreate(savedInstanceState);
        return fragmentView;
    }

    @Override
    public void onItemClick(int position) {
        switch (position) {
            case 0:
                ScannerFragment scannerFragment = new ScannerFragment();
                FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_second_container, scannerFragment, "scanner");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case 1:
                CalculatorFragment calculatorFragment = new CalculatorFragment();
                FragmentManager fragmentManagerCalc = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
                FragmentTransaction fragmentTransactionCalc = fragmentManagerCalc.beginTransaction();
                fragmentTransactionCalc.replace(R.id.fragment_second_container, calculatorFragment, "calculator");
                fragmentTransactionCalc.addToBackStack(null);
                fragmentTransactionCalc.commit();
                break;
        }
    }

}
