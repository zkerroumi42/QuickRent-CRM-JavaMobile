package com.example.rentalapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddPropertyActivity extends AppCompatActivity {

    EditText edt_name;
    EditText edt_description;
    EditText edt_price;
    Spinner spinner_type;
    ImageButton btn_back;
    Button btn_upload;
    Button btn_save;
    ImageView imageView;
    RadioGroup rgRentalType;
    RadioButton rbm;
    RadioButton rbn;
    private String rentalType = "";
    private static final int UPLOAD_REQUEST_CODE = 1001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_property);
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
        rgRentalType = findViewById(R.id.rg_rental_type);
        rbm = findViewById(R.id.rbm);
        rbn = findViewById(R.id.rbn);

        rgRentalType.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbm) {
                rentalType = "mensuelle";
            } else if (checkedId == R.id.rbn) {
                rentalType = "par nuitée";
            }
        });


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), UPLOAD_REQUEST_CODE);
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Property a ajouter avec succes !", Toast.LENGTH_SHORT).show();
                Intent it = new Intent(AddPropertyActivity.this,
                        com.example.rentalapp.PropertiesFragment.class);
                startActivity(it);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UPLOAD_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null && data.getData() != null) {
                Uri selectedImageUri = data.getData();
                Log.d("Upload", "Selected Image URI: " + selectedImageUri.toString());
                imageView.setImageURI(selectedImageUri);
            }
        }
    }
}