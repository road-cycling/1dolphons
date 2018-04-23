package com.example.btcpro.dolphons;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
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
    Calendar myCalendar;
    EditText edittext;

    int Calday, Calmonth, Calyear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_create_event);

        groupID   = getIntent().getExtras().getString("groupID");
        FireStore = FirebaseFirestore.getInstance();
        Summary   = findViewById(R.id.Summary);
        Location  = findViewById(R.id.location);

        myCalendar = Calendar.getInstance();

        EditText edittext= findViewById(R.id.Birthday);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calday = dayOfMonth;
                Calmonth = monthOfYear + 1;
                Calyear = year;
            }

        };

        edittext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(admin_createEvent.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    public void createEvent(View v) {
        //we create event here
        String summary = Summary.getText().toString();
        String location = Location.getText().toString();

        String date = Integer.toString(Calmonth) + "/" + Integer.toString(Calday) + "/" + Integer.toString(Calyear);

        Map<String, String> userMap = new HashMap<>();

        userMap.put("location", location);
        userMap.put("summary", summary);
        userMap.put("date", date);

        addUserReference(userMap);
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
                        navigate();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //failll
            }
        });


    }

    private void updateLabel() {
        String myFormat = "EEE, MMM d, ''yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        System.out.println("called");
        edittext.setText(sdf.format(myCalendar.getTime()));
    }

    public void navigate() {
        Intent intent = new Intent(this, welcome.class);
        startActivity(intent);
    }


}

// i have no clue if this works. 
