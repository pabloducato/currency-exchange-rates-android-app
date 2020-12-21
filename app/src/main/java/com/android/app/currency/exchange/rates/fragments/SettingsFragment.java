package com.android.app.currency.exchange.rates.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.app.currency.exchange.rates.R;
import com.android.app.currency.exchange.rates.adapters.SettingAdapter;
import com.android.app.currency.exchange.rates.items.SettingItem;

import java.util.ArrayList;
import java.util.Objects;

public class SettingsFragment extends Fragment implements SettingAdapter.OnItemClickListener {

    private final ArrayList<SettingItem> settingsList = new ArrayList<>();
    public String selectedAppScreenOrientation;
    public String selectedAppBackgroundMode;
    public static final String GLOBAL_SHARED_PREFERENCES_ORIENTATION = "orientationSharedPreferences";
    public static final String GLOBAL_SHARED_PREFERENCES_DARK_MODE = "darkModeSharedPreferences";
    public String backgroundTheme = null;
    public String screenOrientation = null;
    private String BACK_THEME;
    private String SCREEN_ORIENTATION;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_settings, container, false);
        RecyclerView recyclerView = fragmentView.findViewById(R.id.settings_recycler_view);
        AnimationDrawable animationDrawable = (AnimationDrawable) recyclerView.getBackground();
        animationDrawable.setEnterFadeDuration(1000);
        animationDrawable.setExitFadeDuration(1000);
        animationDrawable.start();
        if (settingsList != null || settingsList.size() > 0) {
            settingsList.clear();
        }

        try {
            settingsList.add(new SettingItem(R.drawable.ic_baseline_highlight_24, "Dark mode"));
            settingsList.add(new SettingItem(R.drawable.ic_baseline_screen_rotation_24, "Orientacja ekranu"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        SettingAdapter adapter = new SettingAdapter(settingsList, this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        return fragmentView;
    }

    @Override
    public void onItemClick(int position) {
        switch (position) {
            case 0:
                showDarkModeDialog();
                break;
            case 1:
                showScreenOrientationDialog();
                break;
        }
    }

    private void saveScreenOrientationSettings() {
        SharedPreferences sharedPreferences = Objects.requireNonNull(getContext()).getSharedPreferences(GLOBAL_SHARED_PREFERENCES_ORIENTATION, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (selectedAppScreenOrientation.equals("Pionowa")) {
            Objects.requireNonNull(getActivity()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            editor.putString(SCREEN_ORIENTATION, "SCREEN_ORIENTATION_PORTRAIT");
        }
        if (selectedAppScreenOrientation.equals("Pozioma")) {
            Objects.requireNonNull(getActivity()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            editor.putString(SCREEN_ORIENTATION, "SCREEN_ORIENTATION_LANDSCAPE");
        }
        editor.apply();
        Toast.makeText(getContext(), "Zmiany zostały zapisane", Toast.LENGTH_SHORT).show();
    }

    private void showScreenOrientationDialog() {
        String[] appOrientationModes = {"Pionowa", "Pozioma"};
        loadScreenOrientationSettings();
        int flag = 0;
        if (!screenOrientation.equals("")) {
            selectedAppScreenOrientation = screenOrientation.equals("SCREEN_ORIENTATION_PORTRAIT") ? appOrientationModes[0] : appOrientationModes[1];
            flag = screenOrientation.equals("SCREEN_ORIENTATION_PORTRAIT") ? 0 : 1;
        } else {
            selectedAppScreenOrientation = appOrientationModes[0];
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Wybierz orientację ekranu");
        builder.setSingleChoiceItems(appOrientationModes, flag, (dialog, which) -> {
            selectedAppScreenOrientation = appOrientationModes[which];
        });
        builder.setPositiveButton("Zatwierdź zmiany", (dialog, which) -> {
            saveScreenOrientationSettings();
            dialog.dismiss();
        });
        builder.setNegativeButton("Anuluj", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    private void saveDarkModeSettings() {
        SharedPreferences sharedPreferences = Objects.requireNonNull(getContext()).getSharedPreferences(GLOBAL_SHARED_PREFERENCES_DARK_MODE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (selectedAppBackgroundMode.equals("Jasny")) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            editor.putString(BACK_THEME, "MODE_NIGHT_NO");
        }
        if (selectedAppBackgroundMode.equals("Ciemny")) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            editor.putString(BACK_THEME, "MODE_NIGHT_YES");
        }
        editor.apply();
        Toast.makeText(getContext(), "Zmiany zostały zapisane", Toast.LENGTH_SHORT).show();
    }

    private void showDarkModeDialog() {
        String[] appBackgroundModes = {"Jasny", "Ciemny"};
        loadBackgroundThemeSettings();
        int flag = 0;
        if (!backgroundTheme.equals("")) {
            selectedAppBackgroundMode = backgroundTheme.equals("MODE_NIGHT_NO") ? appBackgroundModes[0] : appBackgroundModes[1];
            flag = backgroundTheme.equals("MODE_NIGHT_NO") ? 0 : 1;
        } else {
            selectedAppBackgroundMode = appBackgroundModes[0];
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Wybierz motyw aplikacji");
        builder.setSingleChoiceItems(appBackgroundModes, flag, (dialog, which) -> {
            selectedAppBackgroundMode = appBackgroundModes[which];
        });
        builder.setPositiveButton("Zatwierdź zmiany", (dialog, which) -> {
            saveDarkModeSettings();
            dialog.dismiss();
        });
        builder.setNegativeButton("Anuluj", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    private void loadScreenOrientationSettings() {
        SharedPreferences sharedPreferences = Objects.requireNonNull(getContext()).getSharedPreferences(GLOBAL_SHARED_PREFERENCES_ORIENTATION, Context.MODE_PRIVATE);
        screenOrientation = sharedPreferences.getString(SCREEN_ORIENTATION, "");
    }

    private void loadBackgroundThemeSettings() {
        SharedPreferences sharedPreferences = Objects.requireNonNull(getContext()).getSharedPreferences(GLOBAL_SHARED_PREFERENCES_DARK_MODE, Context.MODE_PRIVATE);
        backgroundTheme = sharedPreferences.getString(BACK_THEME, "");
    }

}
