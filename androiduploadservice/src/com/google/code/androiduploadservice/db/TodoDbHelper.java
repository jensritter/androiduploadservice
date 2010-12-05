package com.google.code.androiduploadservice.db;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

import com.google.code.androiduploadservice.model.Status;

public class TodoDbHelper  {
    
    DbHelper helper;
    SQLiteDatabase db;
    

    
    static final String TAG = TodoDbHelper.class.getName();
    static final String TABLE = "upload";
    
    static final String C_ID = BaseColumns._ID;
    static final String C_LAST_CHANGED = "last_changed";
    static final String C_FILE = "file";
    
    public static final String[] COLUMNS = new String[] {
        /* 1 */ C_ID,
        /* 2 */ C_FILE,
        /* 3 */ C_LAST_CHANGED,
    };
    
    
    
    public TodoDbHelper(Context ctx) {
        helper = new DbHelper(ctx);
        db = helper.getWritableDatabase();
    }
    
    public void close() {
        db.close();
    }
    


    
    
    /** Converts Twitter.Status to ContentValues */
    public ContentValues statusToContentValues(Status status) {
      ContentValues ret = new ContentValues();
      ret.put(C_ID, status.getId());
      ret.put(C_LAST_CHANGED, status.getChanged().getTime());
      ret.put(C_FILE, status.getFile());
      return ret;
    }

    public List<Status> getAllStatus() {
        db.beginTransaction();
        Cursor cursor = db.query(TABLE, COLUMNS, null, null, null, null,null);
        cursor.moveToFirst();
        List<Status> result = new LinkedList<Status>();
        while (!cursor.isAfterLast()) {
            Status it = new Status();
            it.setId(cursor.getInt(0));
            it.setFile(cursor.getString(1));
            it.setChanged(new Date(cursor.getInt(2)));
            result.add(it);
            cursor.moveToNext();
        }
        db.endTransaction();
        return result;
    }
    
    public int removeStatus(Status stat) {
        return db.delete(TABLE, "id = ?", new String[] { "" + stat.getId() } );
    }

    public void add(Status stat) {
        ContentValues values = statusToContentValues(stat);
        values.remove(TodoDbHelper.C_ID); // no id.
        db.insertOrThrow(TodoDbHelper.TABLE, null, values);
        Log.i(TAG,"Row inserted");
    }
    
    public boolean update(Status stat) {
        ContentValues values = statusToContentValues(stat);
        return db.update(TABLE, values, C_ID + "=?" , new String[] { "" + stat.getId() }) > 0;
    }

    public void removeAll() {
        db.delete(TABLE, null, null);
    }
}
