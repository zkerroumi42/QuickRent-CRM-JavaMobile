package com.example.rentalapp;

import android.content.ContentValues;
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
import androidx.appcompat.app.AppCompatActivity;

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
    private Uri imageUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_property);
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
        btn_back.setOnClickListener(v -> finish());
        btn_upload.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(Intent.createChooser(intent, "Select Image"), UPLOAD_REQUEST_CODE);
        });
        btn_save.setOnClickListener(view -> {
            String name = edt_name.getText().toString().trim();
            String description = edt_description.getText().toString().trim();
            String price = edt_price.getText().toString().trim();
            String type = spinner_type.getSelectedItem().toString();

            if (name.isEmpty() || price.isEmpty() || rentalType.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Veuillez remplir tous les champs obligatoires.", Toast.LENGTH_SHORT).show();
                return;
            }
            double rent = Double.parseDouble(price);
            ContentValues values = new ContentValues();
            values.put(CrmDB.COL_PROPERTY_NAME, name);
            values.put(CrmDB.COL_PROPERTY_DESCRIPTION, description);
            values.put(CrmDB.COL_PROPERTY_ADDRESS, type);
            values.put(CrmDB.COL_PROPERTY_RENT, rent);
            values.put(CrmDB.COL_PROPERTY_TYPE, type);
            values.put(CrmDB.COL_PROPERTY_RENTAL_TYPE, rentalType);
            if (imageUri != null) {
                values.put(CrmDB.COL_PROPERTY_IMAGE, imageUri.toString());
            }
            CrmDB crmDB = new CrmDB(AddPropertyActivity.this);
            long result = crmDB.ajouterElement(CrmDB.TABLE_PROPERTIES, values);
            if (result != -1) {
                Toast.makeText(getApplicationContext(), "Propriété ajoutée avec succès !", Toast.LENGTH_SHORT).show();
                Intent it = new Intent(AddPropertyActivity.this, PropertiesActivity.class);
                startActivity(it);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Erreur lors de l'ajout de la propriété.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UPLOAD_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null && data.getData() != null) {
                imageUri = data.getData();
                Log.d("Upload", "Selected Image URI: " + imageUri.toString());
                imageView.setImageURI(imageUri);
            }
        }
    }
}
