package com.example.btcpro.dolphons;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class adminPanel extends AppCompatActivity {

    ListView myGroupsListView;
    String intentData;
    Button back_button;


    /// we need to pass in group id here.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_admin_panel);
        back_button = findViewById(R.id.back);

        intentData = getIntent().getExtras().getString("groupID");
        //this is the group data we are referencing, we need to pass it to the next thing
        final ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("View Users");
        arrayList.add("Create Event");
        arrayList.add("Edit Group");

        myGroupsListView = findViewById(R.id.myGroupsListView);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(adminPanel.this, viewGroup.class);
                intent.putExtra("groupID", intentData);
                startActivity(intent);
            }
        });

        attach(arrayList);



    }

    public void attach(final ArrayList arrayList) {
        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);

        myGroupsListView.setAdapter(arrayAdapter);
        myGroupsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("Tapped: ", arrayList.toString());
                Log.i("Tapped (?): ", Integer.toString(i));
                onIndex(i);
            }
        });
    }

    public void onIndex(int idx) {
        if (idx == 0) {
            // we know the user clicked on view users
            Intent intent = new Intent(this, admin_viewuser.class);
            intent.putExtra("groupID",intentData);
            startActivity(intent);
        } else if (idx == 1) {
            // we know the user clicked on Create Event
            Intent intent = new Intent(this, admin_createEvent.class);
            intent.putExtra("groupID",intentData);
            startActivity(intent);
        } else if (idx == 2) {
            // we know the user clicked on Edit Group
            Intent intent = new Intent(this, editGroup.class);
            intent.putExtra("groupID",intentData);
            startActivity(intent);
        }
    }
}
