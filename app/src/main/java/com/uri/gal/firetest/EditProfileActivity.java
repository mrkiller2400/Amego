package com.uri.gal.firetest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.StrictMode;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

public class EditProfileActivity extends Activity implements View.OnClickListener{

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    private Button buttonSaveProfile;
    private EditText editTextFullName, editTextAge;
    private TextView textViewUserEmail;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }

        databaseReference = FirebaseDatabase.getInstance().getReference();
        editTextFullName = (EditText) findViewById(R.id.editTextFullName);
        editTextAge = (EditText) findViewById(R.id.editTextAge);
        buttonSaveProfile = (Button) findViewById(R.id.buttonSaveProfile);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);

        textViewUserEmail.setText("Welcome " + user.getEmail());

        buttonSaveProfile.setOnClickListener(this);


        //update fields with the correct data from firebase


        FirebaseDatabase.getInstance().getReference(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                UserInformation userInformation = dataSnapshot.getValue(UserInformation.class);
                editTextFullName.setText(userInformation.getName());
                editTextAge.setText(Integer.toString(userInformation.getAge()));
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Read From Firebase", "Failed to read value.", error.toException());
            }
        });





    }

    private void saveUserProfile()
    {
        String name = editTextFullName.getText().toString().trim();
        int age = Integer.parseInt(editTextAge.getText().toString().trim());

        UserInformation userInformation = new UserInformation(name, age);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        databaseReference.child(user.getUid()).setValue(userInformation);

        //send to server
        /*
        try {
            mySocket.connected();
            mySocket.sendMessage(name);
            mySocket.getMessageFromServer();
            Toast.makeText(this,mySocket.getData(), Toast.LENGTH_LONG).show();
            mySocket.disconected();
        }catch (IOException ex){
            System.out.print(ex.toString());
        }
        */

        //update the user
        Toast.makeText(this,"Information Saved...", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view)
    {
        if(view == buttonSaveProfile)
        {
            saveUserProfile();
            finish();
            startActivity(new Intent(this,ProfileActivity.class));
        }
    }
}
