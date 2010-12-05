package com.google.code.androiduploadservice;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {
    Context context;
    private static final String TAG = DbHelper.class.getName();
    static final String DB_NAME = "upload.db";
    static final int    DB_VERSION = 1;


    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }
    
    /** Called only once, first time database is created */
    @Override
    public void onCreate(SQLiteDatabase db) {
      // String sql = String.format(
      // "CREATE table $TABLE ( " +
      // "$C_ID INTEGER NOT NULL PRIMARY KEY," +
      // "%C_CREATED_AT INTEGER, $C_TEXT TEXT, $C_USER TEXT)",
      // ,
      //  , 
      //  , , );
      String sql = context.getResources().getString(R.string.sql_create);
      db.execSQL(sql); // execute the sql
      Log.d(TAG, "onCreate'd sql: " + sql);
    }

    @Override
    
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      Log.d(TAG, String.format("onUpgrade from %s to %s", oldVersion, newVersion));
      // temporary
      db.execSQL("DROP TABLE IF EXISTS timeline;");
      this.onCreate(db);
    }
}
