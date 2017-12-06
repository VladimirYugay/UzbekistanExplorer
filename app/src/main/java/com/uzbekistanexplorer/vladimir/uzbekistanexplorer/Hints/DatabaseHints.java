package com.uzbekistanexplorer.vladimir.uzbekistanexplorer.Hints;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.support.v4.app.FragmentActivity;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseHints extends SQLiteAssetHelper {
    private static final  String DATABASE_NAME = "tips.db";
    private static final int DATABASE_VERSION = 6;

    String sqlTables = "";
    public DatabaseHints(FragmentActivity context, String tableName) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        setForcedUpgrade();
        sqlTables = tableName;
    }

    public Cursor getNames(){
        SQLiteDatabase db =  getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"id", "name", "tip"};
        qb.setTables(sqlTables);

        Cursor cursor = qb.query(db, sqlSelect, null, null, null, null, null);
        cursor.moveToFirst();
        return cursor;
    }
}