/*
 *
 * Project Name: 	e-yantra Project:AIDE(AI and IoT Enabled home automation for Disabled and Elderly
 * Author List: 	Srushti Sachdev,Smruti Kshirsagar
 * Filename: 		CurrentStatusDevicesFragment.java
 * Functions: 		listenHouseId(),loadGestureImage()
 * Global Variables:	NONE
 *
 */

package com.example.android.aide;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

public class CurrentStatusDevicesFragment extends Fragment {
    private ImageView imageView1; /*variable of type ImageView storing the image for switching on the device*/
    private ImageView imageView2;/*variable of type ImageView storing the image for switching off the device*/
    private TextView textViewData;/*variable of type TextView storing the device information*/
    private String houseId; /*variable of type String storing the houseId of user*/
    private String userId; /*variable of type String storing the userId of user*/
    private String data;    /*variable of type String temporary storing device information*/
    private String g_on;    /*variable of type String storing the status of device whether it is switched on or not*/
    private String g_off;   /*variable of type String storing the status of device whether it is switched off or not*/
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.device_profile, container, false);
        textViewData=(TextView)rootView.findViewById(R.id.textViewData);
        imageView1=(ImageView)rootView.findViewById(R.id.device_on_gesture);
        imageView2=(ImageView)rootView.findViewById(R.id.device_off_gesture);
        userId = firebaseAuth.getCurrentUser().getUid();
        firebaseFirestore.collection("Users")
                .document(userId).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                houseId = document.getString("HOUSE_ID");
                                listenHouseId();

                            } else {
                                Log.d("TAG", "No such document");
                            }
                        } else {
                            Log.d("TAG", "get failed with ", task.getException());
                        }
                    }
                });
        return rootView;
    }
    /*
     *
     * Function Name: 	listenHouseId
     * Input: 		NONE
     * Output: 		Displays the device information in the screen.
     * Logic: 		The function retrieves device information from database and displays it.
     * Example Call:   listenHouseId()
     */
    public void listenHouseId(){
        CollectionReference cRef = firebaseFirestore.collection("Devices");
        cRef.whereEqualTo("HOUSE_ID", houseId).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                if(e!=null){
                    Log.w("TAG","Listen Failed",e);
                    return;
                }
                data="";

                for(QueryDocumentSnapshot document:queryDocumentSnapshots){
                    Device device = document.toObject(Device.class);
                    String name = device.getName();
                    String type = device.getType();
                    String status = device.getStatus();
                    g_on=document.getString("GESTURE_ON");
                    g_off=document.getString("GESTURE_OFF");
                    loadGestureImage();
                    data+= "\nName: "+name+"\nType of device: "+type+"\nStatus: "+status+"\nGesture On: "+g_on+"\nGesture Off: "+g_off;
                }
                textViewData.setText(data);
            }
        });
    }
    /*
     *
     * Function Name: 	loadGestureImage()
     * Input: 		NONE
     * Output: 		Displays the gesture image on screen.
     * Logic: 		The function retreives the gesture image from document of current respective gesture having image
      *             from collection "Gestures" and then displays it.
     * Example Call:   loadGestureImage()
     */
   public void loadGestureImage(){
        DocumentReference documentReference1=firebaseFirestore.collection("Gestures").document(g_on);
        documentReference1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot doc=task.getResult();
                    String img_url=(doc.get("Img")).toString();
                    Picasso.get().load(img_url).into(imageView1);
                }
            }
        });
       DocumentReference documentReference2=firebaseFirestore.collection("Gestures").document(g_off);
       documentReference2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
           @Override
           public void onComplete(@NonNull Task<DocumentSnapshot> task) {
               if(task.isSuccessful()){
                   DocumentSnapshot doc=task.getResult();
                   String img_url=(doc.get("Img")).toString();
                   Picasso.get().load(img_url).into(imageView2);
               }
           }
       });
    }
}