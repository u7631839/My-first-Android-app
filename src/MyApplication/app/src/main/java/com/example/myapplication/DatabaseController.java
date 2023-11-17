package com.example.myapplication;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.myapplication.conversation.Message;
import com.example.myapplication.entity.Cart;
import com.example.myapplication.entity.Good;
import com.example.myapplication.entity.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.type.DateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * using this class to control the firebase
 * @Author Shiyu Pan u7615103
 * */
public class DatabaseController {

/*    public void addAdaLovelace(String email, String password, long registerTime, String uid, List<Good> lauchGoods, List<Cart> cartList) {
        //    User user = new User( email,password,registerTime);
        //long registerTime, String password, String email, String uid, List<Good> lauchGoods, List<Cart> cartList

       List<Test252> test252List = new ArrayList<>();
       test252List.add(new Test252("111"));
       test252List.add(new Test252("111"));
       test252List.add(new Test252("111"));
        List<Good> goodList = new ArrayList<>();
        goodList.add(new Good("111","222","333","444"));
        goodList.add(new Good("111","222","333","444"));
        goodList.add(new Good("111","222","333","444"));
        User user = new User( registerTime,  password, email, uid, new ArrayList<>(), new ArrayList<>());

        reference.child("users").child(uid).setValue(user);
        reference.child("users").child(uid).child("lauchGoods").setValue(lauchGoods);
        reference.child("users").child(uid).child("cartList").setValue(cartList);

        System.out.println("111111");
        String lauchGoodId = reference.child("tests").push().getKey(); // 为每个 Good 生成一个唯一 ID
        System.out.println("22222");
        reference.child("tests").child(lauchGoodId).setValue(new Test252("kawayi"));
        System.out.println("33333");



    }*/

    // TODO: store an instance of the printer queue.
    private static DatabaseController instance = null;

    private static FirebaseDatabase db = null;

    private  static DatabaseReference reference = null;

    private DatabaseController() {

    }

    /**
     * @return new instance if it currently does not exist and current instance if it already exists.
     */
    public static DatabaseController getInstance() {
        //using the singleton method
        if(instance == null){
            instance = new DatabaseController();
            // db = FirebaseFirestore.getInstance();
            reference = FirebaseDatabase.getInstance().getReference();
        }
        return instance;
    }

    // Create a new user with a first and last name
    public void addAdaLovelace(String email, String password, String uid) {
        // add the new user into firease, and set two empty lists.
        List<Good> lauchGoods = new ArrayList<>();
        List<Cart> cartList = new ArrayList<>();
        User user = new User(System.currentTimeMillis(),password,email,uid,lauchGoods,cartList);
        //select the branch users
        reference.child("users").child(uid).setValue(user);
    }
    public void setUser(User user){
        //change the users's value
        reference.child("users").child(user.getUid()).setValue(user);
    }
    public void addGood(Good good){
        //add a new good
        reference.child("goods").child(good.getGid()).setValue(good);
    }

    public void sendMessage(Message message){
        //sending a message in to firebase
        reference.child("message").child(message.getReceiverID()).setValue(message);
    }

}
