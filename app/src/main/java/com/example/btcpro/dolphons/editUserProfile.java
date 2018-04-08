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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class editUserProfile extends AppCompatActivity {

    private ImageButton profilePicture;
    private Button editName;
    private Button changeEmail;
    private Button changePassword;
    private Button deleteAccount;
    private Button returnToWelcome;
    private FirebaseUser user;

    private static int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);

        profilePicture = findViewById(R.id.imagebuttonProfilePicture);
        editName = findViewById(R.id.buttonEditName);
        changeEmail = findViewById(R.id.buttonChangeEmail);
        changePassword = findViewById(R.id.buttonChangePassword);
        deleteAccount = findViewById(R.id.buttonDeleteAccount);
        returnToWelcome = findViewById(R.id.buttonReturn);

        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            if(user.getPhotoUrl() != null){
                Uri photoUrl = user.getPhotoUrl();
                profilePicture.setImageURI(photoUrl);
            }
        }

        /*profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);


            }
        });*/
        editName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Swap pages to Change Name Page
            }
        });
        changeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Swap pages to Change Email Page
            }
        });
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Swap pages to Change Password Page
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
    private void deleteUserAccount(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.ic_dialog_alert).setTitle("Delete Account?").setMessage("You won't be able to undo this.").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Needs re-authentication method.
                user.delete();
                openLogin();
            }
        }).setNegativeButton("No", null).show();
    }
    private void openLogin(){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }
    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            ImageView imageView = findViewById(R.id.imagebuttonProfilePicture);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }
    }*/
}
