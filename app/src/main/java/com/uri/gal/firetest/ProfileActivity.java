package com.uri.gal.firetest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener
{
    private FirebaseAuth firebaseAuth;
    private Button buttonLogout, buttonEditProfile, buttonRemoteControl, buttonVideoChat, buttonManageFriends;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null)
        {
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user.isEmailVerified())//doesnt work
        {
            Toast.makeText(this, "Email is verified", Toast.LENGTH_SHORT).show();
        }
        else
        {
            user.sendEmailVerification();
        }

        TextView textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
        
        textViewUserEmail.setText("Welcome " + user.getEmail());
        buttonLogout = (Button) findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(this);

        buttonEditProfile =  (Button) findViewById(R.id.buttonEditProfile);
        buttonEditProfile.setOnClickListener(this);

        buttonRemoteControl = (Button) findViewById(R.id.buttonRemoteControl);
        buttonRemoteControl.setOnClickListener(this);

        buttonVideoChat = (Button) findViewById(R.id.buttonVideoChat);
        buttonVideoChat.setOnClickListener(this);

        buttonManageFriends  = (Button) findViewById(R.id.buttonManageFriends);
        buttonManageFriends.setOnClickListener(this);
    }

        @Override
        public void onClick(View view)
        {
            if (view == buttonLogout)
            {
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(this, LoginActivity.class));
            }
            else if(view == buttonEditProfile)
            {
                finish();
                startActivity(new Intent(this, EditProfileActivity.class));
            }
            else if(view == buttonRemoteControl)
            {
                finish();
                startActivity(new Intent(this, RemoteControlActivity.class));
            }
            else if(view == buttonVideoChat)
            {
                finish();
                startActivity(new Intent(this, VideoChatHubActivity.class));
            }
            else if(view == buttonManageFriends)
            {
                finish();
                startActivity(new Intent(this, ManageFriendsActivity.class));
            }
        }
}
