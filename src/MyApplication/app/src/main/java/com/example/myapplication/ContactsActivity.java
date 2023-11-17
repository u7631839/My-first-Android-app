package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.Fragment.LoginFragment;
import com.example.myapplication.conversation.Message;
import com.example.myapplication.entity.User;
import com.example.myapplication.tools.ContactsAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
/**Rong Sun u6591996
 * show the contact list of different users and the login user
 * */
public class ContactsActivity extends AppCompatActivity {

    ContactsAdapter adapter;
    RecyclerView recyclerView;
    String latestTime;

    DatabaseReference databaseReference;

    User loginUser = LoginFragment.loginUser;

  //  private static final String DATABASE_NAME = "chatapp";
  //  private static final int DATABASE_VERSION = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
       /* final DataBaseHandler db = new DataBaseHandler(this,DATABASE_NAME,null,DATABASE_VERSION);


        final HasuraUser user = Hasura.currentUser();*/
         databaseReference = FirebaseDatabase.getInstance().getReference("message");

      /*  user.getQueryBuilder()
                .useDataService()
                .setRequestBody(new SelectMessagesQuery(latestTime))
                .expectResponseTypeArrayOf(ChatMessage.class)
                .build()
                .executeAsync(new Callback<List<ChatMessage>, HasuraException>() {
                    @Override
                    public void onSuccess(List<ChatMessage> chatMessages) {
                        int i;
                        for(i = 0; i < chatMessages.size(); i++)
                            db.insertMessage(chatMessages.get(i));
                    }

                    @Override
                    public void onFailure(HasuraException e) {
                        Toast.makeText(ContactsActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });*/
        recyclerView = (RecyclerView) findViewById(R.id.contacts_recyclerview);

       // Global.senderId = 1;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        adapter = new ContactsAdapter(new ContactsAdapter.Interactor(){
            @Override
            public void onChatClicked(int position, Message contact) {

                if(contact.getSenderID() == loginUser.getUid()){
                    //if I am the sender
                 //   Global.receiverId = contact.getReceiverID();
                } else{
                  //  Global.receiverId = contact.getSenderID();
                }
                Intent i = new Intent(ContactsActivity.this,ChatActivity.class);
               // i.putExtra("receiverid",Global.receiverId);
                startActivity(i);
            }

            @Override
            public void onChatLongClicked(final int position, final Message contact) {
             //   checkForDeleteContact(position,contact);
            }
        });
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        System.out.println("contact1");
      //  adapter.setContacts(db.getAllContacts());
        // 添加数据监听
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("contact lisener");
                List<Message> chatMessages = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String receiverID = snapshot.child("receiverID").getValue(String.class);
                    String senderID = snapshot.child("senderID").getValue(String.class);
                 //   System.out.println("contact2");
                    // 检查是否当前用户参与了对话
               //     if (loginUser.getUid().equals(receiverID) || loginUser.getUid().equals(senderID)) {
                    if (loginUser.getUid().equals(receiverID) || loginUser.getUid().equals(senderID)) {
                        /*for (DataSnapshot messageSnapshot : snapshot.child("message").getChildren()) {
                            Message message = messageSnapshot.getValue(Message.class);
                            chatMessages.add(message);
                        }*/
                        Message message = snapshot.getValue(Message.class);
                        chatMessages.add(message);
                     //   System.out.println("contact3");
                    }
                   /* Message message = snapshot.getValue(Message.class);
                    chatMessages.add(message);*/
                }
                adapter.setContacts(chatMessages);
              //  System.out.println("contact4");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // 处理数据读取错误
            }
        });
    }

 /*   public void checkForDeleteContact(final int position, final ChatMessage contact){
        final DataBaseHandler db = new DataBaseHandler(this,DATABASE_NAME,null,DATABASE_VERSION);

        AlertDialog.Builder alert = new AlertDialog.Builder(ContactsActivity.this);
        alert.setMessage("Are you sure you want to delete this chat?");
        alert.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int id = contact.getReceiver();
                db.deleteContact(id);
                adapter.deleteContact(position);
            }
        });
    }*/

/*    public String getRequiredTime(String timeStampStr){
        try{
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date netDate = (new Date(Long.parseLong(timeStampStr)));
            return sdf.format(netDate);
        } catch (Exception ignored) {
            return "xx";
        }
    }*/
}