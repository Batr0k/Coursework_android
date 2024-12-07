package com.example.coursework;

import androidx.lifecycle.ViewModel;

public class GrammarPracticeViewModel extends ViewModel {
    private int correctAnswers = 0;
    private int currentQuestionIndex = 0;

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void incrementCorrectAnswers() {
        correctAnswers++;
    }

    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    public void incrementCurrentQuestionIndex() {
        currentQuestionIndex++;
    }

    public void reset() {
        correctAnswers = 0;
        currentQuestionIndex = 0;
    }
}
