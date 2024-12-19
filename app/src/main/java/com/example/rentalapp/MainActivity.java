package com.example.rentalapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rentalapp.Controllers.LocaleHelper;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Button btn_login;
    Button btn_signup;
    @Override
    protected void attachBaseContext(Context newBase) {
        SharedPreferences SP = newBase.getSharedPreferences("RentalAppPreferences", Context.MODE_PRIVATE);
        String langCode = SP.getString("app_lang", "en");
        super.attachBaseContext(LocaleHelper.updateLocale(newBase, langCode));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LocaleHelper.applyLocale(this);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btn_login = findViewById(R.id.btn_login);
        btn_signup = findViewById(R.id.btn_signup);
        btn_login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                    Intent it = new Intent(MainActivity.this,
                            com.example.rentalapp.LoginActivity.class) ;
                    startActivity(it);
            }

        });
        btn_signup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent it = new Intent(MainActivity.this,
                        com.example.rentalapp.SignupActivity.class) ;
                startActivity(it);
            }

        });
    }
}