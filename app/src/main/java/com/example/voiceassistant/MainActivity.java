package com.example.voiceassistant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    protected Button sendButton;
    protected EditText userMessage;
    protected TextView chatWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendButton = findViewById(R.id.sendButton);
        userMessage = findViewById(R.id.userMessage);
        chatWindow = findViewById(R.id.chatWindow);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener();
            }
        });
    }

    protected void onClickListener() {
        String message = userMessage.getText().toString(); // То что у нас спрашивает пользователь
        userMessage.setText("");
        chatWindow.append("\n>> " + message);

        String answer = AI.getAnswer(message);

        chatWindow.append("\n<< " + answer);
    }
}
