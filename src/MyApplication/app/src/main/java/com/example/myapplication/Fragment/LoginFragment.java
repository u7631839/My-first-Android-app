package com.example.myapplication.Fragment;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.ContactsActivity;
import com.example.myapplication.DataController.UsersTreeTool.AVLUserNode;
import com.example.myapplication.MainActivity;
import com.example.myapplication.MyApplication;
import com.example.myapplication.MyFragmentPagerAdapter;
import com.example.myapplication.R;
import com.example.myapplication.RegisterActivity;
import com.example.myapplication.ChatActivity;
import com.example.myapplication.entity.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.apache.commons.lang3.StringUtils;


/**
 *@Author Xuejie Dong u7602840
 * The loginFracment to allow login acitivity
 */

public class LoginFragment extends Fragment {

    private FirebaseAuth mAuth;

    private EditText email, password;
    public MyFragmentPagerAdapter myFragmentPagerAdapter;
    //private boolean isLoggedIn;
    private View rootView;
    public static User loginUser;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (rootView==null) {
            rootView = inflater.inflate(R.layout.fragment_login, container, false);
        }
        email = rootView.findViewById(R.id.usernameEditText);
        password = rootView.findViewById(R.id.passwordEditText);
        Button button1 = rootView.findViewById(R.id.loginButton);

        TextView registerTextView = rootView.findViewById(R.id.registerTextView);

        // Set a click listener for the login button
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input and initiate login
                String e = email.getText().toString();
                String p = password.getText().toString();
                if (StringUtils.isNotEmpty(e) && StringUtils.isNotEmpty(p)) {
                    loginEvent();
                }
                else {
                    // Show a message for incomplete input
                    Toast.makeText(getActivity(), "Please enter the correct email and password",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

        // Set a click listener for the register text view
        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the registration activity
                goToRegistration();
            }
        });

        return rootView;
    }
    //@Override
    //public void onResume(){
      //  super.onResume();
     //   myFragmentPagerAdapter.getItem(3);
   // }

    private void goToRegistration() {
        // Start the registration activity here
        Intent registrationIntent = new Intent(getActivity(), RegisterActivity.class);
        startActivity(registrationIntent);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // Handle the case when the user is already logged in
        }
    }

    private void loginEvent() {
        String e = email.getText().toString();
        String p = password.getText().toString();

        mAuth.signInWithEmailAndPassword(e, p)
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        /*if (task.isSuccessful()) {
                            final String uid = task.getResult().getUser().getUid();
                            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("users").child(uid);
                            Toast.makeText(getActivity(), "Login Successful", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "Login Failed", Toast.LENGTH_SHORT).show();
                        }*/


                        if (task.isSuccessful()) {
                            // Handle the case when the user is already logged in
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "Login Successful");
                            Toast.makeText(getActivity(), "Login Successful",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(getActivity(), LoginFragment.class);
                            User userFromTree = findUserFromTree(user);
                            loginUser = userFromTree;
                           // System.out.println("看看User是哪个UID的："+userFromTree.getUid());
                            intent.putExtra("loginUser", userFromTree);
                            Fragment myfragment = new MyFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("email", e);
                            myfragment.setArguments(bundle);

                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .addToBackStack(null)
                                    .add(R.id.fragment_container, myfragment)
                                    .commit();
                           // myFragmentPagerAdapter=new MyFragmentPagerAdapter(getFragmentManager());
                            //myFragmentPagerAdapter.refreshAdapter();
                          //  user.getUid();
                            /*
                            Bundle bundle = new Bundle();
                            String userEmail = user.getEmail();
                            bundle.putString("email", userEmail);

                            MyFragment myfragment = new MyFragment();
                            myfragment.setArguments(bundle);


                            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                            //fragmentTransaction.remove(LoginFragment.this);
                            fragmentTransaction.replace(R.id.fragment_container,myfragment);
                            //fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            //ViewPager viewPager = requireActivity().findViewById(R.id.viewpageer);
                            //viewPager.setVisibility(View.GONE);

                             */

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "Login Failed", task.getException());
                            Toast.makeText(getActivity(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    public LoginFragment() {
        // Required empty public constructor
    }

    private void updateUI(FirebaseUser user) {

    }

    private  com.example.myapplication.entity.User findUserFromTree(FirebaseUser user){
        if(user!=null){
            AVLUserNode userNode =  MyApplication.userTree.searchByID(user.getUid());
           // System.out.println("firebaseUser的UID："+user.getUid());
            com.example.myapplication.entity.User loginUser = userNode.value;
            return loginUser;
        }else {
            return null;
        }
    }


}