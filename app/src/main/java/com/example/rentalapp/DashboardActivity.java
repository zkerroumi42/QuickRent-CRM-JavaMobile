package com.example.rentalapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class DashboardActivity extends AppCompatActivity {
    ImageButton btn_profile;
    ImageButton btn_search;
    ImageButton btn_report;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);
        // Adjust padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btn_profile = findViewById(R.id.btn_profile);
        btn_search = findViewById(R.id.btn_search);
        btn_report = findViewById(R.id.btn_report);

        btn_profile.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent it = new Intent(DashboardActivity.this,
                        com.example.rentalapp.SettingsActivity.class);
                startActivity(it);
            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
            }
        });
        btn_report.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                
            }
        });


    }
}
