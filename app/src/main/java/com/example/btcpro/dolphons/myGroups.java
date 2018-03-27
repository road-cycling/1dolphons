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

import java.util.ArrayList;

public class myGroups extends AppCompatActivity {

    ListView myGroupsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_groups);

        myGroupsListView = findViewById(R.id.myGroupsListView);

        final ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("Data");
        arrayList.add("Data1");
        arrayList.add("Data2");
        arrayList.add("Data3");
        arrayList.add("Data4");
        arrayList.add("Data5");


        /* Probably want to make it a recycler view */

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);

        myGroupsListView.setAdapter(arrayAdapter);
        myGroupsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("Tapped: ", arrayList.get(i));
            }
        });

    }

}
