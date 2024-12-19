package com.example.rentalapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.example.rentalapp.Controllers.LocaleHelper;

import java.util.Locale;

public class SettingsActivity extends Fragment {
    private Button btn_signout;
    private Spinner spinner_lang;
    private TextView tv_email, tv_username;
    private SharedPreferences SP;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_settings, container, false);

        SP = requireContext().getSharedPreferences("RentalAppPreferences", Context.MODE_PRIVATE);

        btn_signout = view.findViewById(R.id.btn_signout);
        spinner_lang = view.findViewById(R.id.spinner_lang);
        tv_username = view.findViewById(R.id.tv_username);
        tv_email = view.findViewById(R.id.tv_email);

        String adminName = SP.getString("admin_name", "Folan");
        String adminEmail = SP.getString("admin_email", "Folan@exemple.com");
        tv_username.setText(adminName);
        tv_email.setText(adminEmail);


        //setupLanguageSpinner();
        setupSignOutButton();

        return view;
    }

    private void setupSignOutButton() {
        btn_signout.setOnClickListener(v -> {
            SharedPreferences.Editor editor = SP.edit();
            editor.putBoolean("is_logged_in", false);
            editor.remove("admin_name");
            editor.apply();

            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            requireActivity().finish();
        });
    }



    private void setupLanguageSpinner() {
        String currentLang = SP.getString("app_lang", "en");
        spinner_lang.setSelection(getLangPosition(currentLang)); // Set current language as default

        spinner_lang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String langCode = positionToLangCode(position); // Get selected language code
                if (!langCode.equals(currentLang)) { // Apply only if the language is different
                    LocaleHelper.saveLocale(requireContext(), langCode); // Save selected language
                    LocaleHelper.applyLocale(requireContext()); // Apply the language
                    requireActivity().recreate(); // Restart activity to apply changes
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No action needed
            }
        });
    }

    private String positionToLangCode(int position) {
        switch (position) {
            case 1:
                return "fr"; // French
            case 2:
                return "ar"; // Arabic
            default:
                return "en"; // English
        }
    }

    private int getLangPosition(String langCode) {
        switch (langCode) {
            case "fr":
                return 1;
            case "ar":
                return 2;
            default:
                return 0;
        }
    }

}
