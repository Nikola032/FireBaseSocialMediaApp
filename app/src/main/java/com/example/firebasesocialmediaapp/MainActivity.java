package com.example.firebasesocialmediaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {


    private EditText edtEmail, edtUserName,edtPassword;
    private Button btnSignUp,btnSignIn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtUserName = findViewById(R.id.edtUserName);

        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignUp = findViewById(R.id.btnSignUp);
        mAuth = FirebaseAuth.getInstance();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });





    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            //Transition to next activity
            transitionToSocialMediaActivity();
    }
    }

    private void signUp () {
        mAuth.createUserWithEmailAndPassword(edtEmail.getText().toString(),
                edtPassword.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(MainActivity.this, "Signing Up successfuly", Toast.LENGTH_SHORT).show();

                            FirebaseDatabase.getInstance().getReference().child("my_users")
                                    .child(task.getResult().getUser().getUid()).child("username")
                                    .setValue(edtUserName.getText().toString());



                    transitionToSocialMediaActivity();
                    // Write a message to the database




                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(MainActivity.this, "Signing Up Faild", Toast.LENGTH_SHORT).show();

                }
            }});
    }

    private void signIn () {
        mAuth.signInWithEmailAndPassword(edtEmail.getText().toString(),edtPassword.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
               if (task.isSuccessful()){
                   Toast.makeText(MainActivity.this, "Signing In successfuly", Toast.LENGTH_SHORT).show();
                   transitionToSocialMediaActivity();

               }else {
                   Toast.makeText(MainActivity.this, "Signing in Faild", Toast.LENGTH_SHORT).show();

               }
            }
        });
    }
    private void transitionToSocialMediaActivity () {
        Intent intent = new Intent(this,SocialMediaActivity.class);
        startActivity(intent);

    }
}
