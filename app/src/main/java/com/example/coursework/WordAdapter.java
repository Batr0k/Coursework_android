package com.example.coursework;

import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.io.IOException;
import java.util.List;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordViewHolder> {

    private List<Word> wordList;
    private MediaPlayer mediaPlayer;

    public WordAdapter(List<Word> wordList) {
        this.wordList = wordList;
    }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_item, parent, false);
        return new WordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WordViewHolder holder, int position) {
        Word word = wordList.get(position);
        holder.englishWordTextView.setText(word.getWord());
        holder.translationsTextView.setText(word.getTranslations());

        // Устанавливаем обработчик клика на элемент
        holder.itemView.setOnClickListener(v -> {
            // Останавливаем воспроизведение текущего аудио, если оно идет
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.release();
            }

            // Получаем аудио файл и воспроизводим его
            String audioFileName = word.getAudio();
            int audioResId = holder.itemView.getContext().getResources().getIdentifier(audioFileName, "raw", holder.itemView.getContext().getPackageName());

            if (audioResId != 0) {
                mediaPlayer = MediaPlayer.create(holder.itemView.getContext(), audioResId);
                mediaPlayer.start(); // Воспроизведение аудио
            }
        });
    }

    @Override
    public int getItemCount() {
        return wordList.size();
    }

    public static class WordViewHolder extends RecyclerView.ViewHolder {
        TextView englishWordTextView;
        TextView translationsTextView;

        public WordViewHolder(View itemView) {
            super(itemView);
            englishWordTextView = itemView.findViewById(R.id.englishWord);
            translationsTextView = itemView.findViewById(R.id.translations);
        }
    }
}
