package com.example.headsup;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private FlipTracker tracker = new FlipTracker();
    private TextView resultText, headsText, tailsText;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Apply night mode preference before view inflation
        boolean night = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("night_mode", false);
        AppCompatDelegate.setDefaultNightMode(
                night ? AppCompatDelegate.MODE_NIGHT_YES
                        : AppCompatDelegate.MODE_NIGHT_NO
        );

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);

        resultText = findViewById(R.id.resultText);
        headsText  = findViewById(R.id.headsCountText);
        tailsText  = findViewById(R.id.tailsCountText);

        // Restore state if available
        if (savedInstanceState != null) {
            int h = savedInstanceState.getInt("heads");
            int t = savedInstanceState.getInt("tails");
            String r = savedInstanceState.getString("result");

            tracker.setHeads(h);
            tracker.setTails(t);

            resultText.setText(r);
            headsText.setText("Heads: " + h);
            tailsText.setText("Tails: " + t);
        }

        ExtendedFloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> flip());

        // Auto-flip on start if enabled
        boolean autoFlip = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("auto_flip", false);
        if (savedInstanceState == null && autoFlip) {
            flip();
        }
    }

    @SuppressLint("SetTextI18n")
    private void flip() {
        boolean isHeads = tracker.flip();
        resultText.setText(isHeads ? R.string.heads : R.string.tails);
        headsText.setText("Heads: " + tracker.getHeads());
        tailsText.setText("Tails: " + tracker.getTails());
    }

    @SuppressLint("SetTextI18n")
    private void resetGame() {
        tracker.reset();
        resultText.setText(R.string.ResultOriginalText);
        headsText.setText("Heads: 0");
        tailsText.setText("Tails: 0");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_reset) {
            resetGame();
            return true;
        } else if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        } else if (id == R.id.action_about) {
            startActivity(new Intent(this, AboutActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle out) {
        super.onSaveInstanceState(out);
        out.putInt("heads", tracker.getHeads());
        out.putInt("tails", tracker.getTails());
        out.putString("result", resultText.getText().toString());
    }
}