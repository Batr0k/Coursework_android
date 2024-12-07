package com.example.coursework;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class LearningWords extends AppCompatActivity {
    Button button1, button5, button9, button13, button17;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_learning_words);

        boolean isWord = getIntent().getBooleanExtra("isWords", false);
        button1 = findViewById(R.id.button1);
        button5 = findViewById(R.id.button5);
        button9 = findViewById(R.id.button9);
        button13 = findViewById(R.id.button13);
        button17 = findViewById(R.id.button17);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Создаем Intent для перехода к новой активности
                if (isWord) {
                    Intent intent = new Intent(LearningWords.this, dictionary.class);
                    intent.putExtra("DifficultyCategory", 1);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(LearningWords.this, GrammarPracticeActivity.class);
                    intent.putExtra("DifficultyCategory", 21);
                    startActivity(intent);
                }
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Создаем Intent для перехода к новой активности
                if (isWord) {
                    Intent intent = new Intent(LearningWords.this, dictionary.class);
                    intent.putExtra("DifficultyCategory", 5);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(LearningWords.this, GrammarPracticeActivity.class);
                    intent.putExtra("DifficultyCategory", 25);
                    startActivity(intent);
                }
            }
        });
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Создаем Intent для перехода к новой активности
                if (isWord) {
                    Intent intent = new Intent(LearningWords.this, dictionary.class);
                    intent.putExtra("DifficultyCategory", 9);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(LearningWords.this, GrammarPracticeActivity.class);
                    intent.putExtra("DifficultyCategory", 29);
                    startActivity(intent);
                }
            }
        });
        button13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Создаем Intent для перехода к новой активности
                if (isWord) {
                    Intent intent = new Intent(LearningWords.this, dictionary.class);
                    intent.putExtra("DifficultyCategory", 13);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(LearningWords.this, GrammarPracticeActivity.class);
                    intent.putExtra("DifficultyCategory", 33);
                    startActivity(intent);
                }
            }
        });
        button17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Создаем Intent для перехода к новой активности
                if (isWord) {
                    Intent intent = new Intent(LearningWords.this, dictionary.class);
                    intent.putExtra("DifficultyCategory", 17);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(LearningWords.this, GrammarPracticeActivity.class);
                    intent.putExtra("DifficultyCategory", 37);
                    startActivity(intent);
                }
            }
        });


    }

}