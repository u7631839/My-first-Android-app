package com.example.myapplication;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.myapplication.Fragment.LoginFragment;
import com.example.myapplication.entity.Good;
import com.example.myapplication.entity.User;
import com.example.myapplication.tools.GenerateGoodsData;
import com.example.myapplication.tools.GenerateUserData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;
/**This activity is used to register a new users
 * @author Shiyu Pan u7615103
 * */

public class RegisterActivity extends AppCompatActivity {

  /*  private String password;
    private String email;*/
    EditText email, password;
    User user=new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //from this items we will get all the necessary information of the new user
        email = (EditText) findViewById(R.id.usernameRegisterText);
        password = (EditText) findViewById(R.id.passwordRegisterText);
        Button button1 = (Button)findViewById(R.id.registerButton);

        //点击button, 获得text, 放进array里面
        Button.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GenerateUserData generateUserData=new GenerateUserData();
              //  signup(generateUserData.multiple_signup());
                signup();

            }
        };
        button1.setOnClickListener(listener);
        ImageView backButton2 = findViewById(R.id.backButton2);
        backButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
               /* GenerateUserData generateUserData=new GenerateUserData();
                signup(generateUserData.multiple_signup());*/

            }
        });

    }

    //this function will submit a register into cloud
    public void signup(){
        if (email.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Please enter both email and password!", Toast.LENGTH_SHORT).show();
            return;
        }
        //register in firebase
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    //record information into realtime database
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            final String uid = task.getResult().getUser().getUid();
                            DatabaseController controller = DatabaseController.getInstance();
                            controller.addAdaLovelace(email.getText().toString(),password.getText().toString(),uid);
                            Toast.makeText(RegisterActivity.this, "Yep", Toast.LENGTH_SHORT).show();
                            goToNextActivity();
                        } else {
                            Toast.makeText(RegisterActivity.this, "error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
    public void upload(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, 10);

    }

    private void goToNextActivity() {
        Intent intent = new Intent(this, LoginFragment.class);
        startActivity(intent);
    }



}