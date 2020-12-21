package com.android.app.currency.exchange.rates;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.app.currency.exchange.rates.fragments.HomeFragment;
import com.android.app.currency.exchange.rates.fragments.InfoFragment;
import com.android.app.currency.exchange.rates.fragments.ListFragment;
import com.android.app.currency.exchange.rates.fragments.MessagesFragment;
import com.android.app.currency.exchange.rates.fragments.SettingsFragment;
import com.android.app.currency.exchange.rates.models.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    public static final String GLOBAL_SHARED_PREFERENCES_ORIENTATION = "orientationSharedPreferences";
    public static final String GLOBAL_SHARED_PREFERENCES_DARK_MODE = "darkModeSharedPreferences";
    public String backgroundTheme;
    public String screenOrientation;
    public String BACK_THEME;
    public String SCREEN_ORIENTATION;

    private DrawerLayout drawerLayout;
    public BottomNavigationView bottomNavigationView;
    public BottomNavigationView topNavigationView;
    public NavigationView navigationView;
    public LinearLayout linearLayout;
    private DatabaseReference databaseReference;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        updateUserSettings();
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_Currencyexchangeratesandroidapp);
        setContentView(R.layout.activity_main);
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        assert firebaseUser != null;
        userId = firebaseUser.getUid();

        navigationView = findViewById(R.id.navigation_view);
        navigationView.getMenu().getItem(0).setChecked(true);
        navigationView.setNavigationItemSelectedListener(onNavigationItemSelectedListener);

        linearLayout = navigationView.getHeaderView(0).findViewById(R.id.linear_layout);
        TextView logout = linearLayout.findViewById(R.id.text_view_logout);
        TextView deleteAccount = linearLayout.findViewById(R.id.text_view_delete_account);
        TextView emailTextView = linearLayout.findViewById(R.id.email_address);
        TextView fullNameTextView = linearLayout.findViewById(R.id.full_name);

        if (isNetworkAvailable(Objects.requireNonNull(getApplicationContext()))) {
            loadUserInformation(emailTextView, fullNameTextView, firebaseUser);
        } else {
            loadUserInformation(emailTextView, fullNameTextView, firebaseUser);
        }

        logout.setOnClickListener(v -> {
            try {
                logout.setPressed(true);
                Handler handler = new Handler();
                handler.postDelayed(this::logoutUser, 100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        deleteAccount.setOnClickListener(v -> {
            try {
                deleteAccount.setPressed(true);
                Handler handler = new Handler();
                handler.postDelayed(() -> deleteUserAccount(firebaseUser), 100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        boolean firstStart = sharedPreferences.getBoolean("firstStart", true);
        if (firstStart) {
            showStartDialog();
        }
        topNavigationView = findViewById(R.id.top_navigation);
        topNavigationView.getMenu().setGroupCheckable(0, false, true);
        topNavigationView.setOnNavigationItemSelectedListener(onBottomNavigationItemSelectedListener);
        topNavigationView.getMenu().findItem(R.id.nav_menu_open).setEnabled(true);
        topNavigationView.getMenu().findItem(R.id.nav_menu_open).setChecked(false);
        topNavigationView.getMenu().findItem(R.id.nav_menu_return).setEnabled(true);
        topNavigationView.getMenu().findItem(R.id.nav_menu_return).setChecked(false);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(onBottomNavigationItemSelectedListener);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, new HomeFragment());
        fragmentTransaction.commit();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            moveTaskToBack(true);
            finish();
        }
    }

    public void returnFrom() {
        super.onBackPressed();
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener onBottomNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {

                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            navigationView.setCheckedItem(R.id.drawer_navigation_home);
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_second_container, new HomeFragment()).commit();
                            break;
                        case R.id.nav_view_list:
                            navigationView.setCheckedItem(R.id.drawer_navigation_list);
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_second_container, new ListFragment()).commit();
                            break;
                        case R.id.nav_settings:
                            navigationView.setCheckedItem(R.id.drawer_navigation_settings);
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_second_container, new SettingsFragment()).commit();
                            break;
                        case R.id.nav_info:
                            navigationView.setCheckedItem(R.id.drawer_navigation_info);
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_second_container, new InfoFragment()).commit();
                            break;
                        case R.id.nav_notifications:
                            navigationView.setCheckedItem(R.id.drawer_navigation_notifications);
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_second_container, new MessagesFragment()).commit();
                            break;
                        case R.id.nav_menu_open:
                            drawerLayout.openDrawer(GravityCompat.START);
                            break;
                        case R.id.nav_menu_return:
                            returnFrom();
                            break;
                    }
                    return true;
                }
            };

    private final NavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener =
            new NavigationView.OnNavigationItemSelectedListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.drawer_navigation_home:
                            bottomNavigationView.setSelectedItemId(R.id.nav_home);
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_second_container, new HomeFragment()).commit();
                            break;
                        case R.id.drawer_navigation_list:
                            bottomNavigationView.setSelectedItemId(R.id.nav_view_list);
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_second_container, new ListFragment()).commit();
                            break;
                        case R.id.drawer_navigation_settings:
                            bottomNavigationView.setSelectedItemId(R.id.nav_settings);
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_second_container, new SettingsFragment()).commit();
                            break;
                        case R.id.drawer_navigation_info:
                            bottomNavigationView.setSelectedItemId(R.id.nav_info);
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_second_container, new InfoFragment()).commit();
                            break;
                        case R.id.drawer_navigation_notifications:
                            bottomNavigationView.setSelectedItemId(R.id.nav_notifications);
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_second_container, new MessagesFragment()).commit();
                            break;
                    }
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                }
            };

    private void loadUserInformation(TextView emailTextView, TextView fullNameTextView, FirebaseUser firebaseUser) {
        databaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (firebaseUser != null) {
                    User user = snapshot.getValue(User.class);
                    if (user != null) {
                        String email = firebaseUser.getEmail();
                        String fullName = user.firstName + " " + user.lastName;
                        emailTextView.setText(email);
                        fullNameTextView.setText(fullName);
                        fullNameTextView.setText(fullName);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Coś poszło nie tak!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void logoutUser() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getApplicationContext(), AuthorizationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void deleteUserAccount(FirebaseUser firebaseUser) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("Czy na pewno chcesz usunąć swoje konto?");
        dialog.setMessage("Ta operacja spowoduje nieodwracalne usunięcie Twojego konta z systemu!");
        dialog.setPositiveButton("Usuń", (dialog1, which) -> firebaseUser.delete().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(MainActivity.this, "Twoje konto zostało usunięte.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, AuthorizationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(MainActivity.this, "Operacja usunięcia Twojego konta nie powiodła się!", Toast.LENGTH_LONG).show();
            }
        }));
        dialog.setNegativeButton("Anuluj", (dialog12, which) -> dialog12.dismiss());
        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }

    private void loadScreenOrientationSettings() {
        SharedPreferences sharedPreferences = getSharedPreferences(GLOBAL_SHARED_PREFERENCES_ORIENTATION, Context.MODE_PRIVATE);
        screenOrientation = sharedPreferences.getString(SCREEN_ORIENTATION, "");
    }

    private void loadBackgroundThemeSettings() {
        SharedPreferences sharedPreferences = getSharedPreferences(GLOBAL_SHARED_PREFERENCES_DARK_MODE, Context.MODE_PRIVATE);
        backgroundTheme = sharedPreferences.getString(BACK_THEME, "");
    }

    private void updateUserSettings() {
        loadScreenOrientationSettings();
        loadBackgroundThemeSettings();

        if (screenOrientation != null && screenOrientation.equals("SCREEN_ORIENTATION_PORTRAIT")) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else if (screenOrientation != null && screenOrientation.equals("SCREEN_ORIENTATION_LANDSCAPE")) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        if (backgroundTheme != null && backgroundTheme.equals("MODE_NIGHT_NO")) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else if (backgroundTheme != null && backgroundTheme.equals("MODE_NIGHT_YES")) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    private void showStartDialog() {
        new AlertDialog.Builder(this)
                .setTitle("One Time Dialog")
                .setMessage("This should only be shown once")
                .setPositiveButton("ok", (dialogInterface, i) -> dialogInterface.dismiss())
                .create().show();
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

}