package com.example.vladimir.uzbekistanexplorer.FragmentPhrasebook;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.support.v4.app.FragmentActivity;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabasePhrases extends SQLiteAssetHelper{
    private static final  String DATABASE_NAME = "phrases.db";
    private static final int DATABASE_VERSION = 1;

    String sqlTables = "";
    String[] sqlSelect;

    public DatabasePhrases(FragmentActivity context, String sqlTables, String[] sqlSelect) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        sqlTables = sqlTables;
        this.sqlSelect = sqlSelect;
    }

    public Cursor getNames(){
        SQLiteDatabase db =  getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();


        qb.setTables(sqlTables);
        Cursor cursor = qb.query(db, sqlSelect, null, null, null, null, null);

        cursor.moveToFirst();
        return cursor;
    }
}
