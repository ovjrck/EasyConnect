package com.example.easyconnect;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DTABASE_NAME = "EasyConnect.db";
    private static final int VERSION = 1;

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DTABASE_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query1 = "CREATE TABLE Location(" +
                        "location_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "location_name TEXT);";

        String query2 = "CREATE TABLE Contact(" +
                        "contact_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "contact_name TEXT);";

        db.execSQL(query1);
        db.execSQL(query2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Location");
        db.execSQL("DROP TABLE IF EXISTS Contact");
        onCreate(db);
    }

    void addLocation(String locationName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("location_name", locationName);
        long result = db.insert("Location", null, cv);
        if(result == -1){
            Toast.makeText(context, "FAILED", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "ADDED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
        }
    }

    void addContact(String contact_name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("contact_name", contact_name);
        long result = db.insert("Contact", null, cv);
        if(result == -1){
            Toast.makeText(context, "FAILED", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "ADDED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
        }
    }

    void updateContact(String oldContactName, String newContactName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("contact_name", newContactName);

        int result = db.update("Contact", cv, "contact_name = ?", new String[]{oldContactName});
        if(result == 0){
            Toast.makeText(context, "FAILED", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "UPDATED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
        }
    }

    public void loadAllLocationsToSharedModel() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT location_name FROM Location", null);

            if (cursor.moveToFirst()) {
                do {
                    String name = cursor.getString(0);
                    SharedDataModel.getInstance().addLocation(name);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();  // Log the error in Logcat
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    public void loadAllContactsToSharedModel() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT contact_name FROM Contact", null);

            if (cursor.moveToFirst()) {
                do {
                    String contact = cursor.getString(0);  // Only one column
                    SharedDataModel.getInstance().addContact(contact);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();  // You can log to Logcat for more detail
        } finally {
            if (cursor != null) cursor.close();
        }
    }

}
