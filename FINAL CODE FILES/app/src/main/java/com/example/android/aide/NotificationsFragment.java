/*
 *
 * Project Name: 	e-yantra Project:AIDE(AI and IoT Enabled home automation for Disabled and Elderly
 * Author List: 	Srushti Sachdev
 * Filename: 		NotificationsFragment.java
 * Functions: 		NONE
 * Global Variables:	NONE
 *
 */

package com.example.android.aide;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class NotificationsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.list,container,false);
        Toast.makeText(getContext(), R.string.category_toast, Toast.LENGTH_SHORT).show();
        return rootView;
    }
}