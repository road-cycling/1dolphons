package com.example.btcpro.dolphons;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class viewEvent extends AppCompatActivity{

    private static final String TAG = "ViewEvent";

    private Intent intent;
    String eventRefID;
    String groupRefID;

    private FirebaseFirestore db;
    private FirebaseUser user;

    private boolean admin;
    private boolean eventFound;

    TextView nameTitle;
    TextView eventDescript;
    Button editEvent;
    Button deleteEvent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);

        intent = this.getIntent();
        groupRefID = intent.getStringExtra("groupID");
        eventRefID = intent.getStringExtra("eventID");

        nameTitle = findViewById(R.id.nameTitle);
        eventDescript = findViewById(R.id.eventDescript);
        editEvent = findViewById(R.id.editEvent);
        deleteEvent = findViewById(R.id.deleteEvent);

        editEvent.setVisibility(View.GONE);
        deleteEvent.setVisibility(View.GONE);

        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();

        eventFound = true;

        db.collection("groupss").document(groupRefID).collection("events").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> data = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot item : data) {
                            if (item.getId().equals(eventRefID)) {
                                eventFound = true;
                            } else {
                                eventFound = false;
                            }
                        }
                    }
                });

        if (eventFound) {
            editEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editEvent();
                }
            });

            deleteEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteEvent();
                }
            });

            db.collection("groupss").document(groupRefID).collection("events").document(eventRefID)
                    .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {

                            if (e != null) {

                            }
                            
                            if (documentSnapshot.getData() != null) {
                                    nameTitle.setText(documentSnapshot.getData().get("title").toString());
                                    eventDescript.setText(documentSnapshot.getData().get("summary").toString());
                            }
                        }

                    });


            db.collection("groupss").document(groupRefID)
                    .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                            if (e != null) {

                            }
                            String adminID = documentSnapshot.getData().get("owner_uid").toString();
                            if (adminID.equals(user.getUid())) {
                                admin = true;
                            } else {
                                admin = false;
                            }

                            if (admin) {
                                editEvent.setVisibility(View.VISIBLE);
                                deleteEvent.setVisibility(View.VISIBLE);
                            }

                        }
                    });
        }
    }

    private void openViewGroup() {
        Intent intent = new Intent().setClass(viewEvent.this, viewGroup.class);
        intent.putExtra("groupID", groupRefID);

        startActivity(intent);
    }

    private void deleteEvent() {
        db.collection("groupss").document(groupRefID).collection("events").document(eventRefID)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Event successfully deleted!");

                        openViewGroup();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Error deleting event!");

                        openViewGroup();
                    }
                });

    }

    private void editEvent() {
        /*Intent intent = new Intent().setClass(viewEvent.this, editEvent.class);
                intent.putExtra("groupID", groupRefID);
                intent.putExtra("eventID", eventRefID);

                startActivity(intent);*/
    }

}
