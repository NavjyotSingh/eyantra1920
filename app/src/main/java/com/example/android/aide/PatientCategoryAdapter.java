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
            return "Current Status of Devices";
        else
            return "My Profile";
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0)
            return new CurrentStatusDevicesFragment();
        else
            return new PatientProfileFragment();

    }

    @Override
    public int getCount() {
        return 2;
    }
}
