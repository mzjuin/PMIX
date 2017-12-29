package com.prestige.kaelmok.pmix.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.prestige.kaelmok.pmix.data.PmixContracts.*;

public class PmixDbHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "stocktakelist.db";
    // If you change the database schema, you must increment the database version
    private static final int DATABASE_VERSION = 3;

    // Constructor
    public PmixDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create a table to hold stocktake data
        final String SQL_CREATE_STOCKTAKELIST_TABLE = "CREATE TABLE " + StockTakeEntry.TABLE_NAME + " (" +
                StockTakeEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                StockTakeEntry.COLUMN_BARCODE + " TEXT NOT NULL UNIQUE, " +
                StockTakeEntry.COLUMN_USER_ID + " INTEGER NOT NULL, " +
                StockTakeEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                "); ";

        // Create a table to hold bale count data
        final String SQL_CREATE_BALECOUNTLIST_TABLE = "CREATE TABLE " + BaleCountEntry.BALE_COUNT_TABLE + " (" +
                BaleCountEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                BaleCountEntry.COLUMN_LOCATION + " TEXT NOT NULL, " +
                BaleCountEntry.COLUMN_BALE_QUANTITY + " INTEGER NOT NULL, " +
                BaleCountEntry.COLUMN_USER_ID + " INTEGER NOT NULL, " +
                BaleCountEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_STOCKTAKELIST_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_BALECOUNTLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // For now simply drop the table and create a new one. This means if you change the
        // DATABASE_VERSION the table will be dropped.
        // In a production app, this method might be modified to ALTER the table
        // instead of dropping it, so that existing data is not deleted.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + StockTakeEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + BaleCountEntry.BALE_COUNT_TABLE);

        // Recreates tables
        onCreate(sqLiteDatabase);
    }
}
