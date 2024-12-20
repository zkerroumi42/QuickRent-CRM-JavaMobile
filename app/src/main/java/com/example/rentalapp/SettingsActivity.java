package com.example.rentalapp;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rentalapp.Controllers.LocaleHelper;

public class SettingsActivity extends Fragment {
    private Button btn_signout,btn_share;
    private Spinner spinner_lang;
    private TextView tv_email, tv_username,tv_about;
    private SharedPreferences SP;
    private Switch switch_notifications, switch_mode;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_settings, container, false);

        SP = requireContext().getSharedPreferences("RentalAppPreferences", Context.MODE_PRIVATE);

        btn_signout = view.findViewById(R.id.btn_signout);
        btn_share = view.findViewById(R.id.btn_share);
        spinner_lang = view.findViewById(R.id.spinner_lang);
        tv_username = view.findViewById(R.id.tv_username);
        tv_email = view.findViewById(R.id.tv_email);
        tv_about = view.findViewById(R.id.tv_about);
        switch_notifications = view.findViewById(R.id.switch_notifications);
        switch_mode = view.findViewById(R.id.switch_mode);

        String adminName = SP.getString("admin_name", "Folan");
        String adminEmail = SP.getString("admin_email", "Folan@exemple.com");
        tv_username.setText(adminName);
        tv_email.setText(adminEmail);
        setupSignOutButton();
        setupNotificationSwitch();
        setupDarkModeSwitch();
        setupLanguageSpinner();
        setupShareButton();
        tv_about.setOnClickListener(v -> about());


        return view;
    }

    private void about() {
        Intent intent = new Intent(getActivity(), AboutActivity.class);
        startActivity(intent);
    }
    private void setupShareButton() {
        btn_share.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            String shareMessage = "Check out this amazing rental app: https://play.google.com/store/apps/details?id=" + requireContext().getPackageName();
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);

            startActivity(Intent.createChooser(shareIntent, "Share app via"));
        });
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
    private void setupNotificationSwitch() {
        boolean isNotificationsEnabled = SP.getBoolean("notifications_enabled", true);
        switch_notifications.setChecked(isNotificationsEnabled);

        switch_notifications.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = SP.edit();
            editor.putBoolean("notifications_enabled", isChecked);
            editor.apply();

            if (isChecked) {
                sendNotification("Notifications activées", "Vous recevrez désormais les alertes.");
            } else {
                Toast.makeText(getContext(), "Notifications désactivées", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "my_channel_id",
                    "Notifications",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription("Canal pour les notifications");
            NotificationManager manager = requireActivity().getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }
    @SuppressLint("MissingPermission")
    private void sendNotification(String title, String message) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel();
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(requireContext(), "my_channel_id")
                .setSmallIcon(R.drawable.nitification)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(requireContext());
        notificationManager.notify(1, builder.build());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "Permission de notification accordée", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Permission de notification refusée", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setupDarkModeSwitch() {
        boolean isDarkMode = SP.getBoolean("dark_mode", false);
        AppCompatDelegate.setDefaultNightMode(
                isDarkMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
        );
        switch_mode.setChecked(isDarkMode);
        switch_mode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = SP.edit();
            editor.putBoolean("dark_mode", isChecked);
            editor.apply();

            AppCompatDelegate.setDefaultNightMode(
                    isChecked ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
            );
        });
    }

    private void setupLanguageSpinner() {
        String currentLang = SP.getString("app_lang", "en");
        spinner_lang.setSelection(getLangPosition(currentLang));
        spinner_lang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String langCode = positionToLangCode(position);
                if (!langCode.equals(currentLang)) {
                    LocaleHelper.saveLocale(requireContext(), langCode);
                    LocaleHelper.applyLocale(requireContext());
                    requireActivity().recreate();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    private String positionToLangCode(int position) {
        switch (position) {
            case 1:
                return "fr";
            case 2:
                return "ar";
            default:
                return "en";
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
