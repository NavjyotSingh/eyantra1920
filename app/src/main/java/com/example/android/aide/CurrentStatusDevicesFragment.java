package com.example.android.aide;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CurrentStatusDevicesFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.list,container,false);
        //ImageButton imageButton= rootView.findViewById(R.id.add_img_button);
        /*imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editDevicesIntent = new Intent(CurrentStatusDevicesFragment.this.getActivity(), EditDevicesActivity.class);
                startActivity(editDevicesIntent);
            }
        });*/
        return rootView;
    }
}