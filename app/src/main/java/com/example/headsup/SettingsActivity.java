package com.example.headsup;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = findViewById(R.id.toolbar_settings);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Settings");
            getSupportActionBar().setDisplayHomeAsUpEnabled(false); // Disable default back button
        }

        SwitchCompat switchNightMode = findViewById(R.id.switch_night_mode);
        SwitchCompat switchAutoFlip = findViewById(R.id.switch_auto_flip);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean nightModeOn = prefs.getBoolean("night_mode", false);
        boolean autoFlipOn = prefs.getBoolean("auto_flip", false);

        switchNightMode.setChecked(nightModeOn);
        switchAutoFlip.setChecked(autoFlipOn);

        switchNightMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs.edit().putBoolean("night_mode", isChecked).apply();
            AppCompatDelegate.setDefaultNightMode(
                    isChecked ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
            recreate();
        });

        switchAutoFlip.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs.edit().putBoolean("auto_flip", isChecked).apply();
        });

        // FAB Back button
        FloatingActionButton fabBack = findViewById(R.id.fab_back);
        fabBack.setOnClickListener(v -> finish());
    }
}