package com.example.myapplication.tools;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Fragment.LoginFragment;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.conversation.Message;
import com.example.myapplication.entity.User;

import android.view.View;
import android.widget.TextView;

import java.util.List;

public class ChatAdapter extends  RecyclerView.Adapter<ChatViewHolder>  {

    private User loginUser;
    private List<Message> messages;

    String timeStampStr;
    private static final int OUTGOING = 2;
    private static final int INCOMING = 1;

    public ChatAdapter(List<Message> messages) {
        this.messages = messages;
    }
    public ChatAdapter() {
        super();
    }
    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == OUTGOING) {
           return new LeftChatViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chatitem_left, parent, false));
        } else {
            return new RightChatViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chatitem_right, parent, false));
        }
    }
    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        final Message chatMessage = messages.get(position);
        if(holder.getItemViewType() == INCOMING){
           // System.out.println("==INCOMING");
            RightChatViewHolder viewHolder = (RightChatViewHolder) holder;
            viewHolder.contents.setText(chatMessage.getContent());
           // timeStampStr = chatMessage.getTime();
          //  viewHolder.time.setText((timeStampStr));
        }else{
          //  System.out.println("==OUTGOING");
            LeftChatViewHolder viewHolder = (LeftChatViewHolder) holder;

            //System.out.println("1没错");
            viewHolder.contents.setText(chatMessage.getContent());
            System.out.println("2没错");
           // timeStampStr = chatMessage.getTime();
          //  viewHolder.time.setText((timeStampStr));
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        // 返回消息的类型（OUTGOING 或 INCOMING）
        if(messages.get(position).getSenderID().equals(LoginFragment.loginUser.getUid())){
            System.out.println("获取type");
            return 1;
        }else{
            return 2;
        }
    }
    public void setChatMessages(List<Message> messages){
        this.messages = messages;
        notifyDataSetChanged();
    }

    public void addMessage(Message message){
        this.messages.add(message);
        notifyDataSetChanged();
    }

    public void deleteMessage(int position){
        this.messages.remove(position);
        notifyDataSetChanged();
    }

 /*   public String getRequiredTime(String timeStampStr){
        try{
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date netDate = (new Date(Long.parseLong(timeStampStr)));
            return sdf.format(netDate);
        } catch (Exception ignored) {
            return "xx";
        }
    }*/



}
