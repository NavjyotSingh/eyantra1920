/*
 *
 * Project Name: 	e-yantra Project:AIDE(AI and IoT Enabled home automation for Disabled and Elderly
 * Author List: 	Srushti Sachdev,Smruti Kshirsagar
 * Filename: 		LoginActivity.java
 * Functions: 		getUserRole(),startProfileActivity()
 * Global Variables:	NONE
 *
 */
package com.example.android.aide;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextEmail;
    private EditText editTextPassword;
    private ProgressDialog progressDialog;
    private String userId;
    private String user_role;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_form);    //setting the login_form layout
        firebaseAuth = FirebaseAuth.getInstance();  //getting firebase auth object
        firebaseFirestore=FirebaseFirestore.getInstance();
        editTextEmail = (EditText) findViewById(R.id.user_name_login);
        editTextPassword = (EditText) findViewById(R.id.pwd_login);
        progressDialog = new ProgressDialog(this);
         Button log_but=(Button)findViewById(R.id.log_but);
        log_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
               final String password  = editTextPassword.getText().toString();


                //checking if email and passwords are empty
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(LoginActivity.this,"Please enter email",Toast.LENGTH_LONG).show();
                    return;
                }

                if(TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "Please enter password", Toast.LENGTH_LONG).show();
                    return;
                }
                progressDialog.setMessage("Logging in Please Wait...");
                progressDialog.show();


                //logging in the user
                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(Task<AuthResult> task) {
                                progressDialog.dismiss();
                                //if the task is successfull
                                if (task.isSuccessful()) {
                                   getUserRole();
                                }
                            }});

    }
});
    }
    /*
     *
     * Function Name: 	getUserRole
     * Input: 		NONE
     * Output: 		Retreives user role from the database.
     * Logic: 		The function retreives the user_role from document of current userId from collection "Users".
     *              It then gives this information to startProfileActivity() for further processing.
     * Example Call:   getUserRole()
     */
    public void getUserRole(){
        userId=firebaseAuth.getCurrentUser().getUid();
        firebaseFirestore.collection("Users")
                .document(userId).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                user_role=document.getString("USER_ROLE");
                                startProfileActivity();
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
     * Function Name: 	startProfileActivity
     * Input: 		NONE
     * Output: 		Starts new activity according to the user role.
     * Logic: 		The functio uses the value of user role and starts patient activity if user is patient or starts
     *              caretaker activity is the user is caretaker
     * Example Call:   startProfileActivity()
     */
    public void startProfileActivity(){
        if(user_role.equalsIgnoreCase("Patient")){
            Intent intent1=new Intent(LoginActivity.this,PatientRegistrationActivity.class);
            startActivity(intent1);
        }
        else{
            Intent intent2=new Intent(LoginActivity.this,CaretakerRegistrationActivity.class);
            startActivity(intent2);
        }
    }
}