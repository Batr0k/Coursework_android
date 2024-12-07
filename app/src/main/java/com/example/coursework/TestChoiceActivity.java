package com.example.coursework;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TestChoiceActivity extends AppCompatActivity {
    ImageButton imageButton;
    TextView textView1;
    private DatabaseHelper dbHelper;
    ArrayList<String> ListOptions;
    private MediaPlayer mediaPlayer;
    List<String> allRussianWords;
    private RadioGroup radioGroupOptions;
    private RadioButton radioOption1, radioOption2, radioOption3, radioOption4;
    private Button btnNext, newButton;
    private TestChoiceViewModel viewModel;
    int isRuEng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_test_choice);

        // Получаем ViewModel
        viewModel = new ViewModelProvider(this).get(TestChoiceViewModel.class);
        imageButton = findViewById(R.id.imageButton);
        radioGroupOptions = findViewById(R.id.radioGroupOptions);
        radioOption1 = findViewById(R.id.radioOption1);
        radioOption2 = findViewById(R.id.radioOption2);
        radioOption3 = findViewById(R.id.radioOption3);
        radioOption4 = findViewById(R.id.radioOption4);
        ListOptions = new ArrayList<>();
//        viewModel.setListOptions(ListOptions);
        btnNext = findViewById(R.id.btnNext);
        dbHelper = new DatabaseHelper(this);

        int DifficultyCategory = getIntent().getIntExtra("DifficultyCategory", 0);
        ArrayList<Word> wordsList = getIntent().getParcelableArrayListExtra("words_list");
        isRuEng = getIntent().getIntExtra("isRuEng", 0);
        // Сохраняем список слов в ViewModel
        viewModel.setWordsList(wordsList);
        if (isRuEng == 1)  {
            allRussianWords = dbHelper.getEnglishWordsByDifficultyRange(1, 20);
        } else {
            // Получаем ссылку на LinearLayout
            LinearLayout mainLayout = findViewById(R.id.main);

            // Получаем ссылку на ImageButton
            ImageButton imageButton = findViewById(R.id.imageButton);

            // Создаем новую кнопку
            newButton = new Button(this);
            newButton.setText("Добавить слово в персональный словарь");
            newButton.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));

            // Получаем текущую позицию ImageButton в LinearLayout
            int imageButtonIndex = mainLayout.indexOfChild(imageButton);

            // Добавляем кнопку сразу после ImageButton
            mainLayout.addView(newButton, imageButtonIndex + 1);
            allRussianWords = dbHelper.getRussianWordsByEnglishDifficultyRange(1, 20);
        }
        textView1 = findViewById(R.id.textView1);

        loadQuestion();

        btnNext.setOnClickListener(view -> {
            // Проверка правильности ответа
            checkAnswer();

            // Переход к следующему вопросу, если они есть
            if (viewModel.getCurrentQuestionIndex() < wordsList.size() - 1) {
                viewModel.setCurrentQuestionIndex(viewModel.getCurrentQuestionIndex() + 1);
                loadQuestion();
            } else {
                // Если вопросы закончились, выводим результат
                Toast.makeText(TestChoiceActivity.this, "Процент правильных ответов = " +
                        (viewModel.getCorrectAnswers() / (double) wordsList.size()) * 100.0, Toast.LENGTH_SHORT).show();

                finish();
            }
        });
    }

    private void loadQuestion() {
        if (isRuEng != 1) {
            newButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dbHelper.addWordToPersonalDictionary(viewModel.getWordsList().get(viewModel.getCurrentQuestionIndex()).getWord());
                    Toast.makeText(TestChoiceActivity.this, "Слово добавлено в личный словарь", Toast.LENGTH_SHORT).show();

                }
            });
        }

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int resId = getResources().getIdentifier(viewModel.getWordsList().get(viewModel.getCurrentQuestionIndex()).getAudio(), "raw", getPackageName());

                if (resId != 0) {
                    // Воспроизведение аудио из raw
                    mediaPlayer = MediaPlayer.create(TestChoiceActivity.this, resId);
                    mediaPlayer.start();
                }
            }
        });
        // Устанавливаем текст вопроса
        textView1.setText("Выберите правильный перевод слова: " + viewModel.getWordsList().get(viewModel.getCurrentQuestionIndex()).getWord());

        ArrayList<String> ListIncorrect = new ArrayList<>();
        List<String> translations = Arrays.asList(viewModel.getWordsList().get(viewModel.getCurrentQuestionIndex()).getTranslations().split(", "));

        for (String item : allRussianWords) {
            if (!translations.contains(item)) {
                ListIncorrect.add(item);
            }
        }

        Random random = new Random();
//          viewModel.getListOptions().clear();
//          viewModel.getListOptions().add(ListIncorrect.remove(random.nextInt(ListIncorrect.size())));
//          viewModel.getListOptions().add(ListIncorrect.remove(random.nextInt(ListIncorrect.size())));
//          viewModel.getListOptions().add(ListIncorrect.remove(random.nextInt(ListIncorrect.size())));
//          viewModel.getListOptions().add(translations.get(random.nextInt(translations.size())));
//        Collections.shuffle(viewModel.getListOptions());
        ListOptions.clear();
        ListOptions.add(ListIncorrect.remove(random.nextInt(ListIncorrect.size())));
        ListOptions.add(ListIncorrect.remove(random.nextInt(ListIncorrect.size())));
        ListOptions.add(ListIncorrect.remove(random.nextInt(ListIncorrect.size())));
        ListOptions.add(translations.get(random.nextInt(translations.size())));
        Collections.shuffle(ListOptions);

        // Сохраняем опции в ViewModel, чтобы они не потерялись при повороте экрана
        viewModel.setListOptions(ListOptions);

        // Устанавливаем текст для каждой радиокнопки
        radioOption1.setText(viewModel.getListOptions().get(0));
        radioOption2.setText(viewModel.getListOptions().get(1));
        radioOption3.setText(viewModel.getListOptions().get(2));
        radioOption4.setText(viewModel.getListOptions().get(3));

        // Сбрасываем выбор радиокнопок
        radioGroupOptions.clearCheck();
    }

    private void checkAnswer() {
        // Получаем выбранный ответ
        int selectedOptionId = radioGroupOptions.getCheckedRadioButtonId();
        if (selectedOptionId != -1) { // Если выбран какой-то вариант
            RadioButton selectedOption = findViewById(selectedOptionId);
            String selectedAnswer = selectedOption.getText().toString();

            // Сравниваем с правильным ответом
            if (Arrays.asList(viewModel.getWordsList().get(viewModel.getCurrentQuestionIndex()).getTranslations().split(", ")).contains(selectedAnswer)) {
                viewModel.incrementCorrectAnswers();
                Toast.makeText(TestChoiceActivity.this, "Правильный ответ", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(TestChoiceActivity.this, "Неправильный ответ", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
