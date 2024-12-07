package com.example.coursework;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class dictionary extends AppCompatActivity {
    Button button1, button2, button3, button4;
    private TextView textView;
    Intent intent;
    private RecyclerView recyclerView;
    private WordAdapter wordAdapter;
    private DatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);
        int DifficultyCategory = getIntent().getIntExtra("DifficultyCategory", 0);
        textView = findViewById(R.id.heading);
//        button2 = findViewById(R.id.button2);
//        button1 = findViewById(R.id.button1);
//        button3 = findViewById(R.id.button3);
//        button4 = findViewById(R.id.button4);
        dbHelper = new DatabaseHelper(this);
        textView.setText("Это набор слов номер " + DifficultyCategory);

        recyclerView = findViewById(R.id.recyclerView);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else {
            Log.e("DictionaryActivity", "RecyclerView is null");
        }

        // Создание списка слов
        List<Map<String, String>> engRuWords = dbHelper.getEnglishWordsWithTranslationsByDifficulty(DifficultyCategory);
        List<Map<String, String>> ruEngWords = dbHelper.getRussianWordsWithTranslationsByDifficulty(DifficultyCategory);
        List<Word> words1 = new ArrayList<>();
        List<Word> words2 = new ArrayList<>();
        for (Map<String, String> word : engRuWords) {
            words1.add(new Word(word.get("english_word"),word.get("audio_url"), word.get("translations")));
        }
        for (Map<String, String> word : ruEngWords) {
            words2.add(new Word(word.get("english_word"),word.get("audio_url"), word.get("translations")));
        }
        // Инициализация Spinner в активности
        Spinner spinner = findViewById(R.id.spinner);

// Создание списка опций для Spinner
        String[] testOptions = {
                "Выберите тест",
                "Англо-русский тест с выбором ответа",
                "Англо-русский тест с вводом ответа",
                "Русско-английский тест с выбором ответа",
                "Русско-английский тест с вводом ответа"
        };

// Создание адаптера для Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, testOptions);
        spinner.setSelection(0);
// Установка адаптера для Spinner
        spinner.setAdapter(adapter);

// Обработка выбора элемента
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch (position) {
                    case 1:
                        intent = new Intent(dictionary.this, TestChoiceActivity.class);
                intent.putParcelableArrayListExtra("words_list", new ArrayList<>(words1));
                intent.putExtra("DifficultyCategory", DifficultyCategory);
                startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(dictionary.this, TestActivity.class);
                intent.putParcelableArrayListExtra("words_list", new ArrayList<>(words1));
                intent.putExtra("DifficultyCategory", DifficultyCategory);
                startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(dictionary.this, TestChoiceActivity.class);
                intent.putParcelableArrayListExtra("words_list", new ArrayList<>(words2));
                intent.putExtra("isRuEng", 1);
                intent.putExtra("DifficultyCategory", DifficultyCategory);
                startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(dictionary.this, TestActivity.class);
                intent.putParcelableArrayListExtra("words_list", new ArrayList<>(words2));
                intent.putExtra("isRuEng", 1);
                intent.putExtra("DifficultyCategory", DifficultyCategory);
                startActivity(intent);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Действия, если ничего не выбрано
            }
        });

//        button2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Создаем Intent для перехода на другую активность
//                Intent intent = new Intent(dictionary.this, TestActivity.class);
//                intent.putParcelableArrayListExtra("words_list", new ArrayList<>(words1));
//                intent.putExtra("DifficultyCategory", DifficultyCategory);
//                startActivity(intent);
//            }
//        });
//        button1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Создаем Intent для перехода на другую активность
//                Intent intent = new Intent(dictionary.this, TestChoiceActivity.class);
//                intent.putParcelableArrayListExtra("words_list", new ArrayList<>(words1));
//                intent.putExtra("DifficultyCategory", DifficultyCategory);
//                startActivity(intent);
//            }
//        });
//        button3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Создаем Intent для перехода на другую активность
//                Intent intent = new Intent(dictionary.this, TestChoiceActivity.class);
//                intent.putParcelableArrayListExtra("words_list", new ArrayList<>(words2));
//                intent.putExtra("isRuEng", 1);
//                intent.putExtra("DifficultyCategory", DifficultyCategory);
//                startActivity(intent);
//            }
//        });
//        button4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Создаем Intent для перехода на другую активность
//                Intent intent = new Intent(dictionary.this, TestActivity.class);
//                intent.putParcelableArrayListExtra("words_list", new ArrayList<>(words2));
//                intent.putExtra("isRuEng", 1);
//                intent.putExtra("DifficultyCategory", DifficultyCategory);
//                startActivity(intent);
//            }
//        });
        wordAdapter = new WordAdapter(words1);
        recyclerView.setAdapter(wordAdapter);
    }
}
