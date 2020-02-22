package com.example.dataauth2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity
{

    private EditText emailField;
    private EditText passwordField;

    private Button auth_button;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailField=(EditText)findViewById(R.id.email_field);
        passwordField=(EditText)findViewById(R.id.password_field);
        auth_button=(Button)findViewById(R.id.auth_button);

        mAuth=FirebaseAuth.getInstance();

        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser()!=null)    // not logged in
                {
                    Toast.makeText(MainActivity.this,"Logged in with" +emailField,Toast.LENGTH_LONG).show();
                    startActivity(new Intent(MainActivity.this,Account_activity.class));
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Signed out",Toast.LENGTH_LONG).show();
                }
            }
        };

        auth_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignIn();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);


    }

    private void startSignIn()
    {
        String email=emailField.getText().toString();
        String password=passwordField.getText().toString();


        if(TextUtils.isEmpty(email)||TextUtils.isEmpty(password))
        {
            Toast.makeText(MainActivity.this,"Fields empty ",Toast.LENGTH_LONG).show();
        }

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful())
                {
                    Toast.makeText(MainActivity.this,"Sign in problem",Toast.LENGTH_LONG ).show();
                }
            }
        });
    }
}
