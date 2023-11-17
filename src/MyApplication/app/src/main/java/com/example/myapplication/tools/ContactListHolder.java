package com.example.myapplication.tools;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class ContactListHolder extends RecyclerView.ViewHolder {

    public TextView name;
    public TextView contents;
    public TextView time;
    public TextView sent_or_received;

    public ContactListHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.contact_name);
        contents = (TextView) itemView.findViewById(R.id.contact_message_contents);
        time = (TextView) itemView.findViewById(R.id.contact_message_time);
        sent_or_received = (TextView) itemView.findViewById(R.id.contact_sent_or_received);
    }
}