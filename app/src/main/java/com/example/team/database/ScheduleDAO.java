package com.example.team.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;

public class ScheduleDAO {
    private DatabaseHelper dbHelper;

    public ScheduleDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // 일정 추가 또는 수정 메서드
    public void addOrUpdateEvent(String date, String event) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_DATE, date);
        values.put(DatabaseHelper.COLUMN_EVENT, event);

        // Check if the date already exists
        Cursor cursor = db.query(DatabaseHelper.TABLE_SCHEDULE, null,
                DatabaseHelper.COLUMN_DATE + "=?",
                new String[]{date}, null, null, null);

        if (cursor.moveToFirst()) {
            // Date exists, update the event
            db.update(DatabaseHelper.TABLE_SCHEDULE, values, DatabaseHelper.COLUMN_DATE + "=?", new String[]{date});
        } else {
            // Date does not exist, insert new event
            db.insert(DatabaseHelper.TABLE_SCHEDULE, null, values);
        }
        cursor.close();
        db.close();
    }

    // 모든 일정 데이터를 반환하는 메서드
    public HashMap<String, String> getAllEvents() {
        HashMap<String, String> scheduleMap = new HashMap<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(DatabaseHelper.TABLE_SCHEDULE, null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String date = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DATE));
                String event = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EVENT));
                scheduleMap.put(date, event);
            }
            cursor.close();
        }
        db.close();
        return scheduleMap;
    }
}
