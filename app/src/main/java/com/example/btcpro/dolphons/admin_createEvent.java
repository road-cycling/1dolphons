package com.example.btcpro.dolphons;

import android.app.DatePickerDialog;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class admin_createEvent extends AppCompatActivity {

    private FirebaseFirestore FireStore;
    EditText Location;
    EditText Summary;
    String groupID;
    DatePicker date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_create_event);

        groupID   = getIntent().getExtras().getString("groupID");
        FireStore = FirebaseFirestore.getInstance();
        Summary   = findViewById(R.id.Summary);
        Location  = findViewById(R.id.location);
        date      = findViewById(R.id.datePicker);

    }

    public void createEvent(View v) {
        //we create event here
        String summary = Summary.getText().toString();
        String location = Location.getText().toString();

        int day = date.getDayOfMonth();
        int month = date.getMonth() + 1;
        int year = date.getYear();

        Map<String, String> userMap = new HashMap<>();

        userMap.put("location", location);
        userMap.put("summary", summary);
        userMap.put("date", Integer.toString(day) /* needs to be changed */);
    }

    private void addUserReference(final Map userMap) {


        FireStore
                .collection("groupss") /* referencing better data */
                .document(groupID) /*correct*/
                .collection("events")
                .add(userMap)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        /* success*/
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //failll
            }
        });
    }
}

// i have no clue if this works. 
