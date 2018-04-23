package com.example.btcpro.dolphons;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.List;

public class myGroups extends AppCompatActivity {

    ArrayList<String> groupID;
    ListView myGroupsListView;
    private FirebaseFirestore FireStore;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_groups);

        user = FirebaseAuth.getInstance().getCurrentUser();
        FireStore = FirebaseFirestore.getInstance();
        myGroupsListView = findViewById(R.id.myGroupsListView);

        final ArrayList<String> arrayList = new ArrayList<String>();
        groupID = new ArrayList<String>();

        FireStore
                .collection("users")
                .document("wxaJHZnNNwfQkvOy3NQSi8CE3Da2") /* for testing would be users uid in real life */
                .collection("groupsApartOf")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> data = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot item : data) {
                            arrayList.add(item.get("groupName").toString());
                            groupID.add(item.get("groupID").toString());
                        }
                        attach(arrayList);
                        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                    }
                });



    }

    public void attach(final ArrayList arrayList) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);

        myGroupsListView.setAdapter(arrayAdapter);
        myGroupsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent().setClass(myGroups.this, viewGroup.class);
                intent.putExtra("groupID", groupID.get(i));

                startActivity(intent);
                /*Log.i("Tapped: ", arrayList.toString());
                Log.i("ID is: ", groupID.get(i)); //working perfectly for intents
                Log.i("Tapped (?): ", Integer.toString(i));*/
            }
        });
    }

}
