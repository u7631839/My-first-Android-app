package com.example.myapplication.tools;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.myapplication.DatabaseController;
import com.example.myapplication.RegisterActivity;
import com.example.myapplication.entity.Cart;
import com.example.myapplication.entity.Good;
import com.example.myapplication.entity.User;
import com.github.javafaker.Faker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.type.DateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GenerateUserData {

    private static final int NUMBER_OF_USERS = 10;  // Change as needed
    private Faker faker = new Faker();
    private Set<String> existingEmails = new HashSet<>();

    public List<User> multiple_signup() {
        List<User> list=new ArrayList<>();
       /* for (int i=0;i<NUMBER_OF_USERS;i++){
            list.add(generateUniqueUser());
        }*/
        return list;

    }

   /* public User generateUniqueUser() {
        String email;
        do {
            email = faker.internet().emailAddress();
        } while (existingEmails.contains(email));
        existingEmails.add(email);

        String password = faker.internet().password();


        long registerTime;  // Current date and time
        registerTime = System.currentTimeMillis();
        System.out.println(registerTime);

    //    return new User(password, email, registerTime);

       List<Good> goodList =  new ArrayList<>();
        Good newGoods = new Good("cartList","Rustic Steel Hat","Kids","Lockman, Strosin and Connelly",169.81);
       //String gid, String name, String category, String brand
       goodList.add(newGoods);
       List<Cart> cartList = new ArrayList<>();
        cartList.add(new Cart("Kids",goodList)) ;
       return new User(  password,  email,  registerTime, goodList, cartList);

    }
*/

}

