package com.android.app.currency.exchange.rates;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.app.currency.exchange.rates.fragments.AuthorizationFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthorizationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else {
            setContentView(R.layout.activity_authorization);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_authorization_container, new AuthorizationFragment(), "AUTHORIZATION_FRAGMENT").commit();
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        finish();
    }
}
