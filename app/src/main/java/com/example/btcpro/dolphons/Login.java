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
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Login extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("message");
    //myRef.setValue("Hello, World!");
    private static final String TAG = "Login";

    String email;
    String password;
    String name;
    Boolean signIn = true;
    private FirebaseAuth mAuth;


    TextView nameView;
    TextView emailView;
    TextView passwordView;
    TextView headerView;
    Button loginSignupButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        nameView = findViewById(R.id.nameView);
        emailView = findViewById(R.id.emailView);
        passwordView = findViewById(R.id.passwordView);
        headerView = findViewById(R.id.header);
        loginSignupButton = findViewById(R.id.loginSignupButton);

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

    }

    /* one onclick function , manage with ID's  TO DO*/
    public void loginSignup(View v) {
        email = emailView.getText().toString();
        password = passwordView.getText().toString();
        name = nameView.getText().toString();

        if (loginSignupButton.getText() == "Sign Up") {
            createAccount(email, password, name);
        } else {
            signIn(email, password);
        }
    }

    public void signUp(View v) {
        if (signIn) {
            nameView.setVisibility(View.VISIBLE);
            loginSignupButton.setText("Sign Up");
            headerView.setText("Sign Up");
            signIn = !signIn;
        }
    }

    public void login(View v) {
        //createAccount(name, password);
        if (!signIn) {
            nameView.setVisibility(View.GONE);
            loginSignupButton.setText("Login");
            headerView.setText("Login");
            signIn = !signIn;
        }
    }


    /* Thanks Google */

    private void createAccount(String email, String password, String name) {
        final String userName = name;
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();

                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(userName)
                                .build();

                        user.updateProfile(profileUpdates)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Log.d(TAG, "user profile updated.");
                                        }
                                    }
                                });

                        updateUI(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(Login.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }

                    // ...
                }
            });
    }



    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(Login.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }

                    // ...
                }
            });
    }

    public void updateUI(FirebaseUser user) {
        if (user != null) {
            System.out.println("Successful");
            headerView.setText("Login Successful");
            System.out.println(user.getDisplayName());
            startActivity(new Intent(Login.this, welcome.class));
        } else {
            System.out.println("Unsuccessful");
            headerView.setText("Email or Password incorrect, please try again");
        }

    }


    /* Need to add this */

    private boolean validateForm() {
        return true;
    }

}