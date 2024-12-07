package com.example.coursework;



import android.media.MediaPlayer;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GrammarPracticeActivity extends AppCompatActivity {
    private TextView textView;
    ImageButton imageButton;
    private LinearLayout wordsLayout;
    private MediaPlayer mediaPlayer;
    private Button submitButton;
    ArrayList<String> userOrder;
    private List<String> correctOrder = new ArrayList<>();
    private List<Button> buttons = new ArrayList<>();
    int DifficultyCategory, currentQuestionIndex = 0, correctAnswers = 0;
    private DatabaseHelper dbHelper;
    List<Map<String, String>> words;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grammar_practice);
        wordsLayout = findViewById(R.id.wordsLayout);
        submitButton = findViewById(R.id.submitButton);
        imageButton = findViewById(R.id.imageButton);
        textView = findViewById(R.id.textView);
        userOrder = new ArrayList<>();
        DifficultyCategory = getIntent().getIntExtra("DifficultyCategory", 0);
        dbHelper = new DatabaseHelper(this);
        words = dbHelper.getEnglishWordsWithTranslationsByDifficulty(DifficultyCategory);

        loadQuestion();

        // Обработчик нажатия на кнопку "Проверить"
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Проверяем, совпадает ли порядок, выбранный пользователем, с правильным
                if (userOrder.equals(correctOrder)) {
                    Toast.makeText(GrammarPracticeActivity.this, "Правильный ответ", Toast.LENGTH_SHORT).show();
                    correctAnswers++;
                    currentQuestionIndex++;
                    userOrder.clear();
                    loadQuestion();
                } else {
                    Toast.makeText(GrammarPracticeActivity.this, "Неправильный ответ", Toast.LENGTH_SHORT).show();
                    userOrder.clear();
                    currentQuestionIndex++;
                    loadQuestion();
                }
            }
        });
    }

    private void loadQuestion() {
        // Очистить старые кнопки
        wordsLayout.removeAllViews();

        // Проверяем, если все вопросы закончились
        if (currentQuestionIndex >= words.size()) {
            // Если вопросы закончились, выводим результат
            double percentage = ((double) correctAnswers / words.size()) * 100.0;
            Toast.makeText(GrammarPracticeActivity.this, "Процент правильных ответов = " + percentage, Toast.LENGTH_SHORT).show();
            finish();
            return; // Прекращаем выполнение кода, если вопросы закончились
        }
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int resId = getResources().getIdentifier(words.get(currentQuestionIndex).get("audio_url"), "raw", getPackageName());

                if (resId != 0) {
                    // Воспроизведение аудио из raw
                    mediaPlayer = MediaPlayer.create(GrammarPracticeActivity.this, resId);
                    mediaPlayer.start();
                }
            }
        });
        textView.setText(words.get(currentQuestionIndex).get("translations"));

        // Очищаем правильный порядок для текущего вопроса
        correctOrder.clear();

        // Разбиваем слова на массив и добавляем в correctOrder
        for (String word : words.get(currentQuestionIndex).get("english_word").split(" ")) {
            correctOrder.add(word.trim());
        }

        // Создаем кнопки для каждого слова
        buttons.clear(); // Очищаем список кнопок перед добавлением новых

        for (String word : words.get(currentQuestionIndex).get("english_word").split(" ")) {
            Button button = new Button(this);
            button.setText(word);
            button.setTag(word.trim());

            final boolean[] isSelected = {false};

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isSelected[0]) {
                        button.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
                        isSelected[0] = true;
                        userOrder.add(button.getText().toString());
                    } else {
                        button.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                        isSelected[0] = false;
                        userOrder.remove(button.getText().toString());
                    }
                }
            });

            buttons.add(button);
        }

        // Перемешиваем кнопки
        Collections.shuffle(buttons);

        // Добавляем кнопки в layout
        for (Button button : buttons) {
            wordsLayout.addView(button);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Сохраняем состояние переменных
        outState.putStringArrayList("userOrder", userOrder);
        outState.putInt("currentQuestionIndex", currentQuestionIndex);
        outState.putInt("correctAnswers", correctAnswers);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Восстанавливаем состояние переменных
        userOrder = savedInstanceState.getStringArrayList("userOrder");
        currentQuestionIndex = savedInstanceState.getInt("currentQuestionIndex");
        correctAnswers = savedInstanceState.getInt("correctAnswers");

        // Загружаем вопрос после восстановления состояния
        loadQuestion();
    }

}