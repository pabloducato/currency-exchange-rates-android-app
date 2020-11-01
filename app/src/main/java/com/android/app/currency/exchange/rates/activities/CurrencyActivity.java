package com.android.app.currency.exchange.rates.activities;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.app.currency.exchange.rates.R;
import com.android.app.currency.exchange.rates.fragments.CurrencyFragment;
import com.android.app.currency.exchange.rates.fragments.HomeFragment;
import com.android.app.currency.exchange.rates.fragments.InfoFragment;
import com.android.app.currency.exchange.rates.fragments.ListFragment;
import com.android.app.currency.exchange.rates.fragments.NotificationsFragment;
import com.android.app.currency.exchange.rates.fragments.SettingsFragment;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class CurrencyActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    public BottomNavigationView bottomNavigationView;
    public BottomNavigationView topNavigationView;
    public NavigationView navigationView;

    public RequestQueue requestQueue;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<String> currencyList = getIntent().getStringArrayListExtra("currency");
        String[] arrayCurrencyList = currencyList.toArray(new String[0]);
        setContentView(R.layout.activity_currencies);
        topNavigationView = findViewById(R.id.top_navigation);
        topNavigationView.getMenu().setGroupCheckable(0, false, true);
        topNavigationView.setOnNavigationItemSelectedListener(onBottomNavigationItemSelectedListener);
        topNavigationView.getMenu().findItem(R.id.nav_menu_open).setChecked(false);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().getItem(1).setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(onBottomNavigationItemSelectedListener);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        navigationView.getMenu().getItem(1).setChecked(true);
        navigationView.setNavigationItemSelectedListener(onNavigationItemSelectedListener);
        requestQueue = Volley.newRequestQueue(this);
        CurrencyFragment currencyFragment = new CurrencyFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArray("currency", arrayCurrencyList);
        currencyFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, currencyFragment).commit();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        super.onBackPressed();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onBottomNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
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
                    }
                    return true;
                }
            };

    private NavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener =
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

}
