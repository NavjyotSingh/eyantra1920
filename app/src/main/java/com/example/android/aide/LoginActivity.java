//This will diaplay login form for the user.
package com.example.android.aide;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;      //firebase auth object
    private FirebaseFirestore firebaseFirestore;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private ProgressDialog progressDialog;      //progress dialog
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_form);    //setting the login_form layout
        firebaseAuth = FirebaseAuth.getInstance();  //getting firebase auth object



        //initializing each variable with their corressponding id
        editTextEmail = (EditText) findViewById(R.id.user_name_login);
        editTextPassword = (EditText) findViewById(R.id.pwd_login);
        progressDialog = new ProgressDialog(this);



         Button log_but=(Button)findViewById(R.id.log_but);
         //Logging in the user
        log_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getting email and password of user
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
                                    //start the profile activity
                                    Toast.makeText(LoginActivity.this,"You are now logged in",Toast.LENGTH_LONG).show();
                                    Intent intent=new Intent(LoginActivity.this,CaretakerRegistrationActivity.class);
                                    startActivity(intent);
                                    finish();
                                   // startActivity(new Intent(getApplicationContext(), PatientRegistrationActivity.class));
                                }
                            }});

    }
});
    }
}
