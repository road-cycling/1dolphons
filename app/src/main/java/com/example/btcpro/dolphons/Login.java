package com.example.btcpro.dolphons;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }


    public void showGreetings(View view) {
        String button_text;
        button_text = ((Button) view).getText().toString();

        if (button_text.equals("Sign In")) {
            Intent intent = new Intent(this, Login_SignIn.class);
            startActivity(intent);
        }
        else if (button_text.equals("Sign Up")) {
            Intent intent = new Intent(this, Login_SignUp.class);
            startActivity(intent);
        }
    }
};