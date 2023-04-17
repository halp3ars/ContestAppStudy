package com.study.contest.db;

import static android.widget.Toast.makeText;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.time.LocalDate;


public class UserRecordsHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "user_records.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "user_records";
    public static final String ID = "_id";
    public static final String RECORD_TITLE = "record_title";
    public static final String CONTENT = "content";
    public static final String DATE = "date";

    private final Context context;

    public UserRecordsHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                RECORD_TITLE + " TEXT, " +
                CONTENT + " TEXT, " +
                DATE + " TEXT);";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addRecord(String title, String content, LocalDate date) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(RECORD_TITLE, title);
        contentValues.put(CONTENT, content);
        contentValues.put(DATE, String.valueOf(date));
        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        Looper.prepare();
        if (isError(result)) {
            makeText(context, "Произошла ошибка при добавлении записи. Повторите еще раз", Toast.LENGTH_SHORT).show();
        } else {
            makeText(context, "Запись добавлена!", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor readAllData() {
        String querySelectAllFromBook = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = null;
        if (sqLiteDatabase != null) {
            cursor = sqLiteDatabase.rawQuery(querySelectAllFromBook, null);
        }

        return cursor;
    }

    public void updateData(String row_id, String title, String content, LocalDate date) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(RECORD_TITLE, title);
        contentValues.put(CONTENT, content);
        contentValues.put(DATE, String.valueOf(date));

        long result = sqLiteDatabase.update(TABLE_NAME, contentValues, "_id=?", new String[]{row_id});
        if (isError(result)) {
            makeText(context, "Произошла ошибка при обновлении. Повторите еще раз", Toast.LENGTH_SHORT).show();
        } else {
            makeText(context, "Запись обновлена!", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteOneRow(String row_id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        long result = sqLiteDatabase.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if (isError(result)) {
            makeText(context, "Ошибка при удалении записи. Повторите еще раз", Toast.LENGTH_SHORT).show();
        } else {
            makeText(context, "Запись удалена.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isError(long result) {
        return result == -1;
    }

}
