package com.android.app.currency.exchange.rates;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.android.app.currency.exchange.rates.fragments.HomeFragment;
import com.android.app.currency.exchange.rates.fragments.InfoFragment;
import com.android.app.currency.exchange.rates.fragments.ListFragment;
import com.android.app.currency.exchange.rates.fragments.NotificationsFragment;
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

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    public BottomNavigationView bottomNavigationView;
    public BottomNavigationView topNavigationView;
    public NavigationView navigationView;
    public LinearLayout linearLayout;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        userId = firebaseUser.getUid();
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
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        navigationView.getMenu().getItem(0).setChecked(true);
        navigationView.setNavigationItemSelectedListener(onNavigationItemSelectedListener);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener onBottomNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    linearLayout = findViewById(R.id.linear_layout);
                    TextView logout = linearLayout.findViewById(R.id.text_view_logout);
                    TextView emailTextView = linearLayout.findViewById(R.id.email_address);
                    TextView fullNameTextView = linearLayout.findViewById(R.id.full_name);
                    databaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            User user = snapshot.getValue(User.class);
                            if (user != null) {
                                String email = user.email;
                                String fullName = user.firstName + " " + user.lastName;
                                emailTextView.setText(email);
                                fullNameTextView.setText(fullName);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(getApplicationContext(), "Coś poszło nie tak!", Toast.LENGTH_LONG).show();

                        }
                    });
                    logout.setOnClickListener(v -> {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getApplicationContext(), AuthorizationActivity.class));
                    });
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
                        case R.id.nav_notifications:
                            navigationView.setCheckedItem(R.id.drawer_navigation_notifications);
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_second_container, new NotificationsFragment()).commit();
                            break;
                        case R.id.nav_info:
                            navigationView.setCheckedItem(R.id.drawer_navigation_info);
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_second_container, new InfoFragment()).commit();
                            break;
                        case R.id.nav_menu_open:
                            drawerLayout.openDrawer(GravityCompat.START);
                            break;
                        case R.id.nav_menu_return:
                            onBackPressed();
                            break;
                    }
                    return true;
                }
            };

    private final NavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener =
            new NavigationView.OnNavigationItemSelectedListener() {
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
                        case R.id.drawer_navigation_notifications:
                            bottomNavigationView.setSelectedItemId(R.id.nav_notifications);
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_second_container, new NotificationsFragment()).commit();
                            break;
                        case R.id.drawer_navigation_info:
                            bottomNavigationView.setSelectedItemId(R.id.nav_info);
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_second_container, new InfoFragment()).commit();
                            break;
                    }
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                }
            };

    private void showStartDialog() {
        new AlertDialog.Builder(this)
                .setTitle("One Time Dialog")
                .setMessage("This should only be shown once")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create().show();
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
    }

}