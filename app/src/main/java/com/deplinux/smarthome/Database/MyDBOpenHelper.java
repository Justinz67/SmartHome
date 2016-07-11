package com.deplinux.smarthome.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Landscape on 2016/7/5.
 */
public class MyDBOpenHelper extends SQLiteOpenHelper{

    public MyDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "my.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE scene(id INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR(20)," +
                "name VARCHAR(20),trigger_type INTEGER,trigger_time TIME)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("ALTER TABLE scene ADD trigger_date VARCHAR(12) NULL");
    }
}
