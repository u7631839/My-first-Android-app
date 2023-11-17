package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.entity.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**@author Xuejie Dong u7602840
 * loging activity but we do not use it any more
 * */
public class login extends AppCompatActivity {

    private FirebaseAuth mAuth;

    EditText email, password;
// ...
// Initialize Firebase Auth

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        email = (EditText) findViewById(R.id.usernameEditText);
        password = (EditText) findViewById(R.id.passwordEditText);
        Button button1 = (Button)findViewById(R.id.loginButton);

        //点击button, 获得text, 放进array里面
        Button.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginEvent();


            }
        };
        button1.setOnClickListener(listener);

    }


    public void goToRegistration(TextView view) {
        // Start the registration activity here
        Intent registrationIntent = new Intent(this, RegisterActivity.class);
        startActivity(registrationIntent);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            //reload();
        }
    }

    public void loginEvent(){
        mAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                .addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            final String uid = task.getResult().getUser().getUid();
                            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("users").child(uid);

                            Toast.makeText(login.this, "Yep", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(login.this, "error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void upload(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, 10);
    }

   /* public void LoginButton(View v, EditText email, EditText password){

        BufferedReader bufferedReader;

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(getAssets().open("loginDetails.csv"), StandardCharsets.UTF_8));
            String line;

            while((line = bufferedReader.readLine()) != null) {
                String[] tokens = line.split("\\|");
                boolean username = false;
                boolean pw = false;
                for (String token:tokens) {
                    String[] temp = token.split(":");
                    if (temp[0].equals("username")) {
                        username = temp[1].equals(email.getText().toString());
                    }
                    if (temp[0].equals("password")) {
                        pw = temp[1].equals(password.getText().toString());

                    }
                }

                if (username && pw) {
                    User u = new User(
                            tokens[1].substring(9),
                            tokens[2].substring(9));
                    Intent intent = new Intent(this, MainActivity.class);
                   *//* intent.putExtra("USER", u);
                    startActivity(intent);*//*
                    return;
                }
            }
            bufferedReader.close(); // Close the reader to prevent problems.
            Toast.makeText(getApplicationContext(), "Not Valid", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
        }


    }*/
}