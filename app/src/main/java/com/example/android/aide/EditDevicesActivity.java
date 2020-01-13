//This will ask user to add information about the electrical devices.
package com.example.android.aide;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditDevicesActivity extends AppCompatActivity {
    private Spinner mDeviceSpinner;
    private int mDevice=0;
    private String selection;
    private EditText device_name;
    private static final String KEY_TITLE="title";
    private static final String KEY_DESCRIPTION="description";
    private String title;
    private String description;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_devices);
        Button done_button=(Button)findViewById(R.id.done_btn);
        device_name = (EditText) findViewById(R.id.device_name_edit_text);
        mDeviceSpinner = (Spinner) findViewById(R.id.spinner_type_of_device);
        setupSpinner();

        done_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title=device_name.getText().toString();
                saveDeviceInfo(title,description);

            }
        });

    }
    //this method will store the devices info into database
    public void saveDeviceInfo(String title,String description){

        Map<String,String> deviceMap=new HashMap<>();
        deviceMap.put(KEY_TITLE,title);
        deviceMap.put(KEY_DESCRIPTION,description);
        db.collection("Houses").add(deviceMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>(){
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(EditDevicesActivity.this,"Device inserted successfully",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(EditDevicesActivity.this, GesturesMappingActivity.class);
                startActivity(intent);
            }


        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditDevicesActivity.this,"Error",Toast.LENGTH_LONG).show();
                        Log.d(String.valueOf(EditDevicesActivity.this),e.toString());
                    }
                });
    }


    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter<CharSequence> deviceSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_type_of_device_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        deviceSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mDeviceSpinner.setAdapter(deviceSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mDeviceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selection = (String) parent.getItemAtPosition(position).toString();
                description=selection;
                //device_name.setText(selection);
                //if (!TextUtils.isEmpty(selection)) {
                  //  if (selection.equals(getString(R.string.category_fan))) {
                    //    mDevice = 1; // Fan
                    //} else if (selection.equals(getString(R.string.category_bulb))) {
                      //  mDevice = 2; // Bulb
                    //} else {
                      //  mDevice = 3; // Window
                    //}
                //}
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //mDevice=0;  //Unknown
            }


        });

    }}
