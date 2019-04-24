package com.example.voiceassistant;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class MessageView extends RecyclerView.ViewHolder {
    TextView messageText;
    TextView messageTime;


    public MessageView(View itemView) {
        super(itemView);
        messageText = itemView.findViewById(R.id.messageText);
        messageTime = itemView.findViewById(R.id.messageTime);
    }

    public void bind(Message message) {
        messageText.setText(message.text);
        DateFormat fmt = new SimpleDateFormat("HH:mm");
        messageTime.setText(fmt.format(message.date));
    }
}
