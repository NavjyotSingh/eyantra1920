package com.example.android.aide;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class PatientProfileFragment extends Fragment{
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
   String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    DocumentReference dRef = firebaseFirestore.collection("Users").document("Users");
    //final ArrayList<Users> usersList=new ArrayList<Users>();
   // final UsersAdapter usersAdapter=new UsersAdapter(getContext(),usersList);
    private static final String NAME="name";
    private static final String USERNAME="username";
    private static final String CONTACT="contact";
    private static final String USER_ROLE="user_role";
    private TextView textViewData;




    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        final View rootView=inflater.inflate(R.layout.user_profile,container,false);
        textViewData=(TextView)rootView.findViewById(R.id.textViewData);
        dRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String name=documentSnapshot.getString(NAME);
                            String user_name=documentSnapshot.getString(USERNAME);
                            String contact=documentSnapshot.getString(CONTACT);
                            String user_role=documentSnapshot.getString(USER_ROLE);

                            //Map<String, Object> note = documentSnapshot.getData();

                            textViewData.setText("Name: " + name + "\n" + "Username: " + user_name + "\n" + "User Role: " + user_role + "\n" + "Contact: " + contact);
                        } else {
                            Toast.makeText(getContext(), "Document does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure( Exception e) {
                        Toast.makeText(getContext(), "Error!", Toast.LENGTH_SHORT).show();
                        Log.d("PatientProfileFragment", e.toString());
                    }
                });

      /* dRef.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete( Task<QuerySnapshot> task) {
                final ArrayList<Users> usersList=new ArrayList<Users>();
                if(task.isSuccessful()){
                    for(DocumentSnapshot document:task.getResult()){
                        Users user=document.toObject(Users.class);
                        usersList.add(user);
                    }
                    ListView mUsersListView=(ListView)rootView.findViewById(R.id.list);
                    UsersAdapter usersAdapter=new UsersAdapter(this,usersList);
                    mUsersListView.setAdapter(usersAdapter);
                }
                else
                    Log.d("PatientProfileFragment","Error getting documenta",task.getException());
            }
        });*/

        return rootView;
    }
    @Override
    public void onStart() {
        super.onStart();
        dRef.addSnapshotListener(getActivity(), new EventListener<DocumentSnapshot>() {

            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                if(e!=null){
                    Toast.makeText(getContext(),"Error while Loading",Toast.LENGTH_SHORT).show();
                    Log.d("PatientProfileFragment",e.toString());return;
                }
                if(documentSnapshot.exists()){
                    String name=documentSnapshot.getString(NAME);
                    String user_name=documentSnapshot.getString(USERNAME);
                    String contact=documentSnapshot.getString(CONTACT);
                    String user_role=documentSnapshot.getString(USER_ROLE);
                    textViewData.setText("Name: " + name + "\n" + "Username: " + user_name + "\n" + "User Role: " + user_role + "\n" + "Contact: " + contact);
                }
            }
        });
    }
}
