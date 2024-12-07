package com.example.coursework;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestActivity extends AppCompatActivity {
    TextView questionText;
    EditText editText;
    private DatabaseHelper dbHelper;
    String enteredWord;
    private MediaPlayer mediaPlayer;
    Button button, newButton;
    ImageButton imageButton;
    int correctAnswers, wordsSize, isRuEng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        questionText = findViewById(R.id.questionText);
        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);
        dbHelper = new DatabaseHelper(this);
        correctAnswers = 0;
        imageButton = findViewById(R.id.imageButton);
        int DifficultyCategory = getIntent().getIntExtra("DifficultyCategory", 0);
        ArrayList<Word> wordsList = getIntent().getParcelableArrayListExtra("words_list");
        isRuEng = getIntent().getIntExtra("isRuEng", 0);
        if (isRuEng == 0) {
            // Получаем ссылку на LinearLayout
            LinearLayout mainLayout = findViewById(R.id.main);

            // Получаем ссылку на ImageButton
            ImageButton imageButton = findViewById(R.id.imageButton);

            // Создаем новую кнопку
            newButton = new Button(this);
            newButton.setText("Добавить слово в персональный словарь");
            newButton.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));



            // Получаем текущую позицию ImageButton в LinearLayout
            int imageButtonIndex = mainLayout.indexOfChild(imageButton);

            // Добавляем кнопку сразу после ImageButton
            mainLayout.addView(newButton, imageButtonIndex + 1);
            newButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dbHelper.addWordToPersonalDictionary(wordsList.get(0).getWord());
                    Toast.makeText(TestActivity.this, "Слово добавлено в личный словарь", Toast.LENGTH_SHORT).show();
                }
            });

        }
        wordsSize = wordsList.size();
        questionText.setText("Введите перевод слова: " + wordsList.get(0).getWord());

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int resId = getResources().getIdentifier(wordsList.get(0).getAudio(), "raw", getPackageName());

                if (resId != 0) {
                    // Воспроизведение аудио из raw
                    mediaPlayer = MediaPlayer.create(TestActivity.this, resId);
                    mediaPlayer.start();
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enteredWord = editText.getText().toString();
                String[] wordsArray = wordsList.get(0).getTranslations().split(", ");
                List<String> translationList = new ArrayList<>(Arrays.asList(wordsArray));

                if (translationList.contains(enteredWord)) {
                    correctAnswers++;
                    Toast.makeText(TestActivity.this, "Правильный ответ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TestActivity.this, "Неправильный ответ", Toast.LENGTH_SHORT).show();
                }

                wordsList.remove(0);

                // Check if wordsList is empty before proceeding
                if (wordsList.isEmpty()) {
                    Toast.makeText(TestActivity.this, "Процент правильных ответов = " + (correctAnswers / (double) wordsSize) * 100.0, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    editText.setText("");
                    questionText.setText("Введите перевод слова: " + wordsList.get(0).getWord());

                    // Re-initialize the imageButton click listener for the next word
                    imageButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int resId = getResources().getIdentifier(wordsList.get(0).getAudio(), "raw", getPackageName());

                            if (resId != 0) {
                                // Воспроизведение аудио из raw
                                mediaPlayer = MediaPlayer.create(TestActivity.this, resId);
                                mediaPlayer.start();
                            }
                        }
                    });
                }
            }
        });

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }


}
