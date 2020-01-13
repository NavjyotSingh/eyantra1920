//This is the first screen of app.It displays the registration form for the user.

package com.example.android.aide;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {
    private EditText editTextName;
    private EditText editTextUsername;   //defining variable for username of user
    private EditText editTextPassword;   //defining variable for password of user
    private EditText editTextContact;
    private ProgressDialog progressDialog;  //defining progress dialog object
    private FirebaseAuth firebaseAuth;//defining firebaseauth object
    private FirebaseFirestore firebaseFirestore;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    DocumentReference documentReference;
    private static final String NAME="name";
    private static final String USERNAME="username";
    private static final String CONTACT="contact";
    private static final String USER_ROLE="user_role";
    private String name;
    private String username;
    private String contact;
    private String user_role;
    private FirebaseUser firebaseUser;      //defining firebaseuser object
    private Button login_btn;               //defining variable for login button


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);     //set activity_main layout
        firebaseAuth = FirebaseAuth.getInstance();//getting the url of firebase
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();     //getting the current user logged in
        documentReference=firebaseFirestore.collection("Houses").document("Users");
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
                registerUser();
                name=editTextName.getText().toString();
                username=editTextUsername.getText().toString();
                contact=editTextContact.getText().toString();
                int selectedId=radioGroup.getCheckedRadioButtonId();
                radioButton=(RadioButton)findViewById(selectedId);
                saveUserInfo(name,username,contact,radioButton.getText().toString());
            }

            private void registerUser() {

                //getting email and password from edit texts
                String email = editTextUsername.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                //checking if email and passwords are empty
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(RegistrationActivity.this, "Please enter email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(RegistrationActivity.this, "Please enter password", Toast.LENGTH_LONG).show();
                    return;
                }

                progressDialog.setMessage("Registering Please Wait...");
                progressDialog.show();

                //creating a new user
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                            private Task<AuthResult> task;

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                this.task = task;
                                //checking if success
                                if (task.isSuccessful()) {
                                    //display some message here
                                    Toast.makeText(RegistrationActivity.this, "Successfully registered", Toast.LENGTH_LONG).show();
                                    Intent intent=new Intent(RegistrationActivity.this,EditDevicesActivity.class);
                                    startActivity(intent);
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
    public void saveUserInfo(String name,String username,String contact,String user_role){
        Map<String,String> userMap=new HashMap<>();
        userMap.put(NAME,name);
        userMap.put(USERNAME,username);
        userMap.put(CONTACT,contact);
        userMap.put(USER_ROLE,user_role);
        documentReference.collection("Users").add(userMap);
    }
}
