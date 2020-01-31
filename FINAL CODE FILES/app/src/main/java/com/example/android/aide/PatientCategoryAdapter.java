/*
 *
 * Project Name: 	e-yantra Project:AIDE(AI and IoT Enabled home automation for Disabled and Elderly
 * Author List: 	Srushti Sachdev
 * Filename: 		PatientCategoryAdapter.java
 * Functions: 		NONE
 * Global Variables:	NONE
 *
 */

package com.example.android.aide;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PatientCategoryAdapter extends FragmentPagerAdapter {
    private Context mContext;

    public PatientCategoryAdapter(Context context,FragmentManager fm) {
        super(fm);
        mContext=context;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0)
            return "Devices";
        else
            return "Logs Devices";
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0)
            return new CurrentStatusDevicesFragment();
        else
            return new LogsFragment();

    }

    @Override
    public int getCount() {
        return 2;
    }
}
