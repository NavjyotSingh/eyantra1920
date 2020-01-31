/*
 *
 * Project Name: 	e-yantra Project:AIDE(AI and IoT Enabled home automation for Disabled and Elderly
 * Author List: 	Srushti Sachdev,Smruti Kshirsagar
 * Filename: 		EditDevicesActivity.java
 * Functions: 		saveDeviceInfo(String,String,String,String,String,String)
 * Global Variables:	NONE
 *
 */
package com.example.android.aide;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditDevicesActivity extends AppCompatActivity {
    private EditText device_name;/*variable of type EditText storing the name of device*/
    private TextView add_devices_message;
    private Spinner mDeviceSpinner;
    private String selection;
    private String title;
    private String description;
    private String userId;
    private String houseId; /*variable of type String storing the houseId of the user*/
    private String numDevices;/*variable of type String storing the number of Devices in house*/
    private int nDevices;/*counter to store number of devices in house*/
    private String d_id;/*variable of type String storing the device Id of the device*/
    private String user_role;
    private String gOn;
    private String gOf;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_devices);
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();
        add_devices_message=(TextView)findViewById(R.id.add_devices_msg);
        Button done_button=(Button)findViewById(R.id.done_btn);
        Button add_button=(Button)findViewById(R.id.add_btn);
        device_name = (EditText) findViewById(R.id.device_name_edit_text);
        mDeviceSpinner = (Spinner) findViewById(R.id.spinner_type_of_device);
        setupSpinner();

        firebaseFirestore.collection("Users")
                .document(userId).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                houseId = document.getString("HOUSE_ID");
                                user_role=document.getString("USER_ROLE");
                            } else {
                                Log.d("TAG", "No such document");
                            }
                        } else {
                            Log.d("TAG", "get failed with ", task.getException());
                        }
                    }
                });
        numDevices="0";
        firebaseFirestore.collection("Houses")
                .document("houseId").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                numDevices = document.getString("numDevices"); //fetch number of devices from house collection
                            } else {
                                Log.d("TAG", "No such document");
                            }
                        } else {
                            Log.d("TAG", "get failed with ", task.getException());
                        }
                    }
                });
        nDevices = Integer.parseInt(numDevices);
        String n = Integer.toString(nDevices+1);
        d_id = "D"+n;
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title=device_name.getText().toString();
                saveDeviceInfo(title,description,houseId,gOn,gOf,d_id);
            }
        });
        done_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user_role.equalsIgnoreCase("Patient")){
                    Intent intent1=new Intent(EditDevicesActivity.this,PatientRegistrationActivity.class);
                    startActivity(intent1);
                }
                else{
                    Intent intent2=new Intent(EditDevicesActivity.this,CaretakerRegistrationActivity.class);
                    startActivity(intent2);
                }
            }
        });
    }
    /*
     *
     * Function Name: 	saveDeviceInfo
     * Input: 		device type,device name,house id,device switchd on status,device swtiched off status,device id
     * Output: 		saves the device info into the database
     * Logic: 		The function receives information and store in the database.
     * Example Call:   saveDeviceInfo()
     */
    public void saveDeviceInfo(final String title, String description,String houseId,String gOn, String gOf,String d_id){
        Map<String,String> deviceMap=new HashMap<>();
        //gestureMapping
        switch(nDevices){
            case 0: gOn = "G1";
                gOf = "G2";
                break;
            case 1: gOn = "G3";
                gOf = "G4";
                break;
            case 2: gOn = "G5";
                gOf = "G6";
                break;
            case 3: gOn = "G7";
                gOf = "G8";
                break;
            default: Toast.makeText(EditDevicesActivity.this,"You cannot add more devices now",Toast.LENGTH_SHORT).show();
        }
        deviceMap.put("DEVICE_ID",d_id);
        deviceMap.put("NAME",title);
        deviceMap.put("TYPE",description);
        deviceMap.put("HOUSE_ID",houseId);
        deviceMap.put("STATUS","OFF");
        deviceMap.put("GESTURE_ON",gOn);
        deviceMap.put("GESTURE_OFF",gOf);
        firebaseFirestore.collection("Devices").add(deviceMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>(){
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(EditDevicesActivity.this,"Device inserted successfully",Toast.LENGTH_LONG).show();
                add_devices_message.setText("You can add more devices now!");
                device_name.setText(" ");

            }

        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditDevicesActivity.this,"Error",Toast.LENGTH_LONG).show();
                        Log.d(String.valueOf(EditDevicesActivity.this),e.toString());
                    }
                });
        //update number of devices in Houses
        nDevices+=1;
        DocumentReference dRef = firebaseFirestore.collection("Houses").document(houseId);
        dRef.update("numDevices",nDevices).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("TAG", "DocumentSnapshot successfully updated!");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error updating document", e);
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
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //mDevice=0;  //Unknown
            }
        });
    }

}