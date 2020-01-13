package com.example.android.aide;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class GesturesMappingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gesture_mapping_list);
        final ArrayList<GestureMapping> gestures=new ArrayList<GestureMapping>();
        gestures.add(new GestureMapping("Device Control Mode",R.drawable.device_controll_mode));
        gestures.add(new GestureMapping("Light On",R.drawable.light_on));
        gestures.add(new GestureMapping("Light Off",R.drawable.light_off));
        gestures.add(new GestureMapping("Fan on",R.drawable.fan_on));
        gestures.add(new GestureMapping("Fan off",R.drawable.fan_off));
        gestures.add(new GestureMapping("Window Open",R.drawable.window_open));
        gestures.add(new GestureMapping("Window Close",R.drawable.window_close));
        gestures.add(new GestureMapping("Increase speed of fan",R.drawable.increase_speed_fan));
        gestures.add(new GestureMapping("Decrease speed of fan",R.drawable.decrease_speed_fan));
        GestureMappingAdapter adapter=new GestureMappingAdapter(this,gestures);

        ListView listView = (ListView) findViewById(R.id.gesture_list);
        listView.setAdapter(adapter);
    }
}
