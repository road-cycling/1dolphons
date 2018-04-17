package com.example.btcpro.dolphons;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.*;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class Login extends AppCompatActivity {

    String email = "test@gmail.com";
    String password = "abadpassword";
    String name = "Nate Kamm";
    Boolean signIn = true;

    private FirebaseAuth mAuth;


    TextView nameView;
    TextView emailView;
    TextView passwordView;
    Button loginSignupButton;
    //private AuthUI mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        nameView = findViewById(R.id.nameView);
        emailView = findViewById(R.id.emailView);
        passwordView = findViewById(R.id.passwordView);
        loginSignupButton = findViewById(R.id.loginSignupButton);

    }


    /* one onclick function , manage with ID's  TO DO*/
    public void loginSignup(View v) {

    }

    public void signUp(View v) {
        if (signIn) {
            nameView.setVisibility(View.VISIBLE);
            loginSignupButton.setText("Sign Up");
            signIn = !signIn;
        }
    }

    public void login(View v) {
        createAccount(name, password);
        if (!signIn) {
            nameView.setVisibility(View.GONE);
            loginSignupButton.setText("Login");
            System.out.println("Click");
            signIn = !signIn;
        }
    }

    /* Thanks Google */

    private void createAccount(String email, String password) {
       // Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        //showProgressDialog();

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            //Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                           //         Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // [START_EXCLUDE]
                        //hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }


    private void signIn(String email, String password) {
        //Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        //showProgressDialog();

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                          //  updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "signInWithEmail:failure", task.getException());
                            //Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                            //        Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // [START_EXCLUDE]
                       // if (!task.isSuccessful()) {
                        //    mStatusTextView.setText(R.string.auth_failed);
                       // }
                       // hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }

    /* Need to add this */

    private boolean validateForm() {
        return true;
    }

};