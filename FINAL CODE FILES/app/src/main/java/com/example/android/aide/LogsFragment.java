/*
 *
 * Project Name: 	e-yantra Project:AIDE(AI and IoT Enabled home automation for Disabled and Elderly
 * Author List: 	Srushti Sachdev,Smruti Kshirsagar
 * Filename: 		LogsFragment.java
 * Functions: 		NONE
 * Global Variables:	NONE
 *
 */

package com.example.android.aide;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LogsFragment extends Fragment {
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    TextView devices;
    String userId,houseId;
    String TAG="tag";
    String name, time, status, date;
    String dev;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View rootView=inflater.inflate(R.layout.show_devices,container,false);
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        devices = rootView.findViewById(R.id.devices);
        userId = fAuth.getCurrentUser().getUid();
        /*Retrives the device log information from database and diaplys the same */
        CollectionReference cRef = fStore.collection("Logs").document(houseId).collection("Logs");
        cRef.orderBy("timestamp").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                Toast.makeText(getContext(),"Reading",Toast.LENGTH_LONG);
                if(e!=null){
                    Log.w(TAG,"Listen failed",e);
                    return;
                }
                dev = "";
                for(QueryDocumentSnapshot document : queryDocumentSnapshots){
                    name = document.getString("name");
                    status = document.getString("status");
                    time = document.getString("timestamp");
                    date = time.substring(5,10);
                    time = time.substring(12,16);
                    dev = "\n"+name+" switched "+status+" on "+date+" at "+time+".\n\n" + dev;
                }
                devices.setText(dev);
            }

        });
        return  rootView;
    }
}
