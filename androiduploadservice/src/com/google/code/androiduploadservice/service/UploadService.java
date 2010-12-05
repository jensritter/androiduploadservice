package com.google.code.androiduploadservice.service;

import java.util.Date;

import android.app.Service;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.code.androiduploadservice.db.TodoDbHelper;
import com.google.code.androiduploadservice.model.Status;

public class UploadService extends Service {

    private static final String TAG = UploadService.class.getName();
    
    private TodoDbHelper dbHelper;
    private SQLiteDatabase db;
    
    /**
     * Class for clients to access.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with
     * IPC.
     */
    public class LocalBinder extends Binder {
        public UploadService getService() {
            return UploadService.this;
        }
    }
    
    // This is the object that receives interactions from clients.  See
    // RemoteService for a more complete example.
    private final IBinder mBinder = new LocalBinder();
    
    
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
    
    @Override
    public void onCreate() {
        dbHelper = new TodoDbHelper(this);
        
        Toast.makeText(this, "My Service Created", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onCreate");
        
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //handler.removeCallbacks(updater); // stop the updater
        db.close();
        Log.d(TAG, "onDestroy'd");
        
        Toast.makeText(this, "My Service Stopped", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onDestroy");
    
    }
    
    @Override
    public void onStart(Intent intent, int startid) {
        Status sta = new Status();
        sta.setChanged(new Date());
        sta.setFile("/tmp/");
        dbHelper.add(sta);
        Toast.makeText(this, "My Service Started", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onStart");
    }
    
    
    public String getVersion() {
        StringBuilder out = new StringBuilder();
        for(Status it : dbHelper.getAllStatus()) {
            out.append(it.getFile()).append("\n");
        }
        return out.toString();
    }
}
