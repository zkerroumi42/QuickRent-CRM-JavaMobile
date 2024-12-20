package com.example.rentalapp.Controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import java.util.Locale;

public class LocaleHelper {

    public static void applyLocale(Context context) {
        SharedPreferences SP = context.getSharedPreferences("RentalAppPreferences", Context.MODE_PRIVATE);
        String langCode = SP.getString("app_lang", "en"); // Default English

        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);

        Configuration config = new Configuration();
        config.setLocale(locale);

        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
    }

    public static void saveLocale(Context context, String langCode) {
        SharedPreferences SP = context.getSharedPreferences("RentalAppPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = SP.edit();
        editor.putString("app_lang", langCode);
        editor.apply();
    }
    public static Context updateLocale(Context context, String langCode) {
        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);

        Configuration config = new Configuration();
        config.setLocale(locale);
        return context.createConfigurationContext(config);
    }
}

