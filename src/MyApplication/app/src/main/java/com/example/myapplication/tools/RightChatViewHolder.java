package com.example.myapplication.tools;

import android.view.View;
import android.widget.TextView;

import com.example.myapplication.R;


public class RightChatViewHolder extends ChatViewHolder {

    public TextView contents;
    //public TextView time;

    public RightChatViewHolder(View itemView) {
        super(itemView);
        contents = (TextView) itemView.findViewById(R.id.chatItem_right_text);
       // time = (TextView) itemView.findViewById(R.id.chatItem_right_time);
    }
}