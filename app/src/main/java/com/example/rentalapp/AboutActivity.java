package com.example.rentalapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {

    private TextView tvAppName, tvAppVersion, tvDeveloper;
    private Button btnContactSupport, btnWebsite,btnPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        tvAppName = findViewById(R.id.tv_app_name);
        tvAppVersion = findViewById(R.id.tv_app_version);
        tvDeveloper = findViewById(R.id.tv_developer);
        btnContactSupport = findViewById(R.id.btn_contact_support);
        btnWebsite = findViewById(R.id.btn_website);
        btnPhone = findViewById(R.id.btn_phone);


        btnContactSupport.setOnClickListener(v -> {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto:"));
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"support@quickrentcrm.com"});
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "QuickRentCRM Support");
            if (emailIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(emailIntent);
            }
        });
        btnWebsite.setOnClickListener(v -> {
            String url = "https://www.quickrentcrm.com";
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        });
        btnPhone.setOnClickListener(v -> {
            Intent phoneIntent = new Intent(Intent.ACTION_DIAL);
            phoneIntent.setData(Uri.parse("tel:+212606290747" ));
            if (phoneIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(phoneIntent);
            } else {
                Toast.makeText(this, "Aucune application de téléphone trouvée", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
