package com.example.team.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.team.CertificationItem;

import java.util.ArrayList;
import java.util.List;

// SQLiteOpenHelper를 상속하여 데이터베이스를 관리하는 클래스
public class DatabaseHelper extends SQLiteOpenHelper {

    // 데이터베이스 이름과 버전 정의
    private static final String DATABASE_NAME = "licenses.db";
    private static final int DATABASE_VERSION = 1;

    // 자격증 테이블 및 칼럼 이름 정의
    public static final String TABLE_LICENSES = "licenses";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_LDID = "ldId";
    public static final String COLUMN_JMFLDNM = "jmfldnm";
    public static final String COLUMN_RGNAME = "rgName";
    public static final String COLUMN_LICENSETYPE = "licenseType";

    // 테이블 생성 SQL 구문 정의
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_LICENSES + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_LDID + " TEXT, " +
                    COLUMN_JMFLDNM + " TEXT, " +
                    COLUMN_RGNAME + " TEXT, " +
                    COLUMN_LICENSETYPE + " TEXT);";

    // 생성자: 데이터베이스 초기화
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // 데이터베이스가 처음 생성될 때 호출되는 메서드
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    // 데이터베이스가 업그레이드될 때 호출되는 메서드
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LICENSES);
        onCreate(db);
    }

    // 모든 자격증 데이터를 반환하는 메서드
    public List<CertificationItem> getAllLicenses() {
        List<CertificationItem> certificationItems = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_LICENSES,
                new String[]{COLUMN_LDID, COLUMN_JMFLDNM, COLUMN_RGNAME, COLUMN_LICENSETYPE},
                null, null, null, null, null);

        // 커서가 null이 아닌 경우 데이터를 읽어옴
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String ldId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LDID));
                String jmfldnm = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_JMFLDNM));
                String rgName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RGNAME));
                String licenseType = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LICENSETYPE));
                // 자격증 아이템을 리스트에 추가
                certificationItems.add(new CertificationItem(jmfldnm, rgName)); // 필요한 필드로 변경 가능
            }
            cursor.close();
        }

        return certificationItems;
    }
}
