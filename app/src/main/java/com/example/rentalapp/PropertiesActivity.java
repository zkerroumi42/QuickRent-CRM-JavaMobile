package com.example.rentalapp;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.rentalapp.Controllers.PropertyAdapter;
import com.example.rentalapp.Entities.Property;
import java.util.ArrayList;
import java.util.List;
public class PropertiesActivity extends Fragment {
    private RecyclerView recyclerView;
    private PropertyAdapter propertyAdapter;
    private List<Property> propertyList;
    private EditText edtSearch;
    private ImageButton btn_search, btn_addp;
    private Button btn_archives;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_properties, container, false);

        recyclerView = view.findViewById(R.id.list_cards_properties);
        edtSearch = view.findViewById(R.id.edt_search);
        btn_search = view.findViewById(R.id.btn_search);
        btn_addp = view.findViewById(R.id.btn_addp);
        btn_archives = view.findViewById(R.id.btn_archives);


        propertyList = new ArrayList<>();
        propertyAdapter = new PropertyAdapter(propertyList, this::onEditProperty, this::onArchiveProperty, this::onMoreOptions,this::onDeleteProperty);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(propertyAdapter);
        loadProperties();
        btn_search.setOnClickListener(v -> performSearch());
        btn_addp.setOnClickListener(v -> addProperty());
        btn_archives.setOnClickListener(v->ArchivesProperties());

        return view;
    }

    private void loadProperties() {
        CrmDB crmDB = new CrmDB(requireContext());
        Cursor cursor = null;
        try {
            cursor = crmDB.getElements(
                    CrmDB.TABLE_PROPERTIES,
                    new String[]{CrmDB.COL_PROPERTY_ID, CrmDB.COL_PROPERTY_NAME, CrmDB.COL_PROPERTY_DESCRIPTION, CrmDB.COL_PROPERTY_RENT, CrmDB.COL_PROPERTY_TYPE, CrmDB.COL_PROPERTY_RENTAL_TYPE, CrmDB.COL_PROPERTY_IMAGE},
                    null,
                    null,
                    CrmDB.COL_PROPERTY_NAME + " ASC"
            );
            propertyList.clear();
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(CrmDB.COL_PROPERTY_ID));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(CrmDB.COL_PROPERTY_NAME));
                    String description = cursor.getString(cursor.getColumnIndexOrThrow(CrmDB.COL_PROPERTY_DESCRIPTION));
                    double rent = cursor.getDouble(cursor.getColumnIndexOrThrow(CrmDB.COL_PROPERTY_RENT));
                    String type = cursor.getString(cursor.getColumnIndexOrThrow(CrmDB.COL_PROPERTY_TYPE));
                    String rentalType = cursor.getString(cursor.getColumnIndexOrThrow(CrmDB.COL_PROPERTY_RENTAL_TYPE));
                    String image = cursor.getString(cursor.getColumnIndexOrThrow(CrmDB.COL_PROPERTY_IMAGE));
                    propertyList.add(new Property(id, name, description, rent, type, rentalType, image));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Error loading properties!", Toast.LENGTH_SHORT).show();
        } finally {
            if (cursor != null) cursor.close();
        }

        propertyAdapter.notifyDataSetChanged();
    }

    private void performSearch() {
        String query = edtSearch.getText().toString().trim();

        if (query.isEmpty()) {
            Toast.makeText(getActivity(), "Veuillez saisir un terme de recherche.", Toast.LENGTH_SHORT).show();
            return;
        }
        List<Property> filteredList = new ArrayList<>();
        for (Property property : propertyList) {
            if (property.getName().toLowerCase().contains(query.toLowerCase()) ||
                    property.getDescription().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(property);
            }
        }

        if (filteredList.isEmpty()) {
            Toast.makeText(getActivity(), "Aucune propriété trouvée.", Toast.LENGTH_SHORT).show();
        }
        propertyAdapter.updateList(filteredList);
    }

    private void addProperty() {
        Intent intent = new Intent(getActivity(), AddPropertyActivity.class);
        startActivity(intent);
    }

    private void ArchivesProperties() {
        Intent it = new Intent(getActivity(), ArchivedActivity.class);
        startActivity(it);
    }

    private void onEditProperty(int position) {
        Property property = propertyList.get(position);
        Intent intent = new Intent(getActivity(), EditPropertyActivity.class);
        intent.putExtra("PROPERTY_ID", property.getId());
        intent.putExtra("PROPERTY_NAME", property.getName());
        intent.putExtra("PROPERTY_DESCRIPTION", property.getDescription());
        intent.putExtra("PROPERTY_RENT", property.getRent());
        intent.putExtra("PROPERTY_TYPE", property.getType());
        intent.putExtra("PROPERTY_RENTAL_TYPE", property.getRentalType());
        intent.putExtra("PROPERTY_IMAGE", property.getImage());
        startActivity(intent);
    }
    private void onArchiveProperty(int position) {
        CrmDB crmDB = new CrmDB(requireContext());
        Property property = propertyList.get(position);
        int propertyId = property.getId();

        ContentValues contentValues = new ContentValues();
        contentValues.put("status", "archived");

        String whereClause = "id = ?";
        String[] whereArgs = new String[]{String.valueOf(propertyId)};

        int rowsUpdated = crmDB.mettreAJourElement(CrmDB.TABLE_PROPERTIES, contentValues, whereClause, whereArgs);

        if (rowsUpdated > 0) {
            propertyList.remove(position);
            propertyAdapter.notifyItemRemoved(position);
            Toast.makeText(getActivity(), "Propriété archivée : " + property.getName(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Échec de l'archivage de la propriété", Toast.LENGTH_SHORT).show();
        }
    }
    private void onDeleteProperty(int position) {
        Property propertyToDelete = propertyList.get(position);
        int propertyId = propertyToDelete.getId();
        CrmDB crmDB = new CrmDB(requireContext());
        int rowsDeleted = crmDB.supprimerElement(
                CrmDB.TABLE_PROPERTIES,
                "id = ?",
                new String[]{String.valueOf(propertyId)}
        );
        if (rowsDeleted > 0) {
            Toast.makeText(getActivity(), "Propriété supprimée avec succès", Toast.LENGTH_SHORT).show();

            propertyList.remove(position);
            RecyclerView.Adapter<RecyclerView.ViewHolder> adapter = null;
            adapter.notifyItemRemoved(position);

        } else {
            Toast.makeText(getActivity(), "Échec de la suppression de la propriété", Toast.LENGTH_SHORT).show();
        }
    }
    private void onMoreOptions(int position) {
        Property property = propertyList.get(position);
        Intent it = new Intent(getActivity(), PropertyDetailsActivity.class);
            it.putExtra("PROPERTY_ID", property.getId());
            it.putExtra("PROPERTY_NAME", property.getName());
            it.putExtra("PROPERTY_DESCRIPTION", property.getDescription());
            it.putExtra("PROPERTY_RENT", property.getRent());
            it.putExtra("PROPERTY_TYPE", property.getType());
            it.putExtra("PROPERTY_RENTAL_TYPE", property.getRentalType());
            it.putExtra("PROPERTY_IMAGE", property.getImage());
            startActivity(it);
    }
}
