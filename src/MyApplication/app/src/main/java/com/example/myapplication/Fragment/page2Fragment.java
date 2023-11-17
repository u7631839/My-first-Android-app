package com.example.myapplication.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.ChatActivity;
import com.example.myapplication.ContactsActivity;
import com.example.myapplication.GoodDetailsActivity;
import com.example.myapplication.R;
import com.example.myapplication.conversation.Message;
import com.example.myapplication.entity.Good;
import com.example.myapplication.entity.User;
import com.example.myapplication.tools.ContactsAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Contact Page Fragment
 * @Author Rong Sun U6591996
 * To have a contact list to receive and send the message
 */
public class page2Fragment extends Fragment {
    ContactsAdapter adapter;
    RecyclerView recyclerView;
    String latestTime;

    public static  String painFul = null;

    DatabaseReference databaseReference;

    User loginUser;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_page2, container, false);
            databaseReference = FirebaseDatabase.getInstance().getReference("message");
            recyclerView = view.findViewById(R.id.contacts_recyclerview);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
       // System.out.println("page2Fragment0");
        if (LoginFragment.loginUser !=null) {
           // System.out.println("page2Fragment1");
            adapter = new ContactsAdapter(new ContactsAdapter.Interactor() {
                Intent i = new Intent(getActivity(), ChatActivity.class);
               /* Intent intent = new Intent(GoodDetailsActivity.this, ChatActivity.class);
                intent.putExtra("good_uid", uID) ;
                startActivity(intent);*/
                @Override
                public void onChatClicked(int position, Message contact) {
                    if (contact.getSenderID() .equals(LoginFragment.loginUser.getUid()) ) {
                        //if I am the sender
                        //   Global.receiverId = contact.getReceiverID();
                        i.putExtra("xxxx",contact.getReceiverID());
                        System.out.println("进来了跳转1");
                        painFul = contact.getReceiverID();
                    } else {
                        //  Global.receiverId = contact.getSenderID();
                        i.putExtra("xxxx",contact.getSenderID());
                        System.out.println("进来了跳转2");
                        painFul = contact.getSenderID();
                    }
                   // Intent i = new Intent(getActivity(), ChatActivity.class);
                    // i.putExtra("receiverid",Global.receiverId);
                    startActivity(i);
                }

                @Override
                public void onChatLongClicked(int position, Message contact) {
                    // Handle chat long click
                }
            });
            // Find the RecyclerView widget in the layout
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(adapter);
            // Get a reference to the Firebase database
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // System.out.println("contact lisener");
                    HashMap<String, List<Message>> conversationEach = new HashMap<>();
                    List<Message> chatMessages = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String receiverID = snapshot.child("receiverID").getValue(String.class);
                        String senderID = snapshot.child("senderID").getValue(String.class);
                        Message message = snapshot.getValue(Message.class);
                        //   System.out.println("contact2");
                        // 检查是否当前用户参与了对话
                        //     if (loginUser.getUid().equals(receiverID) || loginUser.getUid().equals(senderID)) {
                     /*   if (LoginFragment.loginUser.getUid().equals(receiverID) || LoginFragment.loginUser.getUid().equals(senderID)) {
                        *//*for (DataSnapshot messageSnapshot : snapshot.child("message").getChildren()) {
                            Message message = messageSnapshot.getValue(Message.class);
                            chatMessages.add(message);
                        }*//*
                           // Message message = snapshot.getValue(Message.class);
                            chatMessages.add(message);
                            //   System.out.println("contact3");
                        }*/

                        // Check if the user is logged in

                        if(LoginFragment.loginUser.getUid().equals(receiverID)){
                            ////record message that she sent to me
                            // Create an adapter for displaying chat contacts
                            // Handle messages received from others
                            List<Message> messages = conversationEach.get(senderID);
                            if(messages==null){
                                List<Message> messageList = new ArrayList<>();
                                messageList.add(message);
                                conversationEach.put(senderID,messageList);
                            }else{
                                messages.add(message);
                                conversationEach.put(senderID,messages);
                            }
                        }else if(LoginFragment.loginUser.getUid().equals(senderID)){
                            //record message that I sent to her
                            // Handle messages sent by the current user
                            List<Message> messages = conversationEach.get(receiverID);
                            if(messages==null){
                                List<Message> messageList = new ArrayList<>();
                                messageList.add(message);
                                conversationEach.put(receiverID,messageList);
                            }else{
                                messages.add(message);
                                conversationEach.put(receiverID,messages);
                            }
                        }


                   /* Message message = snapshot.getValue(Message.class);
                    chatMessages.add(message);*/
                    }
                    // Create a list of latest messages from each conversation
                    for (Map.Entry<String, List<Message>> entry : conversationEach.entrySet()) {
                        chatMessages.add(entry.getValue().get(entry.getValue().size()-1));
                    }
                    // Update the adapter with the chat contacts
                    adapter.setContacts(chatMessages);
                    //  System.out.println("contact4");
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // 处理数据读取错误
                }
            });
            adapter.notifyDataSetChanged();
            // Find the empty view widget in the layout and hide it
            TextView emptyView = view.findViewById(R.id.textView3);
            emptyView.setVisibility(View.GONE);
            // Your data retrieval logic and adapter setup should go here
        }else {
            // The user is not logged in, show the empty view
            TextView emptyView = view.findViewById(R.id.textView3);
            emptyView.setVisibility(View.VISIBLE);
            // Hide the RecyclerView
            recyclerView.setVisibility(View.GONE);
        }
        return view;
    }
}