package com.example.rentalapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TenantDetailsActivity extends AppCompatActivity {

    private TextView tvTenantName, tvTenantEmail, tvTenantPhone, tvTenantStatus;
    private ImageView ivTenantImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_details);


        tvTenantName = findViewById(R.id.tv_tenant_name);
        tvTenantEmail = findViewById(R.id.tv_tenant_email);
        tvTenantPhone = findViewById(R.id.tv_tenant_phone);
        tvTenantStatus = findViewById(R.id.tv_tenant_status);
        ivTenantImage = findViewById(R.id.image);

        Intent intent = getIntent();
        String tenantName = intent.getStringExtra("TENANT_NAME");
        String tenantEmail = intent.getStringExtra("TENANT_EMAIL");
        String tenantPhone = intent.getStringExtra("TENANT_PHONE");
        String tenantStatus = intent.getStringExtra("TENANT_STATUS");
        String tenantImageUri = intent.getStringExtra("TENANT_IMAGE");

        tvTenantName.setText(tenantName);
        tvTenantEmail.setText(tenantEmail);
        tvTenantPhone.setText(tenantPhone);
        tvTenantStatus.setText(tenantStatus);

    }
}
