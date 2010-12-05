package com.google.code.androiduploadservice.test;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.Button;
import android.widget.TextView;

import com.google.code.androiduploadservice.Testing;

public class TestingTest extends ActivityInstrumentationTestCase2<Testing>{

    Testing mActivity;
        
    Button btnStart;
    Button btnStop;
    Button btnClear;
    Button btnList;
    
    public TestingTest() {
        super("com.google.code.androiduploadservice", Testing.class);
    }
    
    @Override
    public void setUp() {
        mActivity = getActivity();
        btnStart = (Button) mActivity.findViewById(com.google.code.androiduploadservice.R.id.btnStart);
        btnStop= (Button) mActivity.findViewById(com.google.code.androiduploadservice.R.id.btnStop);
        btnClear = (Button) mActivity.findViewById(com.google.code.androiduploadservice.R.id.btnClear);
        btnList = (Button) mActivity.findViewById(com.google.code.androiduploadservice.R.id.btnList);
    }
    
    
    @UiThreadTest
    public void testInit() {
        assertNotNull(mActivity);
        //statusText = (TextView)mActivity.findViewById(com.google.code.androiduploadservice.R.id.lblCount);
        //assertEquals("Status",statusText.getText().toString());
    }
    
    @UiThreadTest
    public void testList() {
        btnClear.performClick();
        btnList.performClick();
        TextView result = (TextView) mActivity.findViewById(com.google.code.androiduploadservice.R.id.lblCount);
        String txt = result.getText().toString();
        System.out.println(txt);
        assertEquals("",result.getText());
        
        btnStart.performClick();
        btnList.performClick();
        assertEquals("", ((TextView) mActivity.findViewById(com.google.code.androiduploadservice.R.id.lblCount)).getText().toString());        
    }

}
