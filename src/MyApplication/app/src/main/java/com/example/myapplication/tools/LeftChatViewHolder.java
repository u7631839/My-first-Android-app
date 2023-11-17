package com.example.myapplication.tools;

import android.view.View;
import android.widget.TextView;

import com.example.myapplication.R;

public class LeftChatViewHolder extends ChatViewHolder {

    public TextView contents;
  // public  TextView time;

    public LeftChatViewHolder(View itemView) {
        super(itemView);
        contents = (TextView) itemView.findViewById(R.id.chatItem_left_text);
    }
}
