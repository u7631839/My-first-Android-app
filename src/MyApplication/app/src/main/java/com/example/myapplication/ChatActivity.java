package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.myapplication.Fragment.LoginFragment;
import com.example.myapplication.Fragment.page2Fragment;
import com.example.myapplication.R;
import com.example.myapplication.conversation.Message;
import com.example.myapplication.tools.ChatAdapter;
import com.example.myapplication.tools.ContactsAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
/**@author Rong Sun u6591996
 * using to show the message between sender and receiver
 * */
public class ChatActivity extends AppCompatActivity {

    EditText message;
    RecyclerView recyclerView;
    Button send;
    ChatAdapter adapter;
    String time;



    public List<Message> messageList = new ArrayList<>();

    String receiverId;
    String senderId;

    String content;



    @Override
    public void onBackPressed() {
        // Intent i = new Intent(ChatActivity.this,ContactsActivity.class);
        //   startActivity(i);
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("message"); //
        message = (EditText) findViewById(R.id.chat_message);
        recyclerView = (RecyclerView) findViewById(R.id.chat_recyclerview);
        send = (Button) findViewById(R.id.sendButton);
        adapter = new ChatAdapter();
        Intent intent = getIntent();
        Intent intent1 = getIntent();
        String aa = null;
       if(intent.getStringExtra("good_uid")!=null){
           System.out.println("intent不为null");
           aa = intent.getStringExtra("good_uid");
       }else {
           System.out.println("intent神奇得null");
       }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        // getData();
        if(aa!=null){
            messageList = getData(aa);
        }else {

            System.out.println("还是null");

            aa = intent1.getStringExtra("contactSenderID");
            System.out.println("获取了吗？ ："+aa);
            System.out.println("获取了吗？ ："+ ContactsAdapter.painFul);

            messageList = getData(ContactsAdapter.painFul);
        }

        adapter.setChatMessages(messageList);
        recyclerView.setAdapter(adapter);
        //recyclerView.setAdapter(adapter);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  System.out.println("7是对的");
                if (message.getText().toString().isEmpty() || message.getText().toString().length() == 0) {

                } else {
                    final Message chat = new Message();
                    chat.setContent(message.getText().toString());
                    chat.setSenderID(LoginFragment.loginUser.getUid());
                    chat.setReceiverID(intent.getStringExtra("good_uid"));
                    System.out.println("receiverId="+chat.getReceiverID());
                    System.out.println("chat Activity 的 intent get到ID了吗： "+intent.getStringExtra("good_uid"));
                    System.out.println("chat Activity 的 intent get到ID了吗： "+ ContactsAdapter.painFul);
                    receiverId = intent.getStringExtra("good_uid");
                    adapter.addMessage(chat);
                   // System.out.println("8是对的");
                    // 将消息写入Firebase数据库
                    String messageId = databaseReference.push().getKey(); // 创建一个唯一的消息ID
                    databaseReference.child(messageId).setValue(chat);
                  //  System.out.println("9是对的");
                    // 清空消息输入框
                    message.setText("");
                    adapter.notifyDataSetChanged();
                    // System.out.println("结束");

                }

            }
        });
        ImageView backButton = findViewById(R.id.back_chat);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private List<Message> getData(String receiverIdS) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("message"); //
         databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Intent intent1 = getIntent();
                System.out.println("lisener");
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                       String content = snapshot.child("content").getValue(String.class);
                        String senderId = snapshot.child("senderID").getValue(String.class);
                        String receiverId = snapshot.child("receiverID").getValue(String.class);
                        System.out.println("receiverIdS = "+ContactsAdapter.painFul);
                        if (senderId==null || receiverId==null){
                            continue;
                        }
                        if(receiverId.equals(receiverIdS) && senderId.equals(LoginFragment.loginUser.getUid())){
                            Message chat = new Message();
                            chat.setContent(content);
                            chat.setSenderID(senderId);
                            chat.setReceiverID(receiverId);
                            System.out.println("5是对的");
                            messageList.add(chat);
                            System.out.println("6是对的");
                        }else if(receiverId.equals(LoginFragment.loginUser.getUid()) && senderId.equals(receiverIdS)){
                            Message chat = new Message();
                            chat.setContent(content);
                            chat.setSenderID(senderId);
                            chat.setReceiverID(receiverId);
                            System.out.println("5是对的");
                            messageList.add(chat);
                            System.out.println("6是对的");

                        }
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 处理错误
            }
        });
        /*adapter.setChatMessages(messageList);
        recyclerView.setAdapter(adapter);*/
         return messageList;
    }
}