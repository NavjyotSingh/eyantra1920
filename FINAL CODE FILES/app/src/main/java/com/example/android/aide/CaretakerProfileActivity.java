/*
 *
 * Project Name: 	e-yantra Project:AIDE(AI and IoT Enabled home automation for Disabled and Elderly
 * Author List: 	Srushti Sachdev,Smruti Kshirsagar
 * Filename: 		CaretakerProfileActivity.java
 * Functions: 		listenHouseId()
 * Global Variables:	NONE
 *
 */

package com.example.android.aide;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class CaretakerProfileActivity extends AppCompatActivity {
    private TextView name_text;  /*variable of type TextView which stores name of the caretaker and accepts only String as input*/
    private TextView username_text;/*variable of type TextView which stores username of the caretaker and accepts only String as input*/
    private TextView contact_text;/*variable of type TextView which stores contact number of the caretaker and accepts only String as input*/
    private TextView user_role_text;/*variable of type TextView which stores role of the user and accepts only String as input*/
    private TextView patient_name_text;/*variable of type TextView which stores name of the patient and accepts only String as input*/
    private TextView patient_age_text;/*variable of type TextView which stores age of the patient and accepts only String as input*/
    private TextView patient_contact_text;/*variable of type TextView which stores contact of the patient and accepts only String as input*/
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();/*object of FirebaseFrestore*/
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();/*object of FirebaseAuth*/
    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();/*variable of type String storing the userId of the user*/
    String houseId;/*variable of type String which stores houseId of user and accepts only String as input*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.caretaker_profile);
        name_text = (TextView) findViewById(R.id.name);
        username_text = (TextView) findViewById(R.id.username_text);
        contact_text = (TextView) findViewById(R.id.contact_text);
        user_role_text = (TextView) findViewById(R.id.userRole_text);
        patient_name_text = (TextView) findViewById(R.id.patientName_text);
        patient_age_text = (TextView) findViewById(R.id.patientAge_text);
        patient_contact_text = (TextView) findViewById(R.id.patientContact_text);

        firebaseFirestore.collection("Users").document(userId).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                houseId = document.getString("HOUSE_ID");
                                String name = document.getString("NAME");
                                String user_name = document.getString("USERNAME");
                                String contact = document.getString("CONTACT");
                                String user_role = document.getString("USER_ROLE");
                                name_text.setText(name);
                                username_text.setText(user_name);
                                contact_text.setText(contact);
                                user_role_text.setText(user_role);
                                listenHouseId();
                            } else {
                                Log.d("TAG", "No such document");
                            }
                        } else {
                            Log.d("TAG", "get failed with ", task.getException());
                        }
                    }
                });}
    /*
     *
     * Function Name: 	listenHouseId
     * Input: 		NONE
     * Output: 		Retreives houseId from the document from firestore.
     * Logic: 		The function retreives the patient information such as name,age,contact from database and displays this
     *              information.
     * Example Call:   listenHouseId()
     */
   public void listenHouseId(){
        CollectionReference cRef = firebaseFirestore.collection("Users");
        cRef.whereEqualTo("HOUSE_ID", houseId).whereEqualTo("USER_ROLE","PATIENT").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                if(e!=null){
                    Log.w("TAG","Listen Failed",e);
                    return;
                }
                for(QueryDocumentSnapshot document:queryDocumentSnapshots){
                    String name=document.getString("PATIENT_NAME");
                    String contact=document.getString("PATIENT_CONTACT");
                    String age=document.getString("PATIENT_AGE");
                    patient_name_text.setText(name);
                    patient_age_text.setText(age);
                    patient_contact_text.setText(contact);
                }
            }
        });
    }
}