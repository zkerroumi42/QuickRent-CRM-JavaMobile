package com.example.rentalapp;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
public class PropertyDetailsActivity extends AppCompatActivity {
    private TextView tv_name, tv_Description, tv_Rent, tv_Type, tv_RentalType;
    private Button btn_delete;
    private ImageView imgPropertyImage;
    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_details);
        tv_name = findViewById(R.id.tv_name);
        tv_Description = findViewById(R.id.tv_description);
        tv_Rent = findViewById(R.id.tv_rent);
        tv_Type = findViewById(R.id.tv_type);
        tv_RentalType = findViewById(R.id.tv_rental_type);
        imgPropertyImage = findViewById(R.id.img_property_image);
        btn_delete = findViewById(R.id.btn_delete);
        int propertyId = getIntent().getIntExtra("PROPERTY_ID",-1);
        String name = getIntent().getStringExtra("PROPERTY_NAME");
        String description = getIntent().getStringExtra("PROPERTY_DESCRIPTION");
        double rent = getIntent().getDoubleExtra("PROPERTY_RENT", 0.0);
        String type = getIntent().getStringExtra("PROPERTY_TYPE");
        String rentalType = getIntent().getStringExtra("PROPERTY_RENTAL_TYPE");
        String imageUri = getIntent().getStringExtra("PROPERTY_IMAGE");
        tv_name.setText(name);
        tv_Description.setText(description);
        tv_Rent.setText(String.format("$%.2f", rent));
        tv_Type.setText(type);
        tv_RentalType.setText(rentalType);
    }
}
