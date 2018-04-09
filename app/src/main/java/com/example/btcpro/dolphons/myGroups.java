package com.example.btcpro.dolphons;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;


import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

public class myGroups extends AppCompatActivity {

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
        arrayList.add("Data");
        arrayList.add("Data1");
        arrayList.add("Data2");
        arrayList.add("Data3");
        arrayList.add("Data4");
        arrayList.add("Data5");


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
                            System.out.println("This item is");
                            if (item.getData().get("groupName") != null) {
                                arrayList.add(item.getData().get("groupName").toString());
                                //might be error in data stored. This shouldnt be needed
                            }
                            //saveData(item.getData());
                            //Map<String, String> data =
                            System.out.println(item.getData().get("groupName"));
                            System.out.println(item.getData().keySet());
                            //item.getData
                            //JSONObject parse = new JSONObject(item.getData());
                            //System.out.println(parse.getString("groupName"));
                        }

                        attach(arrayList);
                    }
                });


        for (String item: arrayList) {
            System.out.println(item);
        }


    }

    public void attach(final ArrayList arrayList) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);

        myGroupsListView.setAdapter(arrayAdapter);
        myGroupsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Log.i("Tapped: ", arrayList.get(i));
            }
        });
    }


}
