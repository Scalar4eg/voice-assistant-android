package com.example.voiceassistant;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;
import java.util.function.Consumer;


public class MainActivity extends AppCompatActivity {

    protected Button sendButton;
    protected EditText userMessage;
    protected RecyclerView chatWindow;
    protected TextToSpeech tts;

    protected MessageController messageController;


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

        messageController = new MessageController();
        chatWindow.setLayoutManager(new LinearLayoutManager(this));
        chatWindow.setAdapter(messageController);


        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
               tts.setLanguage(new Locale("ru"));
            }
        });

    }

    protected void onClickListener() {
        String message = userMessage.getText().toString(); // То что у нас спрашивает пользователь
        userMessage.setText("");

        messageController.messageList.add(new Message(message, true));
        AI.getAnswer(message, new Consumer<String>() {
            @Override
            public void accept(String answer) {
                messageController.messageList.add(new Message(answer, false));
                tts.speak(answer, TextToSpeech.QUEUE_FLUSH, null, null);
                messageController.notifyDataSetChanged();
                chatWindow.scrollToPosition(messageController.messageList.size() - 1);

            }
        });
    }
}
