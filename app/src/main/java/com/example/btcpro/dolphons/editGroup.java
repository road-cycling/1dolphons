package com.example.btcpro.dolphons;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;


public class editGroup extends AppCompatActivity
{
    private Button submit;
    private CheckBox privateGroup;
    //private ImageButton groupPicture;
    private EditText groupName;
    private EditText groupDesc;
    private FirebaseUser user;

    private Intent intent;
    String groupRefID;


    private FirebaseFirestore FireStore;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group);

        intent = this.getIntent();
        groupRefID = intent.getStringExtra("groupID");

        FireStore = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        groupName = (EditText) findViewById(R.id.groupNameTextEnter);
        groupDesc = (EditText) findViewById(R.id.enterGroupDesc);
        privateGroup = (CheckBox) findViewById(R.id.checkboxPrivate);
        submit = (Button) findViewById(R.id.buttonSubmit);
        System.out.println("#1");
        System.out.println(groupRefID);

        FireStore.collection("groupss").document(groupRefID).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                if (e !=null)
                {

                }
                groupName.setText(documentSnapshot.getData().get("groupName").toString());
                groupDesc.setText(documentSnapshot.getData().get("groupDesc").toString());
            }
        });

        submit.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view)
            {

                System.out.println("#2");
                System.out.println(groupRefID);
                //final String groupID = intent.getStringExtra("groupID");

                String name = groupName.getText().toString();
                String desc = groupDesc.getText().toString();
                //
                boolean privateCheck = privateGroup.isChecked();

                Map<String, String> userMap = new HashMap<>();
                System.out.println(user.getUid());
                System.out.println(FireStore);
                userMap.put("groupName", name);
                userMap.put("groupDesc", desc);
                userMap.put("owner_uid", user.getUid());

                FireStore.collection("groupss").document(groupRefID)
                    .set(userMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            System.out.println("#3");
                            System.out.println(groupRefID);
                            //final String groupID = intent.getStringExtra("groupID");
                            Intent intent = new Intent().setClass(editGroup.this, viewGroup.class);
                            intent.putExtra("groupID", groupRefID);

                            startActivity(intent);
                        }
                    })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                System.out.println("FAILED TO UPDATE GROUP");
                            }
                        });
            }
        });

       /* submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                FireStore.collection("groupss").document(groupRefID).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                        if (e !=null)
                        {

                        }
                        groupName.setText(documentSnapshot.getData().get("groupName").toString());
                        groupDesc.setText(documentSnapshot.getData().get("groupDesc").toString());
                    }
                });

            }
        });*/
    }

    /*private void openNextActivity(final String groupRefID){
        Intent intent = new Intent().setClass(editGroup.this, viewGroup.class);
        intent.putExtra("groupID", groupRefID);

        startActivity(intent);
    }

    private void addGroupToFireStore(final String name, String desc, boolean privateCheck) {
        Map<String, String> userMap = new HashMap<>();
        System.out.println(user.getUid());
        System.out.println(FireStore);
        userMap.put("groupName", name);
        userMap.put("groupDesc", desc);
        userMap.put("owner_uid", user.getUid());

        FireStore.collection("groupss").add(userMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                System.out.println("before ID");
                System.out.println(documentReference.getId());
                addUserReference(documentReference.getId(), name);
                //Toast.makeText(createGroup.this, "Successful Group Creation", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                String error = e.getMessage();

                Toast.makeText(editGroup.this, "Error: " + error, Toast.LENGTH_SHORT).show();
            }
        });
        //openNextActivity();
    }

    private void addUserReference(final String refID, String groupName) {

        Map<String, String> userMap = new HashMap<>();
        userMap.put("groupID", refID);
        userMap.put("groupName", groupName);

        FireStore
                .collection("users")
                .document(user.getUid())
                .collection("groupsApartOf")
                .add(userMap)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        addGroupUserReference(documentReference.getId(), refID);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //failll
            }
        });
    }

    private void addGroupUserReference(String userRefID, final String groupRefID) {
        Map<String, String> userMap = new HashMap<>();
        userMap.put("userID", user.getUid());
        userMap.put("deleteID", userRefID);
        userMap.put("owner", *//*boolean :( *//* "true"); //I think we need this
        userMap.put("admin", *//*boolean :( *//* "true");
        //we need to add name of user

        FireStore
                .collection("groupss")
                .document(groupRefID)
                .collection("users")
                .add(userMap)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        //it works!
                        System.out.println("OWNER INFO SET");
                        openNextActivity(groupRefID);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                *//* shake? *//*
            }
        });
    }*/
}

