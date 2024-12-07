package com.example.coursework;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PersonalDictionaryActivity extends AppCompatActivity {
    private WordAdapter wordAdapter;
    private DatabaseHelper dbHelper;
    private RecyclerView recyclerView;
    List<Map<String, String>> words_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_personal_dictionary);
        dbHelper = new DatabaseHelper(this);
        words_list = dbHelper.getEnglishWordsWithTranslationsFromPersonalDictionary();
        List<Word> words = new ArrayList<>();
        for (Map<String, String> word : words_list) {
            words.add(new Word(word.get("english_word"),word.get("audio_url"), word.get("translations")));
        }
        recyclerView = findViewById(R.id.recyclerView);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else {
            Log.e("DictionaryActivity", "RecyclerView is null");
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        wordAdapter = new WordAdapter(words);
        recyclerView.setAdapter(wordAdapter);
    }

}