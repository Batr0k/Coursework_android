package com.example.coursework;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class TestChoiceViewModel extends ViewModel {
    private int currentQuestionIndex = 0;
    private int correctAnswers = 0;
    private ArrayList<Word> wordsList;
    private ArrayList<String> listOptions; // Хранение опций для радиокнопок

    public TestChoiceViewModel() {
        listOptions = new ArrayList<>();
    }

    // Получить текущий индекс вопроса
    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    // Установить текущий индекс вопроса
    public void setCurrentQuestionIndex(int index) {
        currentQuestionIndex = index;
    }

    // Получить количество правильных ответов
    public int getCorrectAnswers() {
        return correctAnswers;
    }

    // Установить количество правильных ответов
    public void incrementCorrectAnswers() {
        correctAnswers++;
    }

    // Получить список слов
    public ArrayList<Word> getWordsList() {
        return wordsList;
    }

    // Установить список слов
    public void setWordsList(ArrayList<Word> list) {
        wordsList = list;
    }

    // Получить опции для радиокнопок
    public ArrayList<String> getListOptions() {
        return listOptions;
    }

    // Установить опции для радиокнопок
    public void setListOptions(ArrayList<String> options) {
        listOptions = options;
    }
}
