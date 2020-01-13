package com.example.android.aide;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class CaretakerCategoryAdapter extends FragmentPagerAdapter {
    private Context mContext;

    public CaretakerCategoryAdapter(Context context, FragmentManager fm){
        super(fm);
        mContext=context;
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0)
            return new NotificationsFragment();
        else
            return new PatientProfileFragment();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0)
            return mContext.getString(R.string.category_notifications);
        else
            return  "Patient Profile";
    }

    @Override
    public int getCount() {
        return 2;
    }
}
