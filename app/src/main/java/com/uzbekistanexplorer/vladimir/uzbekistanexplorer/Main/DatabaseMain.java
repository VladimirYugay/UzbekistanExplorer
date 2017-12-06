package com.uzbekistanexplorer.vladimir.uzbekistanexplorer.Main;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.support.v4.app.FragmentActivity;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseMain extends SQLiteAssetHelper {
    private static final  String DATABASE_NAME = "main.db";
    private static final int DATABASE_VERSION = 6;

    String sqlTables = "";
    public DatabaseMain(FragmentActivity context, String tableName) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        setForcedUpgrade();
        sqlTables = tableName;
    }

    public Cursor getNames(){
        SQLiteDatabase db =  getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"id", "city", "description", "nick", "message"};
        qb.setTables(sqlTables);

        Cursor cursor = qb.query(db, sqlSelect, null, null, null, null, null);
        cursor.moveToFirst();
        return cursor;
    }
}