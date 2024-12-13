package com.example.rentalapp;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardWorkSpaceActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard_work_space);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String userLogin = getIntent().getStringExtra("userlogin");
        Toast.makeText(getApplicationContext(), "User: " + userLogin + " authenticated successfully!", Toast.LENGTH_SHORT).show();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_view);
            bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            Fragment selectedFragment = null;

                for (int i = 0; i < bottomNavigationView.getMenu().size(); i++) {
                    bottomNavigationView.findViewById(bottomNavigationView.getMenu().getItem(i).getItemId())
                            .setBackgroundColor(getResources().getColor(R.color.light_gray));
                }

            if (itemId == R.id.nav_dashboard) {
                selectedFragment = new DashboardActivity();
            } else if (itemId == R.id.nav_settings) {
                selectedFragment = new SettingsActivity();
            } else if (itemId == R.id.nav_tenants) {
                selectedFragment = new TenantsActivity();
            } else if (itemId == R.id.nav_properties) {
                selectedFragment = new PropertiesActivity();
            } else if (itemId == R.id.nav_payments) {
                selectedFragment = new PaymentsActivity();
            }            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
            }

            return true;
        });
    }
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
