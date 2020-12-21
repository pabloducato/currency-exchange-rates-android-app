package com.android.app.currency.exchange.rates.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.app.currency.exchange.rates.LoginAfterRegistrationActivity;
import com.android.app.currency.exchange.rates.R;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordFragment extends Fragment {

    protected EditText editTextEmail;
    protected ProgressBar resetPasswordProgressBar;
    FirebaseAuth auth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_reset_password, container, false);
        TextView resetPassword = fragmentView.findViewById(R.id.text_view_forgot_password_1);
        editTextEmail = fragmentView.findViewById(R.id.reset_password_email);
        resetPasswordProgressBar = fragmentView.findViewById(R.id.resetPasswordProgressBar);
        auth = FirebaseAuth.getInstance();
        resetPassword.setOnClickListener(v -> resetPassword());

        super.onCreate(savedInstanceState);
        return fragmentView;
    }

    private void resetPassword() {
        String email = editTextEmail.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmail.setError("Należy podać swój adres E-Mail!");
            editTextEmail.requestFocus();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Wprowadź prawidłowy adres E-Mail!");
            editTextEmail.requestFocus();
        } else {
            resetPasswordProgressBar.setVisibility(View.VISIBLE);
            auth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "Sprawdź swój adres E-Mail, aby zresetować swoje hasło!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getActivity(), LoginAfterRegistrationActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    editTextEmail.setText("");
                    getActivity().finish();
                } else {
                    Toast.makeText(getContext(), "Spróbuj ponownie! Coś poszło nie tak!", Toast.LENGTH_LONG).show();
                }
                resetPasswordProgressBar.setVisibility(View.GONE);
            });
        }
    }
}
