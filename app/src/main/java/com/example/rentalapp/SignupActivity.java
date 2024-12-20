package com.example.rentalapp;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class SignupActivity extends AppCompatActivity {
    EditText edt_nom;
    EditText edt_prenom;
    EditText edt_email;
    EditText edt_pwd;
    Button btn_signup;
    Button btn_google;
    TextView tv_vide;
    TextView tv_signin;

    private GoogleSignInClient googleSignInClient;
    private static final int GOOGLE_SIGN_IN_REQUEST_CODE = 100;

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
        tv_vide = findViewById(R.id.tv_vide);
        tv_signin = findViewById(R.id.tv_signin);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("YOUR_CLIENT_ID_HERE")
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
        btn_signup.setOnClickListener(view -> handleSignup());
        tv_signin.setOnClickListener(view -> startActivity(new Intent(SignupActivity.this, LoginActivity.class)));

        btn_google.setOnClickListener(view -> {
            Intent signInIntent = googleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, GOOGLE_SIGN_IN_REQUEST_CODE);
        });
    }

    private void handleSignup() {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GOOGLE_SIGN_IN_REQUEST_CODE) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {
                    String idToken = account.getIdToken();
                    String email = account.getEmail();
                    String displayName = account.getDisplayName();

                    Log.d("GoogleLogin", "ID Token: " + idToken);
                    Log.d("GoogleLogin", "Email: " + email);
                    Log.d("GoogleLogin", "Name: " + displayName);


                }
            } catch (ApiException e) {
                Log.e("GoogleLogin", "Sign-in failed: " + e.getMessage());
            }
        }
    }
}
