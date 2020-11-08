package com.android.app.currency.exchange.rates;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.app.currency.exchange.rates.fragments.AuthorizationFragment;

public class AuthorizationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_authorization_container, new AuthorizationFragment(), "AUTHORIZATION_FRAGMENT").commit();
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
            finish();
            moveTaskToBack(true);
        }
    }
}
