package com.example.android.aide;

import android.app.Activity;
import android.gesture.Gesture;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class GestureMappingAdapter extends ArrayAdapter {
    public GestureMappingAdapter(Activity context, ArrayList<GestureMapping> gestures){
        super(context,0,gestures);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView=convertView;
        if(listItemView==null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.gesture_mapping, parent, false);
        }
        GestureMapping currentGesture= (GestureMapping) getItem(position);
        TextView gestureName=(TextView)listItemView.findViewById(R.id.gesture_name);
        gestureName.setText(currentGesture.getmGestureName());

        ImageView imageView=(ImageView)listItemView.findViewById(R.id.gesture_image);
        imageView.setImageResource(currentGesture.getmImgResourceId());
        imageView.setVisibility(View.VISIBLE);
        return  listItemView;
        }
    }

