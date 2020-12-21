package com.android.app.currency.exchange.rates.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.net.Uri;
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
import com.android.app.currency.exchange.rates.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class RegisterFragment extends Fragment {

    protected TextView fragment_login, fragment_register;
    protected Fragment fragment;
    protected FirebaseAuth mAuth;
    protected EditText editTextFirstName, editTextLastName, editTextEmail, editTextPassword, editTextConfirmPassword;
    protected ProgressBar registerProgressBar;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;
    public static final String GLOBAL_SHARED_PREFERENCES_CREDENTIALS = "credentialsDB";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_register, container, false);
        mAuth = FirebaseAuth.getInstance();
        editTextFirstName = fragmentView.findViewById(R.id.register_first_name);
        editTextLastName = fragmentView.findViewById(R.id.register_last_name);
        editTextEmail = fragmentView.findViewById(R.id.register_email);
        editTextPassword = fragmentView.findViewById(R.id.register_password);
        editTextConfirmPassword = fragmentView.findViewById(R.id.register_confirm_password);
        registerProgressBar = fragmentView.findViewById(R.id.registerProgressBar);
        TextView serviceTextView = fragmentView.findViewById(R.id.text_view_register_2);
        TextView register = fragmentView.findViewById(R.id.text_view_register_1);
        serviceTextView.setPaintFlags(serviceTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        View authorizationFragmentView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_authorization, container, false);
        fragment_login = authorizationFragmentView.findViewById(R.id.fragment_login);
        fragment_register = authorizationFragmentView.findViewById(R.id.fragment_register);
        register.setOnClickListener(v -> {
            try {
                register.setPressed(true);
                registerUser();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        serviceTextView.setPaintFlags(serviceTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        serviceTextView.setOnClickListener(v -> {
            String url = "https://www.linkedin.com/in/paweł-k-597919142";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        });

        return fragmentView;
    }

    private void registerUser() {
        String firstName = editTextFirstName.getText().toString().trim();
        String lastName = editTextLastName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();
        if (firstName.isEmpty()) {
            editTextFirstName.setError("Należy podać swoje imię!");
            editTextFirstName.requestFocus();
            return;
        }

        if (lastName.isEmpty()) {
            editTextLastName.setError("Należy podać swoje nazwisko!");
            editTextLastName.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            editTextEmail.setError("Należy podać swój adres E-Mail!");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Wprowadź prawidłowy adres E-Mail!");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Należy podać swoje hasło!");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Hasło musi zawierać minimum 6 znaków!");
            editTextPassword.requestFocus();
            return;
        }

        if (confirmPassword.isEmpty()) {
            editTextConfirmPassword.setError("Należy potwierdzić swoje hasło!");
            editTextConfirmPassword.requestFocus();
        } else if (!confirmPassword.equals(password)) {
            editTextConfirmPassword.setError("Hasła nie są identyczne!");
            editTextConfirmPassword.requestFocus();
        } else {
            registerProgressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {

                    sharedPreferences = Objects.requireNonNull(getContext()).getSharedPreferences(GLOBAL_SHARED_PREFERENCES_CREDENTIALS, Context.MODE_PRIVATE);
                    sharedPreferencesEditor = sharedPreferences.edit();
                    String myArray = email + ";" + firstName + ";" + lastName;
                    Set<String> mySet = new HashSet<>(Collections.singletonList(myArray));
                    sharedPreferences.edit().putStringSet(email, mySet).apply();
                    sharedPreferencesEditor.apply();

                    User user = new User(firstName, lastName, email);
                    FirebaseDatabase.getInstance().getReference("Users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).setValue(user).addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            Intent intent = new Intent(getActivity(), LoginAfterRegistrationActivity.class);
                            startActivity(intent);
                            Toast.makeText(getContext(), "Rejestracja przebiegła pomyślnie", Toast.LENGTH_LONG).show();
                            editTextFirstName.setText("");
                            editTextLastName.setText("");
                            editTextEmail.setText("");
                            editTextPassword.setText("");
                            editTextConfirmPassword.setText("");
                            Objects.requireNonNull(getActivity()).finish();
                        } else {
                            Toast.makeText(getContext(), "Rejestracja nieudana! Spróbuj ponownie!", Toast.LENGTH_LONG).show();
                        }
                        registerProgressBar.setVisibility(View.GONE);
                    });
                } else {
                    Toast.makeText(getContext(), "Rejestracja nieudana! Spróbuj ponownie!", Toast.LENGTH_LONG).show();
                    registerProgressBar.setVisibility(View.GONE);
                }
            });
        }
    }
}
