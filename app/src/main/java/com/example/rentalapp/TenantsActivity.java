package com.example.rentalapp;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.rentalapp.Controllers.TenantAdapter;
import com.example.rentalapp.Entities.Tenant;
import java.util.ArrayList;
import java.util.List;

public class TenantsActivity extends Fragment {

    private RecyclerView recyclerView;
    private TenantAdapter tenantAdapter;
    private List<Tenant> tenantList;
    private EditText edtSearch;
    private ImageButton btnSearch, btnAddTenant;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_tenants, container, false);

        recyclerView = view.findViewById(R.id.list_tenants);
        edtSearch = view.findViewById(R.id.edt_search);
        btnSearch = view.findViewById(R.id.btn_search);
        btnAddTenant = view.findViewById(R.id.btn_add);

        tenantList = new ArrayList<>();
        tenantAdapter = new TenantAdapter(tenantList, this::onCall, this::onMsgmail, this::onMore, this::onDelete);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(tenantAdapter);
        loadTenants();
        btnSearch.setOnClickListener(v -> performSearch());
        btnAddTenant.setOnClickListener(v -> addTenant());

        return view;
    }

    private void loadTenants() {
        CrmDB crmDB = new CrmDB(requireContext());
        Cursor cursor = null;
        try {
            cursor = crmDB.getElements(
                    CrmDB.TABLE_TENANTS,
                    new String[]{CrmDB.COL_TENANT_ID, CrmDB.COL_TENANT_NAME, CrmDB.COL_TENANT_PHONE, CrmDB.COL_TENANT_EMAIL},
                    null,
                    null,
                    CrmDB.COL_TENANT_NAME + " ASC"
            );

            tenantList.clear();
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(CrmDB.COL_TENANT_ID));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(CrmDB.COL_TENANT_NAME));
                    String phone = cursor.getString(cursor.getColumnIndexOrThrow(CrmDB.COL_TENANT_PHONE));
                    String email = cursor.getString(cursor.getColumnIndexOrThrow(CrmDB.COL_TENANT_EMAIL));
                    tenantList.add(new Tenant(id, name, phone, email));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Error loading tenants!", Toast.LENGTH_SHORT).show();
        } finally {
            if (cursor != null) cursor.close();
        }

        tenantAdapter.notifyDataSetChanged();
    }

    private void performSearch() {
        String query = edtSearch.getText().toString().trim();

        if (query.isEmpty()) {
            Toast.makeText(getActivity(), "Veuillez saisir un terme de recherche.", Toast.LENGTH_SHORT).show();
            return;
        }

        List<Tenant> filteredList = new ArrayList<>();
        for (Tenant tenant : tenantList) {
            if (tenant.getName().toLowerCase().contains(query.toLowerCase()) ||
                    tenant.getEmail().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(tenant);
            }
        }

        if (filteredList.isEmpty()) {
            Toast.makeText(getActivity(), "Aucun locataire trouvé.", Toast.LENGTH_SHORT).show();
        }
        tenantAdapter.updateList(filteredList);
    }

    private void addTenant() {
        Intent intent = new Intent(getActivity(), AddTenantActivity.class);
        startActivity(intent);
    }

    private void onCall(int position) {
        Tenant tenant = tenantList.get(position);
        Toast.makeText(getActivity(), "Calling " + tenant.getName(), Toast.LENGTH_SHORT).show();
    }

    private void onMsgmail(int position) {
        Tenant tenant = tenantList.get(position);
        Toast.makeText(getActivity(), "Messaging " + tenant.getName(), Toast.LENGTH_SHORT).show();
    }

    private void onMore(int position) {
        Tenant tenant = tenantList.get(position);
        Toast.makeText(getActivity(), "More details for " + tenant.getName(), Toast.LENGTH_SHORT).show();
    }

    private void onDelete(int position) {
        Tenant tenant = tenantList.get(position);
        CrmDB crmDB = new CrmDB(requireContext());
        int rowsDeleted = crmDB.supprimerElement(
                CrmDB.TABLE_TENANTS,
                CrmDB.COL_TENANT_ID + " = ?",
                new String[]{String.valueOf(tenant.getId())}
        );

        if (rowsDeleted > 0) {
            tenantList.remove(position);
            tenantAdapter.notifyItemRemoved(position);
            Toast.makeText(getActivity(), "Locataire supprimé avec succès", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Échec de la suppression du locataire", Toast.LENGTH_SHORT).show();
        }
    }
}
