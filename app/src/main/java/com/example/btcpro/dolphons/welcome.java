package com.example.btcpro.dolphons;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
//import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.EditText;
//import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
//import android.widget.Toast;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.firestore.DocumentReference;
//import java.util.HashMap;
//import java.util.Map;
import com.google.firebase.firestore.FirebaseFirestore;

public class welcome extends AppCompatActivity {


    private ImageView profilePicture;
    private TextView userName;
    private TextView userEmail;

    private FirebaseFirestore FireStore;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

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
            /* works but takes too long to load (huge file) */
           // if(user.getPhotoUrl() != null){
           //     Uri photoUrl = user.getPhotoUrl();
           //     profilePicture.setImageURI(photoUrl);
           //     System.out.println(photoUrl + "url");
           // } else {
           //     System.out.println("IT IS NULL");
           // }
        }
        else{
            //User is not signed in, return to Login screen.
            openLogin();
        }

    }
    public void openCreateGroup(View v){
        Intent intent = new Intent(this, createGroup.class);
        startActivity(intent);
    }
    public void openMyGroups(View v){
        Intent intent = new Intent(this, myGroups.class);
        startActivity(intent);
    }
    public void openJoinGroup(View v){
        Intent intent = new Intent(this, joinGroup.class);
        startActivity(intent);
    }
    public void openEditProfile(View v){
        Intent intent = new Intent(this, editUserProfile.class);
        startActivity(intent);
    }
    public void openLogin(){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }
    public void signUserOut(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.ic_dialog_alert).setTitle("Sign out?").setMessage("This will return you to the login screen.").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseAuth.getInstance().signOut();
                openLogin();
            }
        }).setNegativeButton("No", null).show();
    }

    public void openAdminPanel(View v) {
        Intent intent = new Intent(this, adminPanel.class);
        intent.putExtra("groupID", "mNYJcsJcxWefOEJwdfTP"); //dummy but real data
        startActivity(intent);
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