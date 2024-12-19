package com.example.rentalapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    EditText edt_email;
    EditText edt_pwd;
    TextView tv_forgot_pwd;
    Switch sw_remember;
    Button btn_login;
    Button btn_google;
    Button btn_facebook;
    TextView tv_no_account;
    TextView tv_signup;
    TextView tv_vide;

    private SharedPreferences SP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        SP = getSharedPreferences("RentalAppPreferences", MODE_PRIVATE);
        boolean isLoggedIn = SP.getBoolean("is_logged_in", false);

        if (isLoggedIn) {
            String admin_name = SP.getString("admin_name", "");
            String admin_email = SP.getString("admin_email", "");

            Intent intent = new Intent(LoginActivity.this, DashboardWorkSpaceActivity.class);
            intent.putExtra("admin_name", admin_name);
            intent.putExtra("admin_email", admin_email);

            startActivity(intent);
            finish();
        } else {
            edt_email = findViewById(R.id.edt_email);
            edt_pwd = findViewById(R.id.edt_pwd);
            tv_forgot_pwd = findViewById(R.id.tv_forgot_pwd);
            sw_remember = findViewById(R.id.sw_remember);
            btn_login = findViewById(R.id.btn_login);
            btn_google = findViewById(R.id.btn_google);
            btn_facebook = findViewById(R.id.btn_facebook);
            tv_no_account = findViewById(R.id.tv_no_account);
            tv_signup = findViewById(R.id.tv_signup);
            tv_vide = findViewById(R.id.tv_vide);
            tv_vide.setVisibility(View.GONE);

            btn_login.setOnClickListener(view -> {
                String email = edt_email.getText().toString().trim();
                String pwd = edt_pwd.getText().toString().trim();

                if (email.isEmpty() && pwd.isEmpty()) {
                    tv_vide.setText("Veuillez remplir email et mot de passe.");
                    tv_vide.setVisibility(View.VISIBLE);
                } else if (email.isEmpty()) {
                    tv_vide.setText("Veuillez remplir email.");
                    tv_vide.setVisibility(View.VISIBLE);
                } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    tv_vide.setText("Veuillez entrer une adresse email valide.");
                    tv_vide.setVisibility(View.VISIBLE);
                } else if (pwd.isEmpty()) {
                    tv_vide.setText("Veuillez remplir mot de passe.");
                    tv_vide.setVisibility(View.VISIBLE);
                } else {
                    CrmDB crmDB = new CrmDB(LoginActivity.this);
                    Cursor cursor = crmDB.getElements(CrmDB.TABLE_ADMIN,
                            null,
                            CrmDB.COL_ADMIN_EMAIL + "=? AND " + CrmDB.COL_ADMIN_PASSWORD + "=?",
                            new String[]{email, pwd},
                            null);

                    if (cursor != null && cursor.moveToFirst()) {
                        if (sw_remember.isChecked()) {
                            SharedPreferences.Editor editor = SP.edit();
                            editor.putString("admin_name", cursor.getString(cursor.getColumnIndexOrThrow(CrmDB.COL_ADMIN_NAME)));
                            editor.putString("admin_email", email);
                            editor.putBoolean("is_logged_in", true);
                            editor.apply();
                        }
                        Intent it = new Intent(LoginActivity.this, DashboardWorkSpaceActivity.class);
                        it.putExtra("admin_name", cursor.getString(cursor.getColumnIndexOrThrow(CrmDB.COL_ADMIN_NAME)));
                        startActivity(it);
                        finish();
                    } else {
                        tv_vide.setText("Email ou mot de passe incorrect.");
                        tv_vide.setVisibility(View.VISIBLE);
                    }
                    if (cursor != null) {
                        cursor.close();
                    }
                }
            });

            btn_google.setOnClickListener(view -> {
                //
            });

            btn_facebook.setOnClickListener(view -> {
                //
            });

            tv_signup.setOnClickListener(view -> {
                Intent signupIntent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(signupIntent);
            });
        }
    }
}
