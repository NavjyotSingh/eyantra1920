/*
 *
 * Project Name: 	e-yantra Project:AIDE(AI and IoT Enabled home automation for Disabled and Elderly
 * Author List: 	Srushti Sachdev
 * Filename: 		LogsActivity.java
 * Functions: 		NONE
 * Global Variables:	NONE
 *
 */

package com.example.android.aide;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class LogsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,new LogsFragment()).commit();
    }
}
