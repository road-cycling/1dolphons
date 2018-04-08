package com.example.btcpro.dolphons;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class welcome extends AppCompatActivity {

    private Button createGroup;
    private Button myGroups;
    private Button joinGroup;
    private Button editProfile;
    private Button signOut;
    private ImageView profilePicture;
    private TextView userName;

    private FirebaseFirestore FireStore;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        createGroup = findViewById(R.id.buttonCreateGroup);
        myGroups = findViewById(R.id.buttonMyGroups);
        joinGroup = findViewById(R.id.buttonJoinGroup);
        editProfile = findViewById(R.id.buttonEditProfile);
        signOut = findViewById(R.id.buttonSignOut);
        profilePicture = findViewById(R.id.imgviewProfilePicture);
        userName = findViewById(R.id.textviewName);

        user = FirebaseAuth.getInstance().getCurrentUser();
        FireStore = FirebaseFirestore.getInstance();
        if(user != null){
            //User is signed in, we may continue.
            if(user.getDisplayName() != null){
                String name = user.getDisplayName();
                String message = "Hello, " + name;
                userName.setText(message);
            }
            if(user.getPhotoUrl() != null){
                Uri photoUrl = user.getPhotoUrl();
                profilePicture.setImageURI(photoUrl);
            }
        }
        else{
            //User is not signed in, return to Login screen.
            openLogin();
        }

        createGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCreateGroup();
            }
        });
        myGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMyGroups();
            }
        });
        joinGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openJoinGroup();
            }
        });
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEditProfile();
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUserOut();
            }
        });
    }
    private void openCreateGroup(){
        Intent intent = new Intent(this, createGroup.class);
        startActivity(intent);
    }
    private void openMyGroups(){
        Intent intent = new Intent(this, myGroups.class);
        startActivity(intent);
    }
    private void openJoinGroup(){
        Intent intent = new Intent(this, joinGroup.class);
        startActivity(intent);
    }
    private void openEditProfile(){
        Intent intent = new Intent(this, editUserProfile.class);
        startActivity(intent);
    }
    private void openLogin(){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }
    private void signUserOut(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.ic_dialog_alert).setTitle("Sign out?").setMessage("This will return you to the login screen.").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseAuth.getInstance().signOut();
                openLogin();
            }
        }).setNegativeButton("No", null).show();
    }
}







/*package com.example.btcpro.dolphons;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class welcome extends AppCompatActivity {

    TextView textView;
    ImageView imageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();
            String uid = user.getUid();
            textView = findViewById(R.id.textView);
            String message = "Hello " + name;
            textView.setText(message);
        }
    }

    public void createGroup(View v) {
        startActivity(new Intent(welcome.this, createGroup.class));
    }

    public void logout(View v) {
        System.out.println("LOG OUT!!!");
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(welcome.this, Login.class));
    }

}
*/