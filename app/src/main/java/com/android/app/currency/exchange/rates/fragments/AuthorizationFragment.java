package com.android.app.currency.exchange.rates.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.app.currency.exchange.rates.R;

public class AuthorizationFragment extends Fragment implements View.OnClickListener {

    TextView fragment_login, fragment_register;
    FrameLayout fragment_authorization;

    FragmentTransaction fragmentTransaction;
    Fragment fragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_authorization, container, false);
        super.onCreateView(inflater, container, savedInstanceState);
        fragment_login = fragmentView.findViewById(R.id.fragment_login);
        fragment_register = fragmentView.findViewById(R.id.fragment_register);
        fragment_authorization = fragmentView.findViewById(R.id.fragment_authorization_area);

        fragment_login.setTextColor(Color.BLACK);
        fragment_login.setTextSize(24);
        fragment_register.setTextColor(Color.GRAY);
        fragment_register.setTextSize(20);

        fragment = new LoginFragment();
        assert getFragmentManager() != null;
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_authorization_area, fragment, "LOGIN_FRAGMENT");
        fragmentTransaction.commit();

        fragment_login.setOnClickListener(this);
        fragment_register.setOnClickListener(this);

        return fragmentView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_login:
                fragment = new LoginFragment();
                assert getFragmentManager() != null;
                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_authorization_area, fragment, "LOGIN_FRAGMENT");
                fragmentTransaction.commit();
                fragment_login.setTextColor(Color.BLACK);
                fragment_login.setTextSize(24);
                fragment_register.setTextColor(Color.GRAY);
                fragment_register.setTextSize(20);
                break;
            case R.id.fragment_register:
                fragment = new RegisterFragment();
                assert getFragmentManager() != null;
                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_authorization_area, fragment, "REGISTER_FRAGMENT");
                fragmentTransaction.commit();
                fragment_login.setTextColor(Color.GRAY);
                fragment_login.setTextSize(20);
                fragment_register.setTextColor(Color.BLACK);
                fragment_register.setTextSize(24);
                break;
        }

    }
}
