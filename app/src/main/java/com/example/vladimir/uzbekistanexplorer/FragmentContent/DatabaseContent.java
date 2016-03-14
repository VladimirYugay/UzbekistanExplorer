package com.example.vladimir.uzbekistanexplorer.FragmentContent;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseContent extends SQLiteAssetHelper {
    private static final int DATABASE_VERSION = 1;

    String sqlTables = "tashkent_rus";

    public DatabaseContent(Context context, String dbPrefix, String city, String current_lang) {
        super(context, city + ".db", null, DATABASE_VERSION);
        this.sqlTables = dbPrefix + city + "_" + current_lang;
    }

    public Cursor getNames(){
        SQLiteDatabase db =  getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"id", "name", "description", "address", "image", "images"};
        qb.setTables(sqlTables);

        Cursor cursor = qb.query(db, sqlSelect, null, null, null, null, null);
        cursor.moveToFirst();
        return cursor;
    }
}