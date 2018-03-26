package com.example.btcpro.dolphons;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.*;



public class Login extends AppCompatActivity {

    String email = "test@gmail.com";
    String password = "abadpassword";
    String name = "Nate Kamm";
    //private AuthUI mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Firebase.setAndroidContext(this);
    }


    public void showGreetings(View view) {
        //String button_text;
        //button_text = ((Button) view).getText().toString();


        /*
        if (button_text.equals("Sign In")) {
            Intent intent = new Intent(this, Login_SignIn.class);
            startActivity(intent);
        }
        else if (button_text.equals("Sign Up")) {
            Intent intent = new Intent(this, Login_SignUp.class);
            startActivity(intent);
        }
        */
    }
};