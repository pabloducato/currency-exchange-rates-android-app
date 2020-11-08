package com.android.app.currency.exchange.rates.fragments;

import android.content.Intent;
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

import com.android.app.currency.exchange.rates.AuthorizationActivity;
import com.android.app.currency.exchange.rates.MainActivity;
import com.android.app.currency.exchange.rates.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment {

    protected FirebaseAuth mAuth;
    protected EditText editTextEmail, editTextPassword;
    protected ProgressBar loginProgressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_login, container, false);
        mAuth = FirebaseAuth.getInstance();
        editTextEmail = (EditText) fragmentView.findViewById(R.id.login_email);
        editTextPassword = (EditText) fragmentView.findViewById(R.id.login_password);
        loginProgressBar = (ProgressBar) fragmentView.findViewById(R.id.loginProgressBar);
        TextView serviceTextView = fragmentView.findViewById(R.id.text_view_login_2);
        serviceTextView.setPaintFlags(serviceTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        TextView login = fragmentView.findViewById(R.id.text_view_login_1);
        TextView forgotPassword = fragmentView.findViewById(R.id.text_view_login_forgot_password);
        forgotPassword.setPaintFlags(forgotPassword.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        login.setOnClickListener(v -> {
            try {
                loginUser();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        forgotPassword.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        });
        serviceTextView.setOnClickListener(v -> {
            String url = "https://www.google.com";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        });
        return fragmentView;
    }

    private void loginUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

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

        loginProgressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                if (firebaseUser.isEmailVerified()) {
                    startActivity(new Intent(getContext(), MainActivity.class));
                    Toast.makeText(getContext(), "Logowanie powiodło się!", Toast.LENGTH_LONG).show();
                    editTextEmail.setText("");
                    editTextPassword.setText("");
//                    LoginFragment loginFragment = (LoginFragment) getFragmentManager().findFragmentByTag("LOGIN_FRAGMENT");
//                    RegisterFragment registerFragment = (RegisterFragment) getFragmentManager().findFragmentByTag("REGISTER_FRAGMENT");
//                    if (loginFragment != null && loginFragment.isVisible()) {
//                        getFragmentManager().beginTransaction().remove(loginFragment).commit();
//                        assert registerFragment != null;
//                        getFragmentManager().beginTransaction().remove(registerFragment).commit();
                } else {
                    firebaseUser.sendEmailVerification();
                    Toast.makeText(getContext(), "Sprawdź swojego maila, aby zweryfikować utworzone konto!", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getContext(), "Logowanie nie powiodło się! Sprawdź poprawność danych!", Toast.LENGTH_LONG).show();
            }
            loginProgressBar.setVisibility(View.GONE);
        });

    }

}
