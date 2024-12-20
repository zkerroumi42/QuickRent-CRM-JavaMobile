package com.example.rentalapp;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class AddTenantActivity extends AppCompatActivity {

    private static final int UPLOAD_REQUEST_CODE = 2001;
    private static final int CAMERA_REQUEST_CODE = 2002;

    private EditText edt_name;
    private EditText edt_email;
    private EditText edt_phone;
    private ImageView imageView;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tenant);

        // Initialize views
        edt_name = findViewById(R.id.edt_name);
        edt_email = findViewById(R.id.edt_email);
        edt_phone = findViewById(R.id.edt_phone);
        imageView = findViewById(R.id.imageView);
        ImageButton btn_back = findViewById(R.id.btn_back);
        Button btn_upload = findViewById(R.id.btn_upload);
        Button btn_camera = findViewById(R.id.btn_camera);
        Button btn_save = findViewById(R.id.btn_save);

        // Back button functionality
        btn_back.setOnClickListener(v -> finish());

        // Upload photo from gallery
        btn_upload.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(Intent.createChooser(intent, "Select Profile Picture"), UPLOAD_REQUEST_CODE);
        });

        // Capture photo using the camera
        btn_camera.setOnClickListener(v -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAMERA_REQUEST_CODE);
        });

        // Save tenant information
        btn_save.setOnClickListener(view -> {
            String name = edt_name.getText().toString().trim();
            String email = edt_email.getText().toString().trim();
            String phone = edt_phone.getText().toString().trim();

            if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Veuillez remplir tous les champs obligatoires.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Save to the database
            ContentValues values = new ContentValues();
            values.put(CrmDB.COL_TENANT_NAME, name);
            values.put(CrmDB.COL_TENANT_EMAIL, email);
            values.put(CrmDB.COL_TENANT_PHONE, phone);

            CrmDB crmDB = new CrmDB(AddTenantActivity.this);
            long result = crmDB.ajouterElement(CrmDB.TABLE_TENANTS, values);

            if (result != -1) {
                Toast.makeText(getApplicationContext(), "Locataire ajouté avec succès !", Toast.LENGTH_SHORT).show();
                Intent it = new Intent(AddTenantActivity.this, TenantsActivity.class);
                startActivity(it);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Erreur lors de l'ajout du locataire.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == UPLOAD_REQUEST_CODE) {
                // Handle image from gallery
                if (data != null && data.getData() != null) {
                    imageUri = data.getData();
                    Log.d("Upload", "Selected Image URI: " + imageUri.toString());
                    imageView.setImageURI(imageUri);
                }
            } else if (requestCode == CAMERA_REQUEST_CODE) {
                // Handle image from camera
                if (data != null && data.getExtras() != null) {
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    imageView.setImageBitmap(photo);

                    // Save the photo as a URI for database storage
                    Uri tempUri = getImageUri(photo);
                    if (tempUri != null) {
                        imageUri = tempUri;
                        Log.d("Camera", "Captured Image URI: " + imageUri.toString());
                    }
                }
            }
        }
    }

    // Convert Bitmap to URI
    private Uri getImageUri(Bitmap bitmap) {
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "ProfilePhoto", null);
        return Uri.parse(path);
    }
}
