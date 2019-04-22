package com.example.voiceassistant;

import android.annotation.TargetApi;
import android.os.Build;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AI {
    @TargetApi(Build.VERSION_CODES.O)
    public static String getAnswer(String user_question) {

        Map<String, String> database = new HashMap<String, String>() {{
            put("привет", "И вам здрасте");
            put("как дела", "Да вроде ничего");
            put("чем занимаешься", "Отвечаю на дурацкие вопросы");
            put("как тебя зовут", "Я - голосовой помошник Иннокентий");
            put("кто тебя создал", "Миша из Скиллбокса меня создал");
        }};

        user_question = user_question.toLowerCase();

        ArrayList<String> answers = new ArrayList<>();

        for (String database_question : database.keySet()) {
            if (user_question.contains(database_question)) {
                answers.add(database.get(database_question));
            }
        }


        if (answers.isEmpty()) {
            return "Ок";
        }
        return String.join(", ", answers);
    }
}
