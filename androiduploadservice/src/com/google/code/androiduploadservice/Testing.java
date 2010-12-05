package com.google.code.androiduploadservice;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.google.code.androiduploadservice.R.id;
import com.google.code.androiduploadservice.service.UploadService;

public class Testing extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
    private static final String TAG = Testing.class.getName();
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button btn = (Button) findViewById(id.btnStart);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(id.btnStop);
        btn.setOnClickListener(this);
        startService(new Intent(this, UploadService.class));
        doBindService(); // service ist im aktuelle Thread nicht aktiv. - 
        // praktisch also erst später vorhanden . . . 

    }
    
    public Testing getThis() {
        return this;
    }
    
    public void onStartPressed() {
        Log.i(TAG,"trigger onStart");
        startService(new Intent(this, UploadService.class));
        if (mIsBound) {
            if (mBoundService != null) {
                Log.i(TAG,mBoundService.getVersion());
            } else {
                Log.e(TAG,"mBoundService is null");
            }
        } else {
            Log.e(TAG,"mBoundService is not connected");   
        }
        
    }
    
    public void onStopPressed() {
        Log.i(TAG,"trigger onStop");
        stopService(new Intent(this, UploadService.class));
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
        case id.btnStart:
            onStartPressed();
            break;
        case id.btnStop:
            onStopPressed();
            break;
        default:
            throw new RuntimeException("Unknown Case for onClick-Switch");
        }
    }
    
    
    
    
    
    
    // **
    // ** Communication with Service
    // ** 
    private UploadService mBoundService;
    private boolean mIsBound = false;
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            // This is called when the connection with the service has been
            // established, giving us the service object we can use to
            // interact with the service.  Because we have bound to a explicit
            // service that we know is running in our own process, we can
            // cast its IBinder to a concrete class and directly access it.
            mBoundService = ((UploadService.LocalBinder)service).getService();

            // Tell the user about this for our demo.
            Log.i(TAG,"Service connected");
        }

        public void onServiceDisconnected(ComponentName className) {
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            // Because it is running in our same process, we should never
            // see this happen.
            mBoundService = null;
            Log.i(TAG,"Service DISconnected");
        }
    };

    void doBindService() {
        // Establish a connection with the service.  We use an explicit
        // class name because we want a specific service implementation that
        // we know will be running in our own process (and thus won't be
        // supporting component replacement by other applications).
        final Intent serviceIntent = new Intent(Testing.this, UploadService.class);
        bindService(serviceIntent, mConnection, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    void doUnbindService() {
        if (mIsBound) {
            // Detach our existing connection.
            unbindService(mConnection);
            mIsBound = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        doUnbindService();
    }
    
    // **
    // ** Communication with Service
    // **
    
}

        