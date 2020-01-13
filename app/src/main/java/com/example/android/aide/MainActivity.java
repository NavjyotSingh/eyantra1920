

package com.example.android.aide;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 2000;
   /* private EditText editTextUsername;   //defining variable for username of user
    private EditText editTextPassword;   //defining variable for password of user
    private EditText editTextContact;
    private ProgressDialog progressDialog;  //defining progress dialog object
    private FirebaseAuth firebaseAuth;      //defining firebaseauth object
    private FirebaseUser firebaseUser;      //defining firebaseuser object
    private Button login_btn;               //defining variable for login button
    private Button cont_btn;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);   //set activity_main layout
        //setting the splash screen
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(MainActivity.this, RegistrationActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
        /*firebaseAuth = FirebaseAuth.getInstance();  //getting the url of firebase
        firebaseUser=firebaseAuth.getCurrentUser();     //getting the current user logged in
        //initializing each variable with their corressponding id
        editTextUsername = (EditText) findViewById(R.id.username_editText);
        editTextPassword = (EditText) findViewById(R.id.pwd_editText);
        editTextContact = (EditText) findViewById(R.id.contact_editText);
        login_btn = (Button) findViewById(R.id.login_btn);
        Button register_button = (Button) findViewById(R.id.register_btn);
        progressDialog = new ProgressDialog(this);
        //if user is still logged in then it will open the user screen directly
        if(firebaseUser!=null){
            startActivity(new Intent(MainActivity.this, PatientRegistrationActivity.class));
        }
        //going to Login screen after click on login button
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        //registeration of new user
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }

            private void registerUser() {

                //getting email and password from edit texts
                String email = editTextUsername.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                //checking if email and passwords are empty
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(MainActivity.this, "Please enter email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(MainActivity.this, "Please enter password", Toast.LENGTH_LONG).show();
                    return;
                }

                progressDialog.setMessage("Registering Please Wait...");
                progressDialog.show();

                //creating a new user
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            private Task<AuthResult> task;

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                this.task = task;
                                //checking if success
                                if (task.isSuccessful()) {
                                    //display some message here
                                    Toast.makeText(MainActivity.this, "Successfully registered", Toast.LENGTH_LONG).show();
                                    Intent intent=new Intent(MainActivity.this,EditDevicesActivity.class);
                                    startActivity(intent);
                                } else {
                                    //display some message here
                                    Toast.makeText(MainActivity.this, "Registration Error", Toast.LENGTH_LONG).show();
                                }
                                progressDialog.dismiss();
                            }
                        });
            }

        });
    }*/
}}
