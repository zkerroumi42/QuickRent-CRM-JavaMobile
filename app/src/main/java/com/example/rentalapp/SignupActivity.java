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

public class SignupActivity extends AppCompatActivity {

    EditText edt_nom;
    EditText edt_prenom;
    EditText edt_email;
    EditText edt_pwd;
    Button btn_signup;
    Button btn_google;
    Button btn_facebook;
    TextView tv_vide;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        edt_nom = findViewById(R.id.edt_nom);
        edt_prenom= findViewById(R.id.edt_prenom);
        edt_email = findViewById(R.id.edt_email);
        edt_pwd = findViewById(R.id.edt_pwd);
        btn_signup = findViewById(R.id.btn_signup);
        btn_google = findViewById(R.id.btn_google);
        btn_facebook = findViewById(R.id.btn_facebook);
        tv_vide = findViewById(R.id.tv_vide);

        btn_signup.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String email = edt_email.getText().toString().trim();
                String pwd = edt_pwd.getText().toString().trim();
                String nom = edt_nom.getText().toString().trim();
                String prenom = edt_prenom.getText().toString().trim();
                String emailRegex = "^[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+$";


                if (email.isEmpty() && pwd.isEmpty()) {
                    tv_vide.setText("Veuillez remplir email et mot de passe.");
                    tv_vide.setVisibility(View.VISIBLE);
                } else if (email.isEmpty()) {
                    tv_vide.setText("Veuillez remplir email.");
                    tv_vide.setVisibility(View.VISIBLE);
                } else if (!email.matches(emailRegex)) {
                    tv_vide.setText("Veuillez entrer une adresse email valide.");
                    tv_vide.setVisibility(View.VISIBLE);
                } else if (nom.isEmpty()) {
                    tv_vide.setText("Veuillez remplir nom.");
                    tv_vide.setVisibility(View.VISIBLE);
                } else if (prenom.isEmpty()) {
                    tv_vide.setText("Veuillez remplir prenom.");
                    tv_vide.setVisibility(View.VISIBLE);
                } else if (pwd.isEmpty()) {
                    tv_vide.setText("Veuillez remplir mot de passe.");
                    tv_vide.setVisibility(View.VISIBLE);
                }
                else {
                    Intent it = new Intent(SignupActivity.this,
                            com.example.rentalapp.DashboardWorkSpaceActivity.class) ;
                    startActivity(it);
                }
            }

        });
        btn_google.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
            //perspectives question of time
            }

        });
        btn_facebook.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
            //perspectives question of time
            }

        });
    }
}