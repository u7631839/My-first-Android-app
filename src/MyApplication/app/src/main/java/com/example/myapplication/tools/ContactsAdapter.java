package com.example.myapplication.tools;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.ChatActivity;
import com.example.myapplication.ContactsActivity;
import com.example.myapplication.DataController.UsersTreeTool.AVLUserNode;
import com.example.myapplication.Fragment.LoginFragment;
import com.example.myapplication.MyApplication;
import com.example.myapplication.R;
import com.example.myapplication.conversation.Message;
import com.example.myapplication.entity.User;

import java.util.ArrayList;
import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactListHolder> {

    List<Message> contacts = new ArrayList<>();
    String receiverId;
    String timeStampStr;

    public static  String painFul = null;

    User loginUser;

    public ContactsAdapter(Interactor interactor) {
        this.interactor = interactor;
    }

    public interface Interactor{
        void onChatClicked(int position,Message contact);
        void onChatLongClicked(int position,Message contact);
    }

    Interactor interactor;

    @Override
    public ContactListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item,parent,false);
        return new ContactListHolder(view);
    }


    @Override
    public void onBindViewHolder(ContactListHolder holder, int position) {
        System.out.println("ViewHolder没问题");
         Message contact = contacts.get(position);

        receiverId = contact.getReceiverID();
        //Call a select query on the hasura auth  table to get username where receiverId = user_id
        if(contact.getReceiverID().equals(LoginFragment.loginUser.getUid())){
            AVLUserNode userNode = MyApplication.userTree.searchByID(contact.getSenderID());
            holder.name.setText(userNode.value.getEmail());
        }else {
            AVLUserNode userNode = MyApplication.userTree.searchByID(contact.getReceiverID());
            holder.name.setText(userNode.value.getEmail());
        }

        holder.contents.setText(contact.getContent());
        System.out.println("adapter1");
       // timeStampStr = contact.getTime();
        holder.time.setText(timeStampStr);
        if(contact.getSenderID().equals(LoginFragment.loginUser.getUid()) ) {
            System.out.println("adapter2");
            holder.sent_or_received.setText("S");
        }else {
            System.out.println("adapter3");
            holder.sent_or_received.setText("R");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* System.out.println("adapter3");
                Intent i = new Intent(, ChatActivity.class);
                // i.putExtra("receiverid",Global.receiverId);
                startActivity(i);
                interactor.onChatClicked(position,contact);*/

                Intent intent = new Intent(v.getContext(), ChatActivity.class);

                // 获取被点击的联系
                Message contact = contacts.get(position);
                System.out.println("我点点点！！！");
                if (contact.getSenderID() .equals(LoginFragment.loginUser.getUid()) ) {
                    //if I am the sender
                    //   Global.receiverId = contact.getReceiverID();
                    //i.putExtra("xxxx",contact.getReceiverID());
                    System.out.println("进来了跳转1");
                    painFul = contact.getReceiverID();
                } else {
                    //  Global.receiverId = contact.getSenderID();
                  //  i.putExtra("xxxx",contact.getSenderID());
                    System.out.println("进来了跳转2");
                    painFul = contact.getSenderID();
                }

                // 将联系的信息传递给 ChatActivity
                intent.putExtra("contactSenderID", contact.getSenderID());
                intent.putExtra("contactReceiverID", contact.getReceiverID());
                intent.putExtra("contactContent", contact.getContent());
                // 你可以继续添加其他需要传递的信息

                // 启动 ChatActivity
                v.getContext().startActivity(intent);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                interactor.onChatLongClicked(position,contact);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public void setContacts(List<Message> contacts){
        this.contacts = contacts;
        notifyDataSetChanged();
    }

    public void addContact(Message contact){
        contacts.add(contact);
        notifyDataSetChanged();
    }

    public void deleteContact(int position){
        contacts.remove(contacts.get(position));
        notifyDataSetChanged();
    }

  /*  public String getRequiredTime(String timeStampStr){
        try{
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date netDate = (new Date(Long.parseLong(timeStampStr)));
            return sdf.format(netDate);
        } catch (Exception ignored) {
            return "xx";
        }
    }*/
}