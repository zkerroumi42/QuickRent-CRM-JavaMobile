package com.example.rentalapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.rentalapp.Controllers.PrintPdfAdapter;
public class DashboardActivity extends Fragment {

    private ImageButton btnReport, btnProperties, btnPayments, btnTenants;
    private TextView tvProperties, tvPayments, tvTenants;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_dashboard, container, false);

        btnReport = view.findViewById(R.id.btn_report);
        btnProperties = view.findViewById(R.id.btn_properties);
        btnPayments = view.findViewById(R.id.btn_payments);
        btnTenants = view.findViewById(R.id.btn_tenants);

        tvProperties = view.findViewById(R.id.tv_properties);
        tvPayments = view.findViewById(R.id.tv_payments);
        tvTenants = view.findViewById(R.id.tv_tenants);

        btnReport.setOnClickListener(v -> generateAndPrintPdf());
        btnProperties.setOnClickListener(v -> Toproperties());
        btnTenants.setOnClickListener(v -> ToTenants());
        btnPayments.setOnClickListener(v -> ToPayments());


        CrmDB crmDB = new CrmDB(requireActivity());
        int totalPayments = crmDB.getCount(CrmDB.TABLE_PAYMENTS);
        int totalProperties = crmDB.getCount(CrmDB.TABLE_PROPERTIES);
        int totalTenants = crmDB.getCount(CrmDB.TABLE_TENANTS);

        tvProperties.setText(String.valueOf(totalProperties));
        tvPayments.setText(String.valueOf(totalPayments));
        tvTenants.setText(String.valueOf(totalTenants));

        return view;
    }
    private void Toproperties() {
        Intent intent = new Intent(getActivity(), PropertiesActivity.class);
        startActivity(intent);
    }
    private void ToTenants() {
        Intent intent = new Intent(getActivity(), TenantsActivity.class);
        startActivity(intent);
    }
    private void ToPayments() {
        Intent intent = new Intent(getActivity(), PaymentsActivity.class);
        startActivity(intent);
    }

    private void generateAndPrintPdf() {
        String reportContent = "Dashboard Report\n\n"
                + "Summary of Activities:\n"
                + "- Total Rentals: 120\n"
                + "- Pending Approvals: 15\n"
                + "- Archived Properties: 10";

        PdfDocument pdfDocument = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);
        page.getCanvas().drawText(reportContent, 10, 25, new android.graphics.Paint());
        pdfDocument.finishPage(page);

        PrintManager printManager = (PrintManager) requireContext().getSystemService(Context.PRINT_SERVICE);
        PrintPdfAdapter printAdapter = new PrintPdfAdapter(requireContext(), pdfDocument, "Dashboard_Report.pdf");
        printManager.print("DashboardReport", printAdapter, new PrintAttributes.Builder().build());
    }
}
