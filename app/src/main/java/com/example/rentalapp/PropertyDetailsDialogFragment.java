package com.example.rentalapp;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class PropertyDetailsDialogFragment extends DialogFragment {

    private static final String ARG_NAME = "property_name";
    private static final String ARG_DESCRIPTION = "property_description";

    public static PropertyDetailsDialogFragment newInstance(String name, String description) {
        PropertyDetailsDialogFragment fragment = new PropertyDetailsDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, name);
        args.putString(ARG_DESCRIPTION, description);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_details_property, container, false);

        ImageView imgProperty = view.findViewById(R.id.img_property);
        TextView tvPropertyName = view.findViewById(R.id.tv_property_name);
        TextView tvPropertyDescription = view.findViewById(R.id.tv_property_description);
        Button btnClose = view.findViewById(R.id.btn_close);

        if (getArguments() != null) {
            tvPropertyName.setText(getArguments().getString(ARG_NAME));
            tvPropertyDescription.setText(getArguments().getString(ARG_DESCRIPTION));
        }

        btnClose.setOnClickListener(v -> dismiss());

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }
}
