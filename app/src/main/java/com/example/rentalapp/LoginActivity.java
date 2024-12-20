package com.example.rentalapp;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class LoginActivity extends AppCompatActivity {

    EditText edt_email;
    EditText edt_pwd;
    TextView tv_forgot_pwd;
    Switch sw_remember;
    Button btn_login;
    Button btn_google;
    TextView tv_no_account;
    TextView tv_signup;
    TextView tv_vide;

    private SharedPreferences SP;
    private GoogleSignInClient googleSignInClient;
    private static final int GOOGLE_SIGN_IN_REQUEST_CODE = 100;

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
            tv_no_account = findViewById(R.id.tv_no_account);
            tv_signup = findViewById(R.id.tv_signup);
            tv_vide = findViewById(R.id.tv_vide);
            tv_vide.setVisibility(View.GONE);

            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken("5359988241-7r665ol8j54tmhi5c267bd9mlqpj8cbq.apps.googleusercontent.com")
                    .requestEmail()
                    .build();
            googleSignInClient = GoogleSignIn.getClient(this, gso);
            btn_login.setOnClickListener(view -> handleEmailPasswordLogin());
            btn_google.setOnClickListener(view -> {
                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, GOOGLE_SIGN_IN_REQUEST_CODE);
            });
            tv_signup.setOnClickListener(view -> {
                Intent signupIntent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(signupIntent);
            });
            tv_forgot_pwd.setOnClickListener(view -> {
                Intent signupIntent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(signupIntent);
            });
        }
    }

    private void handleEmailPasswordLogin() {
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

                    SharedPreferences.Editor editor = SP.edit();
                    editor.putString("admin_name", displayName);
                    editor.putString("admin_email", email);
                    editor.putBoolean("is_logged_in", true);
                    editor.apply();

                    Intent intent = new Intent(LoginActivity.this, DashboardWorkSpaceActivity.class);
                    intent.putExtra("admin_name", displayName);
                    intent.putExtra("admin_email", email);
                    startActivity(intent);
                    finish();
                }
            } catch (ApiException e) {
                Log.e("GoogleLogin", "Sign-in failed: " + e.getMessage());
            }
        }
    }
}
