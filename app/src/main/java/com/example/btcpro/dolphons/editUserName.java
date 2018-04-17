package com.example.btcpro.dolphons;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class editUserName extends AppCompatActivity {

    private EditText editName;
    private Button userSave;
    private Button userCancel;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_name);

        editName = findViewById(R.id.edittextName);
        userSave = findViewById(R.id.buttonSave);
        userCancel = findViewById(R.id.buttonCancel);

        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            if(user.getDisplayName() != null){
                String name = user.getDisplayName();
                editName.setText(name);
            }
        }
        else{
            //User is not signed in, return to Login screen.
            openLogin();
        }

        userSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(editName.getText().toString()).build();
                user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(editUserName.this, "Username changed!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(editUserName.this, "Username could not be changed.", Toast.LENGTH_SHORT).show();
                    }
                });
                openEditUserProfile();
            }
        });
        userCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEditUserProfile();
            }
        });
    }
    private void openEditUserProfile(){
        Intent intent = new Intent(this, editUserProfile.class);
        startActivity(intent);
    }
    private void openLogin(){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }
}
