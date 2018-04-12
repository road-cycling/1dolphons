package com.example.btcpro.dolphons;

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
