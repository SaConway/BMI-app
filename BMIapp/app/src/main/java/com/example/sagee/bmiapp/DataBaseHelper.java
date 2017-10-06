package com.example.sagee.bmiapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DataBaseHelper extends SQLiteOpenHelper
{
    private static DataBaseHelper mInstance = null;

    private static final String DATABASE_NAME = "History.db";
    private static final String TABLE_NAME = "History_table";
    private static final String COL_1 = "DATE";
    private static final String COL_2 = "WEIGHT";
    private static final String COL_3 = "HEIGHT";
    private static final String COL_4 = "BMI";

    public static DataBaseHelper getInstance(Context context)
    {
        if (mInstance == null)
            mInstance = new DataBaseHelper(context.getApplicationContext());

        return mInstance;
    }

    private DataBaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL("create table " + TABLE_NAME +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " DATE TEXT," +
                " WEIGHT REAL," +
                " HEIGHT REAL," +
                " BMI REAL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void insertData(String date, double weight, double height, double bmi)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, date);
        contentValues.put(COL_2, weight);
        contentValues.put(COL_3, height);
        contentValues.put(COL_4, bmi);
        db.insert(TABLE_NAME, null, contentValues);
    }

    public Cursor getAllData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }

    public void deleteData(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "ID = ?", new String[] { id });
    }
}