package com.example.android.aide;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


public class PatientRegistrationActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_registration);
        ViewPager viewPager=(ViewPager)findViewById(R.id.viewpager_patient);
        PatientCategoryAdapter adapter=new PatientCategoryAdapter(this,getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs_patient);
        tabLayout.setupWithViewPager(viewPager);
        //Toolbar toolbar=findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.settings_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                firebaseAuth=FirebaseAuth.getInstance();
                firebaseAuth.signOut();
                finish();
                Toast.makeText(PatientRegistrationActivity.this,"You are now logged out",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(PatientRegistrationActivity.this,RegistrationActivity.class);
                startActivity(intent);
                return true;
            case R.id.manageDevices:
                Intent intent1=new Intent(PatientRegistrationActivity.this,EditDevicesActivity.class);
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
    }}
}
