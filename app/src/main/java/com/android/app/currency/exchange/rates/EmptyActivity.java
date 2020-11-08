package com.android.app.currency.exchange.rates;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.app.currency.exchange.rates.fragments.LoginFragment;

public class EmptyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);
        getSupportFragmentManager().beginTransaction().replace(R.id.empty_container, new LoginFragment()).commit();
    }

}