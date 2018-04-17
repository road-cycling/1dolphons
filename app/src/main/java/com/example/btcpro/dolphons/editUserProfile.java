package com.example.btcpro.dolphons;

import android.content.DialogInterface;
import android.content.Intent;
//import android.database.Cursor;
//import android.graphics.BitmapFactory;
import android.net.Uri;
//import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.EditText;
import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.TextView;
import android.view.View;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthCredential;
//import com.google.firebase.auth.EmailAuthCredential;
//import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
//import java.util.HashMap;
//import java.util.Map;

public class editUserProfile extends AppCompatActivity {

    private ImageButton profilePicture;
    private Button editName;
    private Button changePassword;
    private Button deleteAccount;
    private Button returnToWelcome;
    private FirebaseFirestore FireStore;
    private FirebaseUser user;

    private static int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);

        profilePicture = findViewById(R.id.imagebuttonProfilePicture);
        editName = findViewById(R.id.buttonEditName);
        changePassword = findViewById(R.id.buttonChangePassword);
        deleteAccount = findViewById(R.id.buttonDeleteAccount);
        returnToWelcome = findViewById(R.id.buttonReturn);

        user = FirebaseAuth.getInstance().getCurrentUser();
        FireStore = FirebaseFirestore.getInstance();
        if(user != null){
            if(user.getDisplayName() != null){
                String name = user.getDisplayName();
                //String message = "Hello, " + name;
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

        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
        editName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEditUsername();
            }
        });
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openChangePassword();
            }
        });
        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteUserAccount();
            }
        });
        returnToWelcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWelcome();
            }
        });
    }
    private void openWelcome(){
        Intent intent = new Intent(this, welcome.class);
        startActivity(intent);
    }
    private void openEditUsername(){
        Intent intent = new Intent(this, editUserName.class);
        startActivity(intent);
    }
    private void openChangePassword(){
        Intent intent = new Intent(this, changeUserPassword.class);
        startActivity(intent);
    }
    private void deleteUserAccount(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.ic_dialog_alert).setTitle("Delete Account?").setMessage("You won't be able to undo this.").setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(editUserProfile.this, "User successfully deleted.", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(editUserProfile.this, "User could not be deleted- try re-signing in.", Toast.LENGTH_SHORT).show();
                    }
                });
                FirebaseAuth.getInstance().signOut();
                openLogin();
            }
        }).setNegativeButton("Don't Delete", null).show();
    }
    private void openLogin(){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            user = FirebaseAuth.getInstance().getCurrentUser();
            Uri selectedImage = data.getData();
            //String[] filePathColumn = { MediaStore.Images.Media.DATA };

            //Cursor cursor = getContentResolver().query(selectedImage,
                    //filePathColumn, null, null, null);
            //cursor.moveToFirst();

            //int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            //String picturePath = cursor.getString(columnIndex);
            //cursor.close();

            //ImageView imageView = findViewById(R.id.imagebuttonProfilePicture);
            //imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setPhotoUri(selectedImage).build();
            user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(editUserProfile.this, "Successfully changed picture!", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(editUserProfile.this, "Could not change picture.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
