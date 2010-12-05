package com.google.code.androiduploadservice.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import com.google.code.androiduploadservice.Testing;

public class TestingTest extends ActivityInstrumentationTestCase2<Testing>{

    Testing mActivity;
    TextView statusText;
    public TestingTest() {
        super("com.google.code.androiduploadservice", Testing.class);
    }
    
    @Override
    public void setUp() {
        mActivity = getActivity();
    }
    
    public void testInit() {
        assertNotNull(mActivity);
        statusText = (TextView)mActivity.findViewById(com.google.code.androiduploadservice.R.id.lblCount);
        assertEquals("Status",statusText.getText().toString());
    }

}
