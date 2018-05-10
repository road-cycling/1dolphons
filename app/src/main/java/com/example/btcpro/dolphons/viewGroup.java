package com.example.btcpro.dolphons;

import android.content.Intent;
import android.net.Uri;
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
import android.widget.ImageView;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class viewGroup extends AppCompatActivity{

    private static final String TAG = "ViewGroup";

    private Intent intent;
    String groupRefID;

    private FirebaseFirestore db;
    private FirebaseUser user;

    private boolean admin;


    TextView nameTitle;
    TextView groupDescript;
    TextView home;
    Button admin_panel;
    Button join;
    ListView eventsListView;
    ArrayList<String> eventID;
    ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_group);

        intent = this.getIntent();
        groupRefID = intent.getStringExtra("groupID");

        nameTitle = findViewById(R.id.nameTitle);
        groupDescript = findViewById(R.id.groupDescript);
        home = findViewById(R.id.home);
        admin_panel = findViewById(R.id.admin);
        join = findViewById(R.id.joinGroup);
        eventsListView = findViewById(R.id.eventListView);
        image = findViewById(R.id.imgviewProfilePicture);

        final ArrayList<String> arrayList = new ArrayList<String>();
        eventID = new ArrayList<>();


        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();

        db.collection("groupss").document(groupRefID).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                if (e !=null)
                {

                }
                nameTitle.setText(documentSnapshot.getData().get("groupName").toString());
                groupDescript.setText(documentSnapshot.getData().get("groupDesc").toString());
                if(documentSnapshot.getData().get("photoURL") != null){


                    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    Uri photoUrl = Uri.parse(documentSnapshot.getData().get("photoURL").toString());
                    System.out.println("URL!!!!!!!!!!!!!!!!");
                    System.out.println(photoUrl);
                    intent.setType(photoUrl.toString());
                    //image.setImageURI(Uri.parse(intent.toString()));
                    Picasso.with(viewGroup.this).load(photoUrl).into(image);

                } else {
                    System.out.println("IT IS NULL");
                }
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent().setClass(viewGroup.this, welcome.class);

                startActivity(intent);
            }
        });

        admin_panel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent().setClass(viewGroup.this, adminPanel.class);
                intent.putExtra("groupID", groupRefID);

                startActivity(intent);
            }
        });

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> userMap = new HashMap<>();
                userMap.put("admin", "false");
                userMap.put("userID", user.getUid());
                userMap.put("user_name", user.getDisplayName());


                db
                        .collection("groupss")
                        .document(groupRefID)
                        .collection("users")
                        .add(userMap)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                //it works!

                                Map<String, String> userMap = new HashMap<>();
                                userMap.put("groupID", groupRefID);
                                userMap.put("groupName", nameTitle.getText().toString());


                                db
                                        .collection("users")
                                        .document(user.getUid())
                                        .collection("groupsApartOf")
                                        .add(userMap)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Toast.makeText(viewGroup.this, "Joined Group!", Toast.LENGTH_LONG).show();

                                                Intent intent = new Intent().setClass(viewGroup.this, viewGroup.class);
                                                intent.putExtra("groupID", groupRefID);

                                                startActivity(intent);
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        //failll
                                    }
                                });

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        /* shake? */
                    }
                });


            }
        });


        db.collection("groupss").document(groupRefID)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                if (e !=null)
                {

                }
                String adminID = documentSnapshot.getData().get("owner_uid").toString();
                if (adminID.equals(user.getUid())) {
                    admin = true;
                } else {
                    admin = false;
                }

                if (admin) {
                    admin_panel.setVisibility(View.VISIBLE);
                    join.setVisibility(View.GONE);
                }

            }
        });

        db.collection("groupss").document(groupRefID).collection("users").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> data = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot item : data) {
                            if (user.getUid().equals((item.get("userID").toString())))
                                join.setVisibility(View.GONE);
                        }
                    }
                });



        db.collection("groupss").document(groupRefID).collection("events").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    List<DocumentSnapshot> data = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot item : data) {

                        if (item.get("title") != null)
                            arrayList.add(item.get("title").toString());
                        else
                            arrayList.add("Placeholder");
                        eventID.add(item.getId());

                    }

                    attach(arrayList);

                    findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                }
            });

    }

    public void attach(final ArrayList arrayList) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, arrayList);

        eventsListView.setAdapter(arrayAdapter);
        eventsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent().setClass(viewGroup.this, viewEvent.class);
                intent.putExtra("groupID", groupRefID);
                intent.putExtra("eventID", eventID.get(i));
                System.out.println("EVENT CLICKED!");
                System.out.println(eventID.get(i));
                startActivity(intent);
                //findViewById(R.id.loadingPanel).setVisibility(View.GONE);

            }
        });
    }
}
