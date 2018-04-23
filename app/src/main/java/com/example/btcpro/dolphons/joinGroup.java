package com.example.btcpro.dolphons;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import android.widget.ListView;
import android.widget.ProgressBar;

import android.view.View;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import java.util.List;

import java.util.Timer;
import java.util.TimerTask;

public class joinGroup extends AppCompatActivity {

    ListView joinGroupListView;
    ProgressBar pBar;
    EditText inputText;
    FirebaseFirestore firebase;
    ArrayList<String> arrayList;
    ArrayList<String> groupID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_group);

        firebase = FirebaseFirestore.getInstance();

        arrayList = new ArrayList<String>();
        groupID = new ArrayList<String>();

        joinGroupListView = findViewById(R.id.joinGroupListView);
        inputText = findViewById(R.id.joinGroupEditText);
        pBar = findViewById(R.id.loadingPanelJoinGroup);
        pBar.setVisibility(View.INVISIBLE);

        /* Makeshift Debounce */
        inputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {}

            private Timer timer=new Timer();
            private final long DELAY = 1000; // milliseconds

            @Override
            public void onTextChanged(final CharSequence charSequence, int i, int i1, int i2) {
                timer.cancel();
                timer = new Timer();
                timer.schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                //pBar.setVisibility(View.VISIBLE);
                                queryFirebase(charSequence.toString());
                                //pBar.setVisibility(View.INVISIBLE);
                            }
                        },
                        DELAY
                );
            }


        });
    }

    public void queryFirebase(String toSearch) {
      firebase
                .collection("groupss")
                .whereEqualTo("groupName", toSearch)
                //.whereEqualTo("isPublic", 1)
                .limit(40)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        System.out.println("Success");
                        System.out.println(queryDocumentSnapshots.getDocuments());
                        List<DocumentSnapshot> data = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot item : data) {
                            System.out.println(item.getId());
                            System.out.println(item.getData());
                            arrayList.add(item.get("groupName").toString());
                            groupID.add(item.getId());
                        }

                        attach();
                    }
                });
    }

    public void attach() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        joinGroupListView.setAdapter(arrayAdapter);
        joinGroupListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("Tapped: ", arrayList.toString());
                Log.i("ID is: ", groupID.get(i)); //working perfectly for intents
                Log.i("Tapped (?): ", Integer.toString(i));
            }
        });

    }
}
