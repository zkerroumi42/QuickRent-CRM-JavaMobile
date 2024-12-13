package com.example.rentalapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PropertyDetailsActivity extends AppCompatActivity {

    private ImageView imgProperty;
    private TextView tvPropertyName, tvPropertyDescription;
    private Button btnContactOwner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_details);

        // Initialize views
        imgProperty = findViewById(R.id.img_property);
        tvPropertyName = findViewById(R.id.tv_property_name);
        tvPropertyDescription = findViewById(R.id.tv_property_description);
        btnContactOwner = findViewById(R.id.btn_contact_owner);

        // Get data from Intent
        String propertyName = getIntent().getStringExtra("PROPERTY_NAME");
        String propertyDescription = getIntent().getStringExtra("PROPERTY_DESCRIPTION");

        // Set data to views
        tvPropertyName.setText(propertyName);
        tvPropertyDescription.setText(propertyDescription);

        // Add listener for Contact Owner button
        btnContactOwner.setOnClickListener(v ->
                Toast.makeText(this, "Contacting owner of " + propertyName, Toast.LENGTH_SHORT).show()
        );
    }
}
