package com.example.voiceassistant;

import android.annotation.TargetApi;
import android.os.Build;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AI {
    @TargetApi(Build.VERSION_CODES.O)
    public static void getAnswer(String user_question, final Consumer<String> callback) {

        Map<String, String> database = new HashMap<String, String>() {{
            put("привет", "И вам здрасте");
            put("как дела", "Да вроде ничего");
            put("чем занимаешься", "Отвечаю на дурацкие вопросы");
            put("как тебя зовут", "Я - голосовой помошник Иннокентий");
            put("кто тебя создал", "Миша из Скиллбокса меня создал");

            put("Кто сейчас президент России", "Посмотри по телевизору, я не в курсе");
            put("Какого цвета небо", "Вроде с утра было синее");
            put("Есть ли жизнь на марсе", "Есть, но она об этом не знает");
        }};

        user_question = user_question.toLowerCase();

        final ArrayList<String> answers = new ArrayList<>();

        int max_score = 0;
        String max_score_answer = "Окей";
        String[] split_user = user_question.split("\\s+");

        for (String database_question : database.keySet()) {
              database_question = database_question.toLowerCase();
              String[] split_db = database_question.split("\\s+");
              int score = 0;
              for (String word_user : split_user) {
                for (String word_db : split_db) {
                    int min_len = Math.min(word_db.length(), word_user.length());
                    int cut_len = (int) (min_len * 0.7);
                    String word_user_cut = word_user.substring(0, cut_len);
                    String word_db_cut = word_db.substring(0, cut_len);
                    if (word_user_cut.equals(word_db_cut)) {
                        score++;
                    }
                }
              }
              if (score > max_score) {
                  max_score = score;
                  max_score_answer = database.get(database_question);
              }
        }

        if (max_score > 0) {
            answers.add(max_score_answer);
        }

        Pattern cityPattern = Pattern.compile("какая погода в городе (\\p{L}+)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = cityPattern.matcher(user_question);
        if (matcher.find()) {
            String cityName = matcher.group(1);
            Weather.get(cityName, new Consumer<String>() {
                @Override
                public void accept(String s) {
                    answers.add(s);
                    callback.accept(String.join(", ", answers));
                }
            });
        } else {
            if (answers.isEmpty()) {
                callback.accept("Ок");
                return;
            }
            callback.accept(String.join(", ", answers));
        }
    }
}
