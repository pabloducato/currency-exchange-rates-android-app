package com.android.app.currency.exchange.rates;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.app.currency.exchange.rates.fragments.ResetPasswordFragment;

public class ResetPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        getSupportFragmentManager().beginTransaction().replace(R.id.reset_password_empty_container, new ResetPasswordFragment()).commit();
    }
}
