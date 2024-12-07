package com.example.coursework;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DashboardActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private CardView cardWordStudy, cardGrammarTheory, cardGrammarPractice, cardPersonalDictionary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);

        dbHelper = new DatabaseHelper(this);
        if (!dbHelper.hasEnglishWords()) {
            insertDifficultyCategories();
            insertWords();
        }

//        dbHelper.insertPersonalDictionary(1);
        cardGrammarPractice =  findViewById(R.id.card_grammar_practice);
        cardWordStudy = findViewById(R.id.card_word_study);
        cardPersonalDictionary = findViewById(R.id.card_personal_dictionary);
        cardGrammarTheory = findViewById(R.id.card_grammar_theory);
        cardGrammarPractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Создаем Intent для перехода к новой активности
                Intent intent = new Intent(DashboardActivity.this, LearningWords.class);
                intent.putExtra("isWords", false);
                startActivity(intent);
            }
        });
        cardPersonalDictionary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Создаем Intent для перехода к новой активности
                Intent intent = new Intent(DashboardActivity.this, PersonalDictionaryActivity.class);
                intent.putExtra("isWords", false);
                startActivity(intent);
            }
        });
        cardGrammarTheory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Создаем Intent для перехода к новой активности
                Intent intent = new Intent(DashboardActivity.this, GrammarTheoryActivity.class);
                startActivity(intent);
            }
        });
        // Устанавливаем обработчик клика на карточку "Изучение слов"
        cardWordStudy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Создаем Intent для перехода к новой активности
                Intent intent = new Intent(DashboardActivity.this, LearningWords.class);
                intent.putExtra("isWords", true);
                startActivity(intent);
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    // Метод для вставки 20 категорий сложности в базу данных
    private void insertDifficultyCategories() {

        for (int i = 1; i <= 40; i++) {
            dbHelper.insertDifficultyCategory(i);
        }
    }
    private void insertWords() {
        // ПЕРВАЯ КАТЕГОРИЯ
        // Вставка английского слова "same"
        long englishWordId, russianWordId;
        englishWordId = dbHelper.insertEnglishWord("same", "same", 1);
        russianWordId = dbHelper.insertRussianWord("тот же");
        dbHelper.insertDictionaryRelation(englishWordId, russianWordId);
        russianWordId = dbHelper.insertRussianWord("одинаковый");
        dbHelper.insertDictionaryRelation(englishWordId, russianWordId);

        // Вставка английского слова "no"
        englishWordId = dbHelper.insertEnglishWord("no", "no", 1);
        russianWordId = dbHelper.insertRussianWord("нет");
        dbHelper.insertDictionaryRelation(englishWordId, russianWordId);

        englishWordId = dbHelper.insertEnglishWord("said", "said", 1);
        russianWordId = dbHelper.insertRussianWord("указанный");
        dbHelper.insertDictionaryRelation(englishWordId, russianWordId);

        englishWordId = dbHelper.insertEnglishWord("as", "as", 1);
        russianWordId = dbHelper.insertRussianWord("как");
        dbHelper.insertDictionaryRelation(englishWordId, russianWordId);

        englishWordId = dbHelper.insertEnglishWord("use", "use", 1);
        russianWordId = dbHelper.insertRussianWord("использовать");
        dbHelper.insertDictionaryRelation(englishWordId, russianWordId);

        englishWordId = dbHelper.insertEnglishWord("people", "people", 1);
        russianWordId = dbHelper.insertRussianWord("люди");
        dbHelper.insertDictionaryRelation(englishWordId, russianWordId);

        englishWordId = dbHelper.insertEnglishWord("and", "and", 1);
        russianWordId = dbHelper.insertRussianWord("и");
        dbHelper.insertDictionaryRelation(englishWordId, russianWordId);

        // Вставка английского слова "also"
        englishWordId = dbHelper.insertEnglishWord("also", "also", 1);
        russianWordId = dbHelper.insertRussianWord("так же");
        dbHelper.insertDictionaryRelation(englishWordId, russianWordId);

        // Вставка английского слова "now"
        englishWordId = dbHelper.insertEnglishWord("now", "now", 1);
        russianWordId = dbHelper.insertRussianWord("сейчас");
        dbHelper.insertDictionaryRelation(englishWordId, russianWordId);

        // Вставка английского слова "such"
        englishWordId = dbHelper.insertEnglishWord("such", "such", 1);
        russianWordId = dbHelper.insertRussianWord("такой");
        dbHelper.insertDictionaryRelation(englishWordId, russianWordId);
        // ПЯТАЯ КАТЕГОРИЯ
        englishWordId = dbHelper.insertEnglishWord("Persian", "persian", 5);
        russianWordId = dbHelper.insertRussianWord("персидский");
        dbHelper.insertDictionaryRelation(englishWordId, russianWordId);
        englishWordId = dbHelper.insertEnglishWord("bodily", "bodily", 5);
        russianWordId = dbHelper.insertRussianWord("телесный");
        dbHelper.insertDictionaryRelation(englishWordId, russianWordId);
        englishWordId = dbHelper.insertEnglishWord("coherent","coherent", 5);
        russianWordId = dbHelper.insertRussianWord("последовательный");
        dbHelper.insertDictionaryRelation(englishWordId, russianWordId);
        // Девятая категория
        englishWordId = dbHelper.insertEnglishWord("discomfort","discomfort", 9 );
        russianWordId = dbHelper.insertRussianWord("дискомфорт");
        dbHelper.insertDictionaryRelation(englishWordId, russianWordId);
        englishWordId = dbHelper.insertEnglishWord("nutrient","nutrient", 9 );
        russianWordId = dbHelper.insertRussianWord("питательное вещество");
        dbHelper.insertDictionaryRelation(englishWordId, russianWordId);
        englishWordId = dbHelper.insertEnglishWord("rim","rim", 9 );
        russianWordId = dbHelper.insertRussianWord("обод");
        dbHelper.insertDictionaryRelation(englishWordId, russianWordId);
        // Тринадцатая категория
        englishWordId = dbHelper.insertEnglishWord("witchcraft","witchcraft", 13 );
        russianWordId = dbHelper.insertRussianWord("колдовство");
        dbHelper.insertDictionaryRelation(englishWordId, russianWordId);
        englishWordId = dbHelper.insertEnglishWord("moonlight","moonlight", 13 );
        russianWordId = dbHelper.insertRussianWord("лунный свет");
        dbHelper.insertDictionaryRelation(englishWordId, russianWordId);
        englishWordId = dbHelper.insertEnglishWord("cessation","cessation", 13 );
        russianWordId = dbHelper.insertRussianWord("прекращение");
        dbHelper.insertDictionaryRelation(englishWordId, russianWordId);
        // Семнадцатая категория
        englishWordId = dbHelper.insertEnglishWord("deciduous","deciduous", 17 );
        russianWordId = dbHelper.insertRussianWord("лиственный");
        dbHelper.insertDictionaryRelation(englishWordId, russianWordId);
        englishWordId = dbHelper.insertEnglishWord("foresee","foresee", 17 );
        russianWordId = dbHelper.insertRussianWord("предвидеть");
        dbHelper.insertDictionaryRelation(englishWordId, russianWordId);
        englishWordId = dbHelper.insertEnglishWord("eradicate","eradicate", 17 );
        russianWordId = dbHelper.insertRussianWord("искоренять");
        dbHelper.insertDictionaryRelation(englishWordId, russianWordId);
        // Фразы
        englishWordId = dbHelper.insertEnglishWord("I will be here", "i_will_be_here", 21);
        russianWordId = dbHelper.insertRussianWord("Я буду здесь");
        dbHelper.insertDictionaryRelation(englishWordId, russianWordId);
        englishWordId = dbHelper.insertEnglishWord("He doesn't come", "he_doesnt_come", 21);
        russianWordId = dbHelper.insertRussianWord("Он не приходит");
        dbHelper.insertDictionaryRelation(englishWordId, russianWordId);
        englishWordId = dbHelper.insertEnglishWord("He works as an engineer", "he_works_as_an_engineer", 21);
        russianWordId = dbHelper.insertRussianWord("Он работает инженером");
        dbHelper.insertDictionaryRelation(englishWordId, russianWordId);
        englishWordId = dbHelper.insertEnglishWord("I was busy this month", "i_was_busy_this_month", 21);
        russianWordId = dbHelper.insertRussianWord("Я был занят в этом месяце");
        dbHelper.insertDictionaryRelation(englishWordId, russianWordId);
        englishWordId = dbHelper.insertEnglishWord("Who saw you", "who_saw_you", 21);
        russianWordId = dbHelper.insertRussianWord("Кто тебя видел");
        dbHelper.insertDictionaryRelation(englishWordId, russianWordId);

        // 5
        englishWordId = dbHelper.insertEnglishWord("I have plenty of time", "i_have_plenty_of_time", 25);
        russianWordId = dbHelper.insertRussianWord("У меня уйма времени");
        dbHelper.insertDictionaryRelation(englishWordId, russianWordId);

        englishWordId = dbHelper.insertEnglishWord("This article was written by tom", "this_article_was_written_by_tom", 25);
        russianWordId = dbHelper.insertRussianWord("Эта статья была написана Томом");
        dbHelper.insertDictionaryRelation(englishWordId, russianWordId);

        englishWordId = dbHelper.insertEnglishWord("What have you done with my bag", "what_have_you_done_with_my_bag", 25);
        russianWordId = dbHelper.insertRussianWord("Что вы сделали с моей сумкой");
        dbHelper.insertDictionaryRelation(englishWordId, russianWordId);

        // 9

        englishWordId = dbHelper.insertEnglishWord("He gave a little money", "he_gave_a_little_money", 29);
        russianWordId = dbHelper.insertRussianWord("Он дал немного денег");
        dbHelper.insertDictionaryRelation(englishWordId, russianWordId);

        englishWordId = dbHelper.insertEnglishWord("He was there", "he_was_there", 29);
        russianWordId = dbHelper.insertRussianWord("Он был там");
        dbHelper.insertDictionaryRelation(englishWordId, russianWordId);

        englishWordId = dbHelper.insertEnglishWord("Let me try", "let_me_try", 29);
        russianWordId = dbHelper.insertRussianWord("Дайте мне попробовать");
        dbHelper.insertDictionaryRelation(englishWordId, russianWordId);

        // 13
        englishWordId = dbHelper.insertEnglishWord("Was he happy", "was_he_happy", 33);
        russianWordId = dbHelper.insertRussianWord("Он был счастлив?");
        dbHelper.insertDictionaryRelation(englishWordId, russianWordId);

        englishWordId = dbHelper.insertEnglishWord("You mustn't lie", "you_mustnt_lie", 33);
        russianWordId = dbHelper.insertRussianWord("Ты не должен лгать");
        dbHelper.insertDictionaryRelation(englishWordId, russianWordId);

        englishWordId = dbHelper.insertEnglishWord("There is a tv in the room", "there_is_a_tv_in_the_room", 33);
        russianWordId = dbHelper.insertRussianWord("В комнате есть телевизор");
        dbHelper.insertDictionaryRelation(englishWordId, russianWordId);

        // 17
        englishWordId = dbHelper.insertEnglishWord("Do you work as a manager in a cafe", "do_you_work_as_a_manager_in_a_cafe", 37);
        russianWordId = dbHelper.insertRussianWord("Ты работаешь менеджером в кафе?");
        dbHelper.insertDictionaryRelation(englishWordId, russianWordId);

        englishWordId = dbHelper.insertEnglishWord("You have a unique chance to see his works", "you_have_a_unique_chance_to_see_his_works", 37);
        russianWordId = dbHelper.insertRussianWord("У вас есть уникальный шанс увидеть его работы");
        dbHelper.insertDictionaryRelation(englishWordId, russianWordId);

        englishWordId = dbHelper.insertEnglishWord("Few people think so", "few_people_think_so", 37);
        russianWordId = dbHelper.insertRussianWord("Мало людей думают так");
        dbHelper.insertDictionaryRelation(englishWordId, russianWordId);

    }

}