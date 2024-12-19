package com.example.rentalapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CrmDB {
    private static final String DB_NAME = "crmDatabase.db";
    private static final int DB_VERSION = 1;
    public static final String TABLE_ADMIN = "_admin";
    public static final String COL_ADMIN_ID = "_id";
    public static final String COL_ADMIN_NAME = "_name";
    public static final String COL_ADMIN_EMAIL = "_email";
    public static final String COL_ADMIN_PASSWORD = "_password";

    public static final String TABLE_TENANTS = "tenants";
    public static final String COL_TENANT_ID = "_id";
    public static final String COL_TENANT_NAME = "_name";
    public static final String COL_TENANT_PHONE = "_phone";
    public static final String COL_TENANT_EMAIL = "_email";

    public static final String TABLE_PROPERTIES = "_properties";
    public static final String COL_PROPERTY_ID = "_id";
    public static final String COL_PROPERTY_NAME = "_name";
    public static final String COL_PROPERTY_DESCRIPTION = "_description";
    public static final String COL_PROPERTY_ADDRESS = "_address";
    public static final String COL_PROPERTY_RENT = "_rent";
    public static final String COL_PROPERTY_TYPE = "_type";
    public static final String COL_PROPERTY_RENTAL_TYPE = "_rental_type";
    public static final String COL_PROPERTY_IMAGE = "_image";

    public static final String TABLE_PAYMENTS = "payments";
    public static final String COL_PAYMENT_ID = "_id";
    public static final String COL_PAYMENT_TENANT_ID = "_tenant_id";
    public static final String COL_PAYMENT_PROPERTY_ID = "_property_id";
    public static final String COL_PAYMENT_DATE = "_date";
    public static final String COL_PAYMENT_AMOUNT = "_amount";

    private Context dbContext;

    public CrmDB(Context context) {
        dbContext = context;
    }

    private static class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + TABLE_ADMIN + " (" +
                    COL_ADMIN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_ADMIN_NAME + " TEXT NOT NULL, " +
                    COL_ADMIN_EMAIL + " TEXT NOT NULL, " +
                    COL_ADMIN_PASSWORD + " TEXT NOT NULL);");

            db.execSQL("CREATE TABLE " + TABLE_TENANTS + " (" +
                    COL_TENANT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_TENANT_NAME + " TEXT NOT NULL, " +
                    COL_TENANT_PHONE + " TEXT NOT NULL, " +
                    COL_TENANT_EMAIL + " TEXT NOT NULL);");

            db.execSQL("CREATE TABLE " + TABLE_PROPERTIES + " (" +
                    COL_PROPERTY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_PROPERTY_NAME + " TEXT NOT NULL, " +
                    COL_PROPERTY_DESCRIPTION + " TEXT NOT NULL, " +
                    COL_PROPERTY_ADDRESS + " TEXT NOT NULL, " +
                    COL_PROPERTY_RENT + " REAL NOT NULL, " +
                    COL_PROPERTY_TYPE + " TEXT NOT NULL, " + // For Property Type
                    COL_PROPERTY_RENTAL_TYPE + " TEXT NOT NULL, " + // For Rental Type (e.g., 'Mensuelle' or 'Par Nuitée')
                    COL_PROPERTY_IMAGE + " TEXT);"); // Storing image as a path (or BLOB if you prefer)

            db.execSQL("CREATE TABLE " + TABLE_PAYMENTS + " (" +
                    COL_PAYMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_PAYMENT_TENANT_ID + " INTEGER NOT NULL, " +
                    COL_PAYMENT_PROPERTY_ID + " INTEGER NOT NULL, " +
                    COL_PAYMENT_DATE + " TEXT NOT NULL, " +
                    COL_PAYMENT_AMOUNT + " REAL NOT NULL, " +
                    "FOREIGN KEY (" + COL_PAYMENT_TENANT_ID + ") REFERENCES " + TABLE_TENANTS + "(" + COL_TENANT_ID + "), " +
                    "FOREIGN KEY (" + COL_PAYMENT_PROPERTY_ID + ") REFERENCES " + TABLE_PROPERTIES + "(" + COL_PROPERTY_ID + "));");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADMIN);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TENANTS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROPERTIES);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PAYMENTS);
            onCreate(db);
        }
    }

    private DBHelper dbHelper;

    public SQLiteDatabase OuvrirBD() {
        if (dbHelper == null) {
            dbHelper = new DBHelper(dbContext);
        }
        return dbHelper.getWritableDatabase();
    }

    public void FermerBD() {
        if (dbHelper != null) {
            dbHelper.close();
            dbHelper = null;
        }
    }

    public long ajouterElement(String table, ContentValues values) {
        SQLiteDatabase db = OuvrirBD();
        long result = db.insert(table, null, values);
        FermerBD();
        return result;
    }

    public int modifierElement(String table, ContentValues values, String whereClause, String[] whereArgs) {
        SQLiteDatabase db = OuvrirBD();
        int rowsAffected = db.update(table, values, whereClause, whereArgs);
        FermerBD();
        return rowsAffected;
    }

    public int supprimerElement(String table, String whereClause, String[] whereArgs) {
        SQLiteDatabase db = OuvrirBD();
        return  db.delete(table, whereClause, whereArgs);
    }
    public int mettreAJourElement(String table, ContentValues contentValues, String whereClause, String[] whereArgs) {
        SQLiteDatabase db = OuvrirBD();
        int rowsUpdated = db.update(table, contentValues, whereClause, whereArgs);
        FermerBD();
        return rowsUpdated;
    }


    public Cursor getElements(String table, String[] columns, String selection, String[] selectionArgs, String orderBy) {
        SQLiteDatabase db = OuvrirBD();
        return db.query(table, columns, selection, selectionArgs, null, null, orderBy);
    }
}
