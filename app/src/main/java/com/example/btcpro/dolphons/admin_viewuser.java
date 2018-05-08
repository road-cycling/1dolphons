package com.example.btcpro.dolphons;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/* https://guides.codepath.com/android/using-the-recyclerview bless */
public class admin_viewuser extends AppCompatActivity {

    //ArrayList<Contact> contacts;
    ArrayList<firebaseUser> fUser;
    private FirebaseFirestore FireStore;

    public String intentData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_viewuser);


        intentData = getIntent().getExtras().getString("groupID");

        fUser = new ArrayList<>();
        FireStore = FirebaseFirestore.getInstance();

        query();

    }


    public void query() {

        FireStore
                .collection("groupss")
                .document(intentData) /* for testing would be users uid in real life */
                .collection("users")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> data = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot item : data) {
                            System.out.println(item.getData());
                            fUser.add(new firebaseUser(item, intentData));
                        }
                        addAdapter();

                    }
                });
    }

    public void addAdapter() {
        RecyclerView rvContacts = findViewById(R.id.rvContacts);

        ContactsAdapter adapter = new ContactsAdapter(fUser);

        rvContacts.setAdapter(adapter);

        rvContacts.setLayoutManager(new LinearLayoutManager(this));

    }

}

class firebaseUser {
    private String uName;
    private String deleteID;
    public String intentData;

    public firebaseUser(DocumentSnapshot data, String intentData) {
        uName = data.get("user_name").toString();
        deleteID = data.get("userID").toString();
        intentData = intentData;
    }

    public firebaseUser(String name, String id, String intentData) {
        uName = name;
        deleteID = id;
        intentData = intentData;
    }

    public String getName() {
        return uName;
    }

    public String getDeleteID() {
        return deleteID;
    }

    public void deleteUser() {

        FirebaseFirestore FireStore = FirebaseFirestore.getInstance();
        //String groupID = "Lpux0m1ZmAHgiulBbvOZ";

        FireStore
                .collection("groupss")
                .document(intentData)
                .collection("users")
                .whereEqualTo("userID", deleteID)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> data = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot item : data) {
                            item.getReference().delete();
                        }


                    }
                });


        FireStore
                .collection("users")
                .document(deleteID)
                .collection("groupsApartOf")
                .whereEqualTo("groupID", intentData)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> data = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot item : data) {
                            item.getReference().delete();
                        }
                    }
                });

    }
}


class ContactsAdapter extends
        RecyclerView.Adapter<ContactsAdapter.ViewHolder> {


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nameTextView;
        public Button messageButton;


        public ViewHolder(View itemView) {

            super(itemView);

            nameTextView =  itemView.findViewById(R.id.contact_name);
            messageButton = itemView.findViewById(R.id.message_button);
        }
    }

    private List<firebaseUser> mContacts;

    public ContactsAdapter(List<firebaseUser> contacts) {
        mContacts = contacts;
    }

    @Override
    public ContactsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_contact, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ContactsAdapter.ViewHolder viewHolder, int position) {
        final firebaseUser contact = mContacts.get(position);

        TextView textView = viewHolder.nameTextView;
        textView.setText(contact.getName());
        Button button = viewHolder.messageButton;
        button.setText("Kick User");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contact.deleteUser();
            }
        });

    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mContacts.size();
    }
}