package com.example.rentalapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
public class PropertiesFragment extends Fragment {
    private RecyclerView recyclerView;
    private PropertyAdapter propertyAdapter;
    private List<PropertyItem> propertyList;
    private EditText edtSearch;
    private ImageButton btn_search, btn_addp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_properties, container, false);
        recyclerView = view.findViewById(R.id.list_cards_properties);
        edtSearch = view.findViewById(R.id.edt_search);
        btn_search = view.findViewById(R.id.btn_search);
        btn_addp = view.findViewById(R.id.btn_addp);

        propertyAdapter = new PropertyAdapter(getActivity(), propertyList);

        propertyList = new ArrayList<>();
        propertyAdapter = new PropertyAdapter(propertyList, this::onEditProperty, this::onArchiveProperty, this::onMoreOptions);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(propertyAdapter);
        loadProperties();

        btn_search.setOnClickListener(v -> performSearch());
        btn_addp.setOnClickListener(v -> addProperty());


        return view;
    }

    private void loadProperties() {
        for (int i = 1; i <= 10; i++) {
            propertyList.add(new PropertyItem("Property " + i, "Description " + i));
        }
        propertyAdapter.notifyDataSetChanged();
    }

    private void performSearch() {
        String query = edtSearch.getText().toString().trim();
        if (query.isEmpty()) {
            Toast.makeText(getActivity(), "Please enter a search term.", Toast.LENGTH_SHORT).show();
            return;
        }
        List<PropertyItem> filteredList = new ArrayList<>();
        for (PropertyItem property : propertyList) {
            if (property.getName().toLowerCase().contains(query.toLowerCase()) ||
                    property.getDescription().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(property);
            }
        }
        propertyAdapter.updateList(filteredList);
    }

    private void addProperty() {
        Intent intent = new Intent(getActivity(), AddPropertyActivity.class);
        startActivity(intent);
    }

    // Action for Edit button
    private void onEditProperty(int position) {
        PropertyItem property = propertyList.get(position);
        Toast.makeText(getActivity(), "Editing: " + property.getName(), Toast.LENGTH_SHORT).show();

        // Logic to open an edit property screen can go here
    }

    // Action for Archive button
    private void onArchiveProperty(int position) {
        PropertyItem property = propertyList.get(position);
        propertyList.remove(position);
        propertyAdapter.notifyItemRemoved(position);
        Toast.makeText(getActivity(), "Archived: " + property.getName(), Toast.LENGTH_SHORT).show();
    }

    // Action for More Options button
    private void onMoreOptions(int position) {
        PropertyItem property = propertyList.get(position);
        Toast.makeText(getActivity(), "More options for: " + property.getName(), Toast.LENGTH_SHORT).show();
        // Logic to show a menu or dialog for more options can go here
    }
}
