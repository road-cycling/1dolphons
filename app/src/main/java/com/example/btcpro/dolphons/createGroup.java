package com.example.btcpro.dolphons;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;



public class createGroup extends AppCompatActivity
{
    private Button createGroup;
    private CheckBox privateGroup;
    private EditText groupName;
    private EditText groupDesc;
    private Button chooseImage;
    private ImageView imageView;

    private Uri imageUri;
    private StorageReference storageRef;
    private DatabaseReference databaseRef;

    private static final int PICK_IMAGE_REQUEST = 1;

    private FirebaseFirestore FireStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        FireStore = FirebaseFirestore.getInstance();

        groupName = (EditText) findViewById(R.id.groupNameTextEnter);
        groupDesc = (EditText) findViewById(R.id.enterGroupDesc);
        privateGroup = (CheckBox) findViewById(R.id.checkboxPrivate);
        createGroup = (Button) findViewById(R.id.buttonCreateGroup);
        chooseImage = (Button) findViewById(R.id.chooseImage);
        imageView = (ImageView) findViewById(R.id.imageView);

        storageRef = FirebaseStorage.getInstance().getReference("uploads");
        databaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        createGroup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //What to do after create button is pressed
                String name = groupName.getText().toString();
                String desc = groupDesc.getText().toString();

                //Checkbox not done yet
                boolean privateCheck = privateGroup.isChecked();

                uploadFile();

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

    private void openFileChooser()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private String getFileExtension(Uri uri)
    {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile()
    {
        if (imageUri != null)
        {
            StorageReference fileReference = storageRef.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));

            fileReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
                    {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                        {
                            Toast.makeText(createGroup.this, "Group Succesfully Created", Toast.LENGTH_LONG).show();
                            Upload upload = new Upload(taskSnapshot.getDownloadUrl().toString());
                            String uploadId = databaseRef.push().getKey();
                            databaseRef.child(uploadId).setValue(upload);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener()
                    {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {
                            //pop up notification needed
                            Toast.makeText(createGroup.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        else
        {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            imageUri = data.getData();

            Picasso.with(this).load(imageUri).into(imageView);
            //imageView.setImageURI(imageUri);
        }
    }
}