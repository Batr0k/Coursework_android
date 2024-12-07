package com.example.coursework;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GrammarTheoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_grammar_theory);
        func1();
        func2();
        func3();
        func4();
        func5();
        func6();
        func7();
        func8();
        func9();
    }
    void func1() {

        // TextView для основного текста
        TextView bodyTextView = findViewById(R.id.bodyTextView);

        String bodyText = "Утверждение в настоящем времени.\nС глаголами в утвердительных предложениях всё очень просто, мы используем глагол в его словарной форме. Но есть один нюанс, если мы используем местоимения he (он), she (она) или it (это), то на конце глагола добавляется буква - s.";

        // Создаем SpannableString для форматирования основного текста
        SpannableString spannable = new SpannableString(bodyText);

        // Зелёный цвет для слова "утверждение"
        ForegroundColorSpan greenColor = new ForegroundColorSpan(Color.GREEN);
        spannable.setSpan(greenColor, bodyText.indexOf("Утверждение"), bodyText.indexOf("Утверждение") + 11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Красный цвет для буквы "s"
        ForegroundColorSpan redColor = new ForegroundColorSpan(Color.RED);
        spannable.setSpan(redColor, bodyText.lastIndexOf("s"), bodyText.lastIndexOf("s") + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Устанавливаем отформатированный текст в TextView
        bodyTextView.setText(spannable);
    }
    void func2() {
        // TextView для основного текста
        TextView bodyTextView = findViewById(R.id.textView3);

        String bodyText = "Отрицание в настоящем времени\nОтрицательные фразы в английском языке строятся с помощью местоимения, вспомогательного глагола do (does) и частицы not, затем идет основной глагол, который мы хотим использовать в фразе.\n" +
                "С местоимениями I (я), you (вы), we (мы), they (они) используется вспомогательный глагол - do not сокращёно don’t.\n" +
                "С местоимениями he (он), she (она), it (это) используется другая форма глагола - does not сокращёно doesn’t.";

        // Создаем SpannableString для форматирования основного текста
        SpannableString spannable = new SpannableString(bodyText);

        // Красный цвет для слова "Отрицание"
        ForegroundColorSpan redColor = new ForegroundColorSpan(Color.RED);
        spannable.setSpan(redColor, bodyText.indexOf("Отрицание"), bodyText.indexOf("Отрицание") + 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        // Устанавливаем отформатированный текст в TextView
        bodyTextView.setText(spannable);
    }
    void func3() {
        TextView bodyTextView = findViewById(R.id.textView4);

        String bodyText = "Вопрос в настоящем времени\nВопросительные предложения строятся с теми же глаголами do или does без частицы not, но для того, чтобы задать вопрос, потребуется поставить вспомогательный глагол в самое начало.\n" +
                "С местоимениями I (я), you (вы), we (мы), they (они) используется вспомогательный глагол - do.\n" +
                "С местоимениями he (он), she (она), it (это) используется другая форма глагола - does.";

        // Создаем SpannableString для форматирования основного текста
        SpannableString spannable = new SpannableString(bodyText);

        // Оранжевый цвет для слова "вопрос"
        ForegroundColorSpan orangeColor = new ForegroundColorSpan(Color.parseColor("#FFA500")); // Оранжевый цвет
        spannable.setSpan(orangeColor, bodyText.indexOf("Вопрос"), bodyText.indexOf("Вопрос") + 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Устанавливаем отформатированный текст в TextView
        bodyTextView.setText(spannable);
    }
    void func4() {
        TextView TextView5 = findViewById(R.id.textView5);
        String bodyText = "Утверждение в прошедшем времени\n" +
                "\n" +
                "C утверждением в прошедшем времени все было бы просто, но все портит тот факт, что существуют правильные и неправильные глаголы, формы прошедшего времени которых различаются сильно. Правильные глаголы в прошедшем времени имеют окончание -ed. Неправильные глаголы представлены в таблице, форму прошедшего времени нужно смотреть во второй колонке, а впоследствии выучить наизусть. Хорошая новость в том, что используются данные формы только в утвердительных предложениях прошедшего времени.";
        // Создаем SpannableString для форматирования основного текста
        SpannableString spannable = new SpannableString(bodyText);

        // Зелёный цвет для слова "утверждение"
        ForegroundColorSpan greenColor = new ForegroundColorSpan(Color.GREEN);
        spannable.setSpan(greenColor, bodyText.indexOf("Утверждение"), bodyText.indexOf("Утверждение") + 11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Устанавливаем отформатированный текст в TextView
        TextView5.setText(spannable);
    }
    void func5() {
        // TextView для основного текста
        TextView bodyTextView = findViewById(R.id.textView6);

        String bodyText = "Отрицание в прошедшем времени\n" +
                "\n" +
                "Отрицательные фразы в прошедшем времени образуются с помощью вспомогательного глагола did и частицы not (сокращенно didn’t), далее следует основной глагол.";

        // Создаем SpannableString для форматирования основного текста
        SpannableString spannable = new SpannableString(bodyText);

        // Красный цвет для слова "Отрицание"
        ForegroundColorSpan redColor = new ForegroundColorSpan(Color.RED);
        spannable.setSpan(redColor, bodyText.indexOf("Отрицание"), bodyText.indexOf("Отрицание") + 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        // Устанавливаем отформатированный текст в TextView
        bodyTextView.setText(spannable);
    }
    void func6() {
        TextView bodyTextView = findViewById(R.id.textView7);

        String bodyText = "Вопрос в прошедшем времени\n" +
                "\n" +
                "Вопросы в прошедшем времени образуются с тем же вспомогательным глаголом did, который, как и в любых вопросах, ставится в начале фразы. Для образования вопроса, содержащего отрицание, после did ставится частица not, сокращенно didn’t.";

        // Создаем SpannableString для форматирования основного текста
        SpannableString spannable = new SpannableString(bodyText);

        // Оранжевый цвет для слова "вопрос"
        ForegroundColorSpan orangeColor = new ForegroundColorSpan(Color.parseColor("#FFA500")); // Оранжевый цвет
        spannable.setSpan(orangeColor, bodyText.indexOf("Вопрос"), bodyText.indexOf("Вопрос") + 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Устанавливаем отформатированный текст в TextView
        bodyTextView.setText(spannable);
    }
    void func7() {

        // TextView для основного текста
        TextView bodyTextView = findViewById(R.id.textView8);

        String bodyText = "Утверждение в будушем времени\n" +
                "\n" +
                "C утверждением в будущем времени английского языка тоже всё просто, образуются такие фразы с помощью специального глагола will (будет или буду).";

        // Создаем SpannableString для форматирования основного текста
        SpannableString spannable = new SpannableString(bodyText);

        // Зелёный цвет для слова "утверждение"
        ForegroundColorSpan greenColor = new ForegroundColorSpan(Color.GREEN);
        spannable.setSpan(greenColor, bodyText.indexOf("Утверждение"), bodyText.indexOf("Утверждение") + 11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        // Устанавливаем отформатированный текст в TextView
        bodyTextView.setText(spannable);
    }
    void func8() {
        // TextView для основного текста
        TextView bodyTextView = findViewById(R.id.textView9);

        String bodyText = "Отрицание в будущем времени\n" +
                "\n" +
                "Отрицательные фразы в будущем времени образуются с помощью  глагола will и частицы not, далее следует основной глагол.";

        // Создаем SpannableString для форматирования основного текста
        SpannableString spannable = new SpannableString(bodyText);

        // Красный цвет для слова "Отрицание"
        ForegroundColorSpan redColor = new ForegroundColorSpan(Color.RED);
        spannable.setSpan(redColor, bodyText.indexOf("Отрицание"), bodyText.indexOf("Отрицание") + 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        // Устанавливаем отформатированный текст в TextView
        bodyTextView.setText(spannable);
    }
    void func9() {
        TextView bodyTextView = findViewById(R.id.textView10);

        String bodyText = "Вопрос в будущем времени\n" +
                "\n" +
                "Вопросы в будущем образуются с тем же глаголом will, который ставится в начало предложения. Ну а если это вопрос с отрицанием, то за ним идет not.";

        // Создаем SpannableString для форматирования основного текста
        SpannableString spannable = new SpannableString(bodyText);

        // Оранжевый цвет для слова "вопрос"
        ForegroundColorSpan orangeColor = new ForegroundColorSpan(Color.parseColor("#FFA500")); // Оранжевый цвет
        spannable.setSpan(orangeColor, bodyText.indexOf("Вопрос"), bodyText.indexOf("Вопрос") + 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Устанавливаем отформатированный текст в TextView
        bodyTextView.setText(spannable);
    }

}
