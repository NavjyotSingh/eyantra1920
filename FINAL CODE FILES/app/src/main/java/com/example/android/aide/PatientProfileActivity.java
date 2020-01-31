/*
 *
 * Project Name: 	e-yantra Project:AIDE(AI and IoT Enabled home automation for Disabled and Elderly
 * Author List: 	Srushti Sachdev,Smruti Kshirsagar
 * Filename: 		PatientProfileActivity.java
 * Functions: 		NONE
 * Global Variables:	NONE
 *
 */

package com.example.android.aide;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class PatientProfileActivity extends AppCompatActivity {
    private TextView name_text;
    private TextView username_text;
    private TextView contact_text;
    private TextView user_role_text;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);
            name_text=(TextView)findViewById(R.id.name);
            username_text=(TextView)findViewById(R.id.username_text);
            contact_text=(TextView)findViewById(R.id.contact_text);
            user_role_text=(TextView)findViewById(R.id.userRole_text);
            firebaseFirestore.collection("Users").document(userId).get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    String name=document.getString("NAME");
                                    String user_name=document.getString("USERNAME");
                                    String contact=document.getString("CONTACT");
                                    String user_role=document.getString("USER_ROLE");
                                    name_text.setText(name);
                                    username_text.setText(user_name);
                                    contact_text.setText(contact);
                                    user_role_text.setText(user_role);
                                    //textViewData.setText("Name: " + name + "\n" + "Username: " + user_name + "\n" + "User Role: " + user_role + "\n" + "Contact: " + contact);
                                } else {
                                    Log.d("TAG", "No such document");
                                }
                            } else {
                                Log.d("TAG", "get failed with ", task.getException());
                            }
                        }
                    });





        }}