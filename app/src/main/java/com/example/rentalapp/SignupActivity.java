package com.example.rentalapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    TextView tv_signin;

    @SuppressLint("MissingInflatedId")
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
        edt_prenom = findViewById(R.id.edt_prenom);
        edt_email = findViewById(R.id.edt_email);
        edt_pwd = findViewById(R.id.edt_pwd);
        btn_signup = findViewById(R.id.btn_signup);
        btn_google = findViewById(R.id.btn_google);
        btn_facebook = findViewById(R.id.btn_facebook);
        tv_vide = findViewById(R.id.tv_vide);
        tv_signin = findViewById(R.id.tv_signin);
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
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
                } else {
                    CrmDB crmDB = new CrmDB(SignupActivity.this);
                    ContentValues values = new ContentValues();
                    values.put(CrmDB.COL_ADMIN_NAME, nom + " " + prenom);
                    values.put(CrmDB.COL_ADMIN_EMAIL, email);
                    values.put(CrmDB.COL_ADMIN_PASSWORD, pwd);

                    long result = crmDB.ajouterElement(CrmDB.TABLE_ADMIN, values);
                    if (result != -1) {
                        Intent it = new Intent(SignupActivity.this, LoginActivity.class);
                        startActivity(it);
                        finish();
                    } else {
                        tv_vide.setText("Une erreur s'est produite lors de l'inscription. Veuillez réessayer.");
                        tv_vide.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        tv_signin.setOnClickListener(view -> {
            Intent signupIntent = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(signupIntent);
        });

        btn_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btn_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
