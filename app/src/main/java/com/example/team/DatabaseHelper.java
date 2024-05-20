package com.example.team.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.team.CertificationItem;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "licenses.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_LICENSES = "licenses";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_LDID = "ldId";
    public static final String COLUMN_JMFLDNM = "jmfldnm";
    public static final String COLUMN_RGNAME = "rgName";
    public static final String COLUMN_LICENSETYPE = "licenseType";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_LICENSES + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_LDID + " TEXT, " +
                    COLUMN_JMFLDNM + " TEXT, " +
                    COLUMN_RGNAME + " TEXT, " +
                    COLUMN_LICENSETYPE + " TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LICENSES);
        onCreate(db);
    }

    public List<CertificationItem> getAllLicenses() {
        List<CertificationItem> certificationItems = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_LICENSES,
                new String[]{COLUMN_LDID, COLUMN_JMFLDNM, COLUMN_RGNAME, COLUMN_LICENSETYPE},
                null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String ldId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LDID));
                String jmfldnm = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_JMFLDNM));
                String rgName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RGNAME));
                String licenseType = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LICENSETYPE));
                certificationItems.add(new CertificationItem(jmfldnm, rgName)); // or any fields you want
            }
            cursor.close();
        }

        return certificationItems;
    }
}
