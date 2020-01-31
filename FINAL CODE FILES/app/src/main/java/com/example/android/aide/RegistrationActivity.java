/*
 *
 * Project Name: 	e-yantra Project:AIDE(AI and IoT Enabled home automation for Disabled and Elderly
 * Author List: 	Srushti Sachdev,Smruti Kshirsagar
 * Filename: 		RegistrationActivity.java
 * Functions: 		registerUser(),validateEmail(),validateName(),validatePassword(),validateContact()
 * Global Variables:	NONE
 *
 */

package com.example.android.aide;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {
    private static final String NAME="name";
    private static final String USERNAME="username";
    private static final String CONTACT="contact";
    private static final String USER_ROLE="user_role";
    private static final String HOUSE_ID="houseId";
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{6,}" +               //at least 6 characters
                    "$");
    private EditText editTextName;
    private EditText editTextUsername;   //defining variable for username of user
    private EditText editTextPassword;   //defining variable for password of user
    private EditText editTextContact;
    private Button login_btn;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private ProgressDialog progressDialog;
    private String User_id="";
    private String name;
    private String username;
    private String contact;
    private String user_role;
    private String userId;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseUser firebaseUser;
    DocumentReference documentReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);     //set activity_main layout
        firebaseAuth = FirebaseAuth.getInstance();//getting the url of firebase
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();     //getting the current user logged in
        documentReference=firebaseFirestore.collection("Houses/").document("Users");
        //initializing each variable with their corressponding id
        editTextName=(EditText)findViewById(R.id.name_editText);
        editTextUsername = (EditText) findViewById(R.id.username_editText);
        editTextPassword = (EditText) findViewById(R.id.pwd_editText);
        editTextContact = (EditText) findViewById(R.id.contact_editText);
        radioGroup=(RadioGroup)findViewById(R.id.user_role);
        login_btn = (Button) findViewById(R.id.login_btn);
        Button register_button = (Button) findViewById(R.id.register_btn);
        progressDialog = new ProgressDialog(this);

        //if user is still logged in then it will open the user screen directly
        if(firebaseUser!=null){
            startActivity(new Intent(RegistrationActivity.this, PatientRegistrationActivity.class));
        }
        //going to Login screen after click on login button
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        //registeration of new user
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateEmail() | !validateName() | !validatePassword() | !validateContact()) {
                    return;
                }
                else{ registerUser();}
                name=editTextName.getText().toString();
                username=editTextUsername.getText().toString();
                contact=editTextContact.getText().toString();
                int selectedId=radioGroup.getCheckedRadioButtonId();
                radioButton=(RadioButton)findViewById(selectedId);
                user_role=radioButton.getText().toString();
            }
            /*
             *
             * Function Name: 	registerUser
             * Input: 		NONE
             * Output: 		Saves the user information into the database
             * Logic: 		The function take the user information from as input from the registratio form and stores the information
             *              into the firestore database.
             * Example Call:   registerUser()
             */
            private void registerUser() {

                //getting email and password from edit texts

                String email = editTextUsername.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                progressDialog.setMessage("Registering Please Wait...");
                progressDialog.show();

                //creating a new user
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete( Task<AuthResult> task) {

                                //checking if success
                                if (task.isSuccessful()) {
                                    Toast.makeText(RegistrationActivity.this, "Successfully registered", Toast.LENGTH_LONG).show();
                                    Map<String,Object> house=new HashMap<>();
                                    house.put("houseId",contact);
                                    house.put("numDevices",0);
                                    firebaseFirestore.collection("Houses").document(contact).set(house);
                                    userId=firebaseAuth.getCurrentUser().getUid();
                                    DocumentReference documentReference=firebaseFirestore.collection("Users").document(userId);
                                    Map<String,Object> user=new HashMap<>();
                                    user.put("NAME",name);
                                    user.put("USERNAME",username);
                                    user.put("CONTACT",contact);
                                    user.put("USER_ROLE",user_role);
                                    user.put("HOUSE_ID",contact);
                                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d("TAG","User profile is created for "+userId);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d("TAG",e.toString());
                                        }
                                    });
                                    if(user_role.equals("Caretaker")){
                                    Intent intent=new Intent(RegistrationActivity.this,AddPatientDetails.class);
                                    startActivity(intent);}
                                    else{
                                        Intent intent1=new Intent(RegistrationActivity.this,EditDevicesActivity.class);
                                        startActivity(intent1);
                                    }
                                } else {
                                    //display some message here
                                    Toast.makeText(RegistrationActivity.this, "Registration Error", Toast.LENGTH_LONG).show();
                                }
                                progressDialog.dismiss();
                            }
                        });
            }

        });
    }
    /*
     *
     * Function Name: 	validateEmail
     * Input: 		NONE
     * Output: 		return type is boolean.True if email is correct else false.
     * Logic: 		The function checks the email given by user.If the email field is empty or does not match the standard then
     *              it returns false else it returns true
     * Example Call:   validateEmail()
     */
    private boolean validateEmail() {
        String email = editTextUsername.getText().toString().trim();
        if (email.isEmpty()) {
            editTextUsername.setError("Field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextUsername.setError("Please enter a valid email address");
            return false;
        } else {
            editTextUsername.setError(null);
            return true;
        }
    }
    /*
     *
     * Function Name: 	validateName
     * Input: 		NONE
     * Output: 		return type is boolean.True if name is correct else false.
     * Logic: 		The function checks the name given by user.If the name field is empty or is too long then
     *              it returns false else it returns true
     * Example Call:   validateName()
     */
    private boolean validateName() {
        String nameInput = editTextName.getText().toString().trim();
        if (nameInput.isEmpty()) {
            editTextName.setError("Field can't be empty");
            return false;
        } else if (nameInput.length() > 15) {
            editTextName.setError("Name too long");
            return false;
        } else {
            editTextName.setError(null);
            return true;
        }
    }
    /*
     *
     * Function Name: 	validatePassword
     * Input: 		NONE
     * Output: 		return type is boolean.True if password is correct else false.
     * Logic: 		The function checks the password given by user.If the password field is empty or does not match standards then
     *              it returns false else it returns true
     * Example Call:   validatePassword()
     */
    private boolean validatePassword() {
        String password = editTextPassword.getText().toString().trim();

        if (password.isEmpty()) {
            editTextPassword.setError("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            editTextPassword.setError("Password too weak");
            return false;
        } else {
            editTextPassword.setError(null);
            return true;
        }
    }

    /*
     *
     * Function Name: 	validateContact
     * Input: 		NONE
     * Output: 		return type is boolean.True if contat is correct else false.
     * Logic: 		The function checks the contact given by user.If the contact field is empty then
     *              it returns false else it returns true
     * Example Call:   validateContact()
     */
    private boolean validateContact() {
        String contact = editTextContact.getText().toString().trim();

        if (contact.isEmpty()) {
            editTextContact.setError("Field can't be empty");
            return false;
        } else {
            editTextContact.setError(null);
            return true;
        }
    }

}