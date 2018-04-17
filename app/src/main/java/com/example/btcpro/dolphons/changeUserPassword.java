package com.example.btcpro.dolphons;

//import android.content.DialogInterface;
import android.content.Intent;
//import android.database.Cursor;
//import android.graphics.BitmapFactory;
//import android.net.Uri;
//import android.provider.MediaStore;
import android.support.annotation.NonNull;
//import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
//import android.widget.CheckBox;
import android.widget.EditText;
//import android.widget.ImageButton;
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
//import com.google.firebase.auth.UserProfileChangeRequest;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.FirebaseFirestore;
//import java.util.HashMap;
//import java.util.Map;

public class changeUserPassword extends AppCompatActivity {

    private EditText userPassword;
    private EditText userPassword2;
    private Button userSave;
    private Button userCancel;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user_password);

        userPassword = findViewById(R.id.edittextPassword);
        userPassword2 = findViewById(R.id.edittextPassword2);
        userSave = findViewById(R.id.buttonSave);
        userCancel = findViewById(R.id.buttonCancel);

        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            //User is not signed in, return to Login screen.
            openLogin();
        }

        userSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeUserPassword(userPassword.getText().toString(), userPassword2.getText().toString());
            }
        });
        userCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEditUserProfile();
            }
        });
    }
    private void changeUserPassword(final String pass1, String pass2){
        if(pass1.equals(pass2)){
            String newPassword = pass1;
            user = FirebaseAuth.getInstance().getCurrentUser();
            user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(changeUserPassword.this, "Password updated!", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(changeUserPassword.this, "Password could not be updated- try re-signing in.", Toast.LENGTH_SHORT).show();
                }
            });
            openEditUserProfile();
        }
        else{
            Toast.makeText(this, "Entered Passwords are not the same!", Toast.LENGTH_SHORT).show();
        }
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
