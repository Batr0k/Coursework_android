package com.example.coursework;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Word  implements Parcelable {
    private String word;
    private String translations;
    private String audio;
    public Word(String englishWord, String audio, String translations) {
        this.word = englishWord;
        this.audio = audio;
        this.translations = translations;
    }
    protected Word(Parcel in) {
        word = in.readString();
        audio = in.readString();
        translations = in.readString();
    }
    public static final Creator<Word> CREATOR = new Creator<Word>() {
        @Override
        public Word createFromParcel(Parcel in) {
            return new Word(in);
        }

        @Override
        public Word[] newArray(int size) {
            return new Word[size];
        }
    };
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(word);
        dest.writeString(audio);
        dest.writeString(translations);
    }

    @Override
    public int describeContents() {
        return 0;
    }
    public String getAudio() {
        return audio;
    }
    public String getWord() {
        return word;
    }

    public String getTranslations() {
        return translations;
    }
}

