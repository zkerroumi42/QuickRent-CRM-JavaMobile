package com.example.rentalapp;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EditPropertyActivity extends AppCompatActivity {
    EditText edt_name;
    EditText edt_description;
    EditText edt_price;
    Spinner spinner_type;
    ImageButton btn_back;
    Button btn_upload;
    Button btn_save;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_property);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        edt_name = findViewById(R.id.edt_name);
        edt_description = findViewById(R.id.edt_description);
        edt_price = findViewById(R.id.edt_price);
        spinner_type = findViewById(R.id.spinner_type);
        imageView = findViewById(R.id.imageView);
        btn_back = findViewById(R.id.btn_back);
        btn_upload = findViewById(R.id.btn_upload);

        btn_save = findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Property changé avec succes !", Toast.LENGTH_SHORT).show();
                Intent it = new Intent(EditPropertyActivity.this,
                        com.example.rentalapp.PropertiesActivity.class);
                startActivity(it);
            }
        });
    }
}