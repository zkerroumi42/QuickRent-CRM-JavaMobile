package com.example.rentalapp;

import android.content.Intent;
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
        btn_login.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String email = edt_email.getText().toString().trim();
                String pwd = edt_pwd.getText().toString().trim();

                if (email.isEmpty() && pwd.isEmpty()) {
                    tv_vide.setText("Veuillez remplir email et mot de passe.");
                    tv_vide.setVisibility(View.VISIBLE);
                } else if (email.isEmpty()) {
                    tv_vide.setText("Veuillez remplir email.");
                    tv_vide.setVisibility(View.VISIBLE);
                } else if (pwd.isEmpty()) {
                    tv_vide.setText("Veuillez remplir mot de passe.");
                    tv_vide.setVisibility(View.VISIBLE);
                }
                else {
                    Intent it = new Intent(LoginActivity.this,
                            com.example.rentalapp.DashboardActivity.class) ;
                    it.putExtra("userlogin",email);
                    startActivity(it);
                }
            }

        });

    }
}