package com.example.btcpro.dolphons;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class createGroup extends AppCompatActivity
{
    private Button createGroup;
    private CheckBox privateGroup;
    //private ImageButton groupPicture;
    private EditText groupName;
    private EditText groupDesc;

    private FirebaseFirestore FireStore;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        FireStore = FirebaseFirestore.getInstance();

        groupName = (EditText) findViewById(R.id.groupNameTextEnter);
        groupDesc = (EditText) findViewById(R.id.enterGroupDesc);
        privateGroup = (CheckBox) findViewById(R.id.checkboxPrivate);
        createGroup = (Button) findViewById(R.id.buttonCreateGroup);

        createGroup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //What to do after create button is pressed
                String name = groupName.getText().toString();
                String desc = groupDesc.getText().toString();
                //
                boolean privateCheck = privateGroup.isChecked();

                Map<String, String> userMap = new HashMap<>();

                userMap.put("groupName", name);
                userMap.put("groupDescp", desc);

                FireStore.collection("Groups").add(userMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference)
                    {
                        Toast.makeText(createGroup.this, "Succesful Group Creation", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                       String error = e.getMessage();

                       Toast.makeText(createGroup.this, "Error: " + error, Toast.LENGTH_SHORT).show();
                    }
                });
                openNextActivity();

            }
        });
    }
    private void openNextActivity(){
        Intent intent = new Intent(this, welcome.class);
        startActivity(intent);
    }
}

/*public class createGroup extends AppCompatActivity {

    private Button createGroup;
    private CheckBox privateGroup;
    private ImageButton groupPicture;
    private TextView groupName;
    private TextView groupDescription;

    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        privateGroup = findViewById(R.id.checkboxPrivate);
        groupName = findViewById(R.id.textviewGroupName);
        groupDescription = findViewById(R.id.textviewGroupDescription);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        /*mAuthListener = (FirebaseAuth){
                FirebaseUser user = FirebaseAuth.getCurrentUser();
                if(user != null){
                    Log.d(TAG, "onAuthStateChanged:signed_in" + user.getUid());
                    toastMessage
                }
        }

        createGroup = (Button)findViewById(R.id.buttonCreateGroup);
        createGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Write a message to the database
                /*FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("groupName");
                myRef.setValue(groupName);
                DatabaseReference myRef2 = database.getReference("groupDescription");
                myRef2.setValue(groupDescription);

                //Go to next window.
                openNextActivity();
            }
        });


    }

    private void openNextActivity(){
        Intent intent = new Intent(this, welcome.class);
        startActivity(intent);
    }



         //Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });



    //configureNextButton();


    //nameText = (TextView)findViewById(R.id.name_text);
    //nameButton = (Button)findViewById(R.id.name_button);

        /*nameButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                MainActivity.this.nameText.setText(R.string.name_text);
            }
        });*/


    /*private void configureNextButton(){
        createGroup = (Button)findViewById(R.id.buttonCreateGroup);
        createGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(createGroup.this, welcome.class));
            }
        });
    }*/
//}


/*

          const resp = await firestore.collection('groups')
            .add({
              name,
              organizer,
              summary,
              isPublic,
              lower_name,
              owner: uid,
              displayName
            })
      let { id } = resp;

      const add = await firestore.collection('users')
          .doc(uid)
          .collection('groupsApartOf')
          .add({ groupID: id, name, organizer });

      id = add.id

      await firestore.collection('groups')
          .doc(id)
          .collection('events')
          .add({
            location: 'Group Created',
            summary: 'Group Created',
            chosenDate: new Date().toString()
          })

      await firestore.collection('groups')
          .doc(id)
          .collection('users')
          .add({ displayName, userID: uid, owner: true, admin: true, deleteID: id })

    
*/
