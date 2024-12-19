package com.example.rentalapp;

import android.content.Context;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import com.example.rentalapp.Controllers.PrintPdfAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DashboardActivity extends Fragment {

    private ImageButton btnReport;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_dashboard, container, false);

        // Initialize the button
        btnReport = view.findViewById(R.id.btn_report);
        // Set the click listener for btnReport
        btnReport.setOnClickListener(v -> generateAndPrintPdf());

        return view;
    }
    private void generateAndPrintPdf() {
        // Define your content for the PDF
        String reportContent = "Dashboard Report\n\n"
                + "Summary of Activities:\n"
                + "- Total Rentals: 120\n"
                + "- Pending Approvals: 15\n"
                + "- Archived Properties: 10";

        // Create a simple PDF document
        PdfDocument pdfDocument = new PdfDocument();

        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);

        // Write content into the PDF
        page.getCanvas().drawText(reportContent, 10, 25, new android.graphics.Paint());
        pdfDocument.finishPage(page);

        // Use a PrintManager to send the document to the print service
        PrintManager printManager = (PrintManager) requireContext().getSystemService(Context.PRINT_SERVICE);
        PrintPdfAdapter printAdapter = new PrintPdfAdapter(requireContext(), pdfDocument, "Dashboard_Report.pdf");
        printManager.print("DashboardReport", printAdapter, new PrintAttributes.Builder().build());
    }
}
