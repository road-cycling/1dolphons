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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/* https://guides.codepath.com/android/using-the-recyclerview bless */
public class admin_viewuser extends AppCompatActivity {

    //ArrayList<Contact> contacts;
    ArrayList<firebaseUser> fUser;
    private FirebaseFirestore FireStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_viewuser);

        fUser = new ArrayList<firebaseUser>();
        FireStore = FirebaseFirestore.getInstance();


        fUser.add(new firebaseUser("Nathan", "f"));
        fUser.add(new firebaseUser("Nathan", "f"));
        fUser.add(new firebaseUser("Nathan", "f"));
        fUser.add(new firebaseUser("Nathan", "f"));


        RecyclerView rvContacts = findViewById(R.id.rvContacts);


        //contacts = Contact.createContactsList(20);
        ContactsAdapter adapter = new ContactsAdapter(fUser);

        rvContacts.setAdapter(adapter);

        rvContacts.setLayoutManager(new LinearLayoutManager(this));

        query(adapter);



    }

    public void query(ContactsAdapter adapter) {
        FireStore
                .collection("groupss")
                .document("z6lBx0owSf5jRN6Jfnfa") /* for testing would be users uid in real life */
                .collection("users")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> data = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot item : data) {
                            fUser.add(new firebaseUser(item));
                        }

                    }
                });
        adapter.notifyDataSetChanged(); //notworking



    }
}

class firebaseUser {
    private String uName;
    private String deleteID; //pos

    public firebaseUser(DocumentSnapshot data) {
        uName = data.get("name").toString();
        deleteID = data.get("deleteID").toString();
    }

    public firebaseUser(String name, String id) {
        uName = name;
        deleteID = id;
    }

    public String getName() {
        return uName;
    }

    public String getDeleteID() {
        return deleteID;
    }
}


class ContactsAdapter extends
        RecyclerView.Adapter<ContactsAdapter.ViewHolder> {


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nameTextView;
        public Button messageButton;


        public ViewHolder(View itemView) {

            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.contact_name);
            messageButton = (Button) itemView.findViewById(R.id.message_button);
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
        firebaseUser contact = mContacts.get(position);

        TextView textView = viewHolder.nameTextView;
        textView.setText(contact.getName());
        Button button = viewHolder.messageButton;
        button.setText("Kick User");
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mContacts.size();
    }
}