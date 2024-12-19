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

public class EditPropertyActivity extends AppCompatActivity {
    EditText edt_name;
    EditText edt_description;
    EditText edt_price;
    Spinner spinner_type;
    ImageButton btn_back;
    Button btn_upload;
    Button btn_update;
    ImageView imageView;
    RadioGroup rgRentalType;
    RadioButton rbm;
    RadioButton rbn;
    private String rentalType = "";
    private static final int UPLOAD_REQUEST_CODE = 1001;
    private Uri imageUri = null;
    private int propertyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_property);

        edt_name = findViewById(R.id.edt_name);
        edt_description = findViewById(R.id.edt_description);
        edt_price = findViewById(R.id.edt_price);
        spinner_type = findViewById(R.id.spinner_type);
        imageView = findViewById(R.id.imageView);
        btn_back = findViewById(R.id.btn_back);
        btn_upload = findViewById(R.id.btn_upload);
        btn_update = findViewById(R.id.btn_save);
        rgRentalType = findViewById(R.id.rg_rental_type);
        rbm = findViewById(R.id.rbm);
        rbn = findViewById(R.id.rbn);

            propertyId = getIntent().getIntExtra("PROPERTY_ID", -1);
            String name = getIntent().getStringExtra("PROPERTY_NAME");
            String description = getIntent().getStringExtra("PROPERTY_DESCRIPTION");
            double rent = getIntent().getDoubleExtra("PROPERTY_RENT", 0.0);
            String type = getIntent().getStringExtra("PROPERTY_TYPE");
            rentalType = getIntent().getStringExtra("PROPERTY_RENTAL_TYPE");
            String imageUriStr = getIntent().getStringExtra("PROPERTY_IMAGE");
            edt_name.setText(name);
            edt_description.setText(description);
            edt_price.setText(String.valueOf(rent));


            setSpinnerSelection(spinner_type, type);

            if ("mensuelle".equals(rentalType)) {
                rbm.setChecked(true);
            } else if ("par nuitée".equals(rentalType)) {
                rbn.setChecked(true);
            }
            if (imageUriStr != null) {
                imageUri = Uri.parse(imageUriStr);
                imageView.setImageURI(imageUri);
            }

        rgRentalType.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbm) {
                rentalType = "mensuelle";
            } else if (checkedId == R.id.rbn) {
                rentalType = "par nuitée";
            }
        });

        btn_back.setOnClickListener(v -> finish());
        btn_upload.setOnClickListener(v -> {
            Intent intentUpload = new Intent(Intent.ACTION_GET_CONTENT);
            intentUpload.setType("image/*");
            intentUpload.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(Intent.createChooser(intentUpload, "Select Image"), UPLOAD_REQUEST_CODE);
        });

        btn_update.setOnClickListener(view -> {
            String namerec = edt_name.getText().toString().trim();
            String descriptionrec = edt_description.getText().toString().trim();
            String rentrec = edt_price.getText().toString().trim();
            String typerec = spinner_type.getSelectedItem().toString();

            if (namerec.isEmpty() || rentrec.isEmpty() || rentalType.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Veuillez remplir tous les champs obligatoires.", Toast.LENGTH_SHORT).show();
                return;
            }

            double rentrecp = Double.parseDouble(rentrec);

            ContentValues values = new ContentValues();
            values.put(CrmDB.COL_PROPERTY_NAME, namerec);
            values.put(CrmDB.COL_PROPERTY_DESCRIPTION, descriptionrec);
            values.put(CrmDB.COL_PROPERTY_RENT, rentrecp);
            values.put(CrmDB.COL_PROPERTY_TYPE, typerec);
            values.put(CrmDB.COL_PROPERTY_RENTAL_TYPE, rentalType);
            if (imageUri != null) {
                values.put(CrmDB.COL_PROPERTY_IMAGE, imageUri.toString());
                Toast.makeText(getApplicationContext(), "url:"+ imageUri.toString(), Toast.LENGTH_SHORT).show();

            }else{
                Toast.makeText(getApplicationContext(), "urlsss:", Toast.LENGTH_SHORT).show();

            }

            CrmDB crmDB = new CrmDB(EditPropertyActivity.this);
            int rowsUpdated = crmDB.modifierElement(CrmDB.TABLE_PROPERTIES, values, CrmDB.COL_PROPERTY_ID + "=?", new String[]{String.valueOf(propertyId)});

            if (rowsUpdated > 0) {
                Toast.makeText(getApplicationContext(), "Propriété mise à jour avec succès !", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Erreur lors de la mise à jour de la propriété.", Toast.LENGTH_SHORT).show();
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
    private void setSpinnerSelection(Spinner spinner, String value) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equals(value)) {
                spinner.setSelection(i);
                break;
            }
        }
    }
}
