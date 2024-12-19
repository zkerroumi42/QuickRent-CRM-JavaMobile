package com.example.rentalapp.Controllers;

import android.content.Context;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
public class PrintPdfAdapter extends PrintDocumentAdapter {
    private final Context context;
    private final PdfDocument pdfDocument;
    private final String fileName;

    public PrintPdfAdapter(Context context, PdfDocument pdfDocument, String fileName) {
        this.context = context;
        this.pdfDocument = pdfDocument;
        this.fileName = fileName;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onLayout(PrintAttributes oldAttributes, PrintAttributes newAttributes, CancellationSignal cancellationSignal, LayoutResultCallback callback, Bundle extras) {

    }

    public void onWrite(PageRange[] pages, ParcelFileDescriptor destination, CancellationSignal cancellationSignal, WriteResultCallback callback) {

    }
    public void onLayout(PrintAttributes oldAttributes, PrintAttributes newAttributes,
                         android.print.PrintDocumentAdapter.LayoutResultCallback callback,
                         android.os.Bundle extras) {
        if (pdfDocument != null) {
            PrintDocumentInfo info = new PrintDocumentInfo.Builder(fileName)
                    .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                    .setPageCount(pdfDocument.getPages().size())
                    .build();
            callback.onLayoutFinished(info, true);
        } else {
            callback.onLayoutFailed("Error creating PDF.");
        }
    }

    public void onWrite(android.print.PageRange[] pages, android.os.ParcelFileDescriptor destination,
                        android.print.PrintDocumentAdapter.WriteResultCallback callback) {
        try (java.io.FileOutputStream outputStream =
                     new java.io.FileOutputStream(destination.getFileDescriptor())) {
            pdfDocument.writeTo(outputStream);
            callback.onWriteFinished(new android.print.PageRange[]{android.print.PageRange.ALL_PAGES});
        } catch (Exception e) {
            callback.onWriteFailed(e.toString());
        } finally {
            pdfDocument.close();
        }
    }
}
