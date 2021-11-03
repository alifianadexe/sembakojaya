package com.adexe.sembakojaya;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SMSCenterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call_center);
        getSupportActionBar().hide();
    }
}
