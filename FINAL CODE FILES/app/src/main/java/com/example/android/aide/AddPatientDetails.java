 /*
  *
  * Project Name: 	e-yantra Project:AIDE(AI and IoT Enabled home automation for Disabled and Elderly
  * Author List: 	Srushti Sachdev,Smruti Kshirsagar
  * Filename: 		AddPatientDetails.java
  * Functions: 		listenHouseId(),savePatientInfo()
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class AddPatientDetails extends AppCompatActivity {
    private EditText patientContact;/*variable of type EditText which stores contact number of patient and aceepts String input*/
    private EditText patientName;   /*variable of type EditText which stores name of patient and accepts String input*/
    private EditText patientAge;    /*variable of type EditText which stores age of patient and accepts String input*/
    private Button done;            /*variable of type Button which on click goes to other screen*/
    private RadioGroup radioGroup;  /*variable of type RadioGroup.It is a group of two radio buttons*/
    private RadioButton radioButton;/*variable of type radioButton which will store the selected option*/
    private String patient_name;    /*variable of type String which stores contact number of patient*/
    private String patient_age;     /*variable of type String which stores age of patient*/
    private String patient_contact; /*variable of String which stores name of patient*/
    private String houseId;         /*variable of type String which stores houseId of the user.*/
    private String userId;          /*variable of type String which stores userId of the user.*/
    private String patient_type;    /*variable of type String which stores the information(disabled or elder) of the user.*/
    private FirebaseAuth firebaseAuth;/*object of FirebaseAuth.*/
    private FirebaseFirestore firebaseFirestore;/*object of FirebaseFirestore.*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_patient_details);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        done=(Button)findViewById(R.id.done);
        patientName=(EditText)findViewById(R.id.patient_name_editText);
        patientAge=(EditText)findViewById(R.id.patient_age_editText);
        patientContact=(EditText)findViewById(R.id.patient_contact_editText);
        radioGroup=(RadioGroup)findViewById(R.id.patient_type);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenHouseId();
               }
            /*
             *
             * Function Name: 	listenHouseId
             * Input: 		NONE
             * Output: 		Retreives houseId from the document from firestore.
             * Logic: 		The function retreives the HOUSE_ID from document of current userId from collection "Users".
             *              It also receives other patient information from edit text and this information is passed on to
             *              savePatientInfo() function.
             * Example Call:   listenHouseId()
             */
            public void listenHouseId(){
                userId = firebaseAuth.getCurrentUser().getUid();
                firebaseFirestore.collection("Users")
                        .document(userId).get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        patient_name=patientName.getText().toString().trim();
                                        patient_age=patientAge.getText().toString().trim();
                                        patient_contact=patientContact.getText().toString();
                                        int selectedId=radioGroup.getCheckedRadioButtonId();
                                        radioButton=(RadioButton)findViewById(selectedId);
                                        patient_type=radioButton.getText().toString();
                                        houseId = document.getString("HOUSE_ID");
                                        savePatientInfo(patient_name,patient_age,patient_contact,houseId,patient_type);
                                    } else {
                                        Log.d("TAG", "No such document");
                                    }
                                } else {
                                    Log.d("TAG", "get failed with ", task.getException());
                                }
                            }
                        });
            }
            /*
             *
             * Function Name: 	savePatientInfo
             * Input: 		name->stores the name of patient
             *              age->store age of the patient
             *              contact->stores the contact number of the patient
             *              houseId->stores the houseId of the user
             *              type->stores the category of the patient
             * Output: 		Stores the received patient information into firestore database.
             * Logic: 		Stores the received patient information into firestore database.
             * Example Call:   savePatientInfo("Rahul","60","9817234560","value from database","Disabled")
             */
public void savePatientInfo(String name,String age,String contact,String houseId,String type){
    Map<String,String> patient=new HashMap<>();
    patient.put("PATIENT_NAME",name);
    patient.put("PATIENT_AGE",age);
    patient.put("PATIENT_CONTACT",contact);
    patient.put("HOUSE_ID",houseId);
    patient.put("PATIENT TYPE",type);
    patient.put("USER_ROLE","PATIENT");
    firebaseFirestore.collection("Users").add(patient).addOnSuccessListener(new OnSuccessListener<DocumentReference>(){
        @Override
        public void onSuccess(DocumentReference documentReference) {
            Intent intent=new Intent(AddPatientDetails.this,EditDevicesActivity.class);
            startActivity(intent);
        }

    })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddPatientDetails.this,"Error",Toast.LENGTH_LONG).show();
                    Log.d(String.valueOf(AddPatientDetails.this),e.toString());
                }
            });
}
        });
}
}