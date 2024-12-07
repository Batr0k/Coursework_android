package com.example.coursework;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Имя базы данных и версия
    private static final String DATABASE_NAME = "english_russian_dictionary.db";
    private static final int DATABASE_VERSION = 15;

    // Названия таблиц
    public static final String TABLE_ENGLISH_WORDS = "english_words";
    public static final String TABLE_RUSSIAN_WORDS = "russian_words";
    public static final String TABLE_DICTIONARY_RELATIONS = "dictionary_relations";
    public static final String TABLE_DIFFICULTY_CATEGORIES = "difficulty_categories";
    public static final String TABLE_PERSONAL_DICTIONARY = "personal_dictionary";

    // SQL-запросы для создания таблиц
    private static final String CREATE_ENGLISH_WORDS_TABLE = "CREATE TABLE " + TABLE_ENGLISH_WORDS + " ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "word TEXT NOT NULL, "
            + "audio_url TEXT, "
            + "difficulty_category_id INTEGER, "
            + "FOREIGN KEY (difficulty_category_id) REFERENCES " + TABLE_DIFFICULTY_CATEGORIES + "(id));";

    private static final String CREATE_RUSSIAN_WORDS_TABLE = "CREATE TABLE " + TABLE_RUSSIAN_WORDS + " ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "word TEXT NOT NULL);";

    private static final String CREATE_DICTIONARY_RELATIONS_TABLE = "CREATE TABLE " + TABLE_DICTIONARY_RELATIONS + " ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "english_word_id INTEGER, "
            + "russian_word_id INTEGER, "
            + "FOREIGN KEY (english_word_id) REFERENCES " + TABLE_ENGLISH_WORDS + "(id), "
            + "FOREIGN KEY (russian_word_id) REFERENCES " + TABLE_RUSSIAN_WORDS + "(id));";

    private static final String CREATE_DIFFICULTY_CATEGORIES_TABLE = "CREATE TABLE " + TABLE_DIFFICULTY_CATEGORIES + " ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "difficulty_level INTEGER NOT NULL);";

    private static final String CREATE_PERSONAL_DICTIONARY_TABLE = "CREATE TABLE " + TABLE_PERSONAL_DICTIONARY + " ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "english_word_id INTEGER, "
            + "FOREIGN KEY (english_word_id) REFERENCES " + TABLE_ENGLISH_WORDS + "(id));";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Создание таблиц
        db.execSQL(CREATE_ENGLISH_WORDS_TABLE);
        db.execSQL(CREATE_RUSSIAN_WORDS_TABLE);
        db.execSQL(CREATE_DICTIONARY_RELATIONS_TABLE);
        db.execSQL(CREATE_DIFFICULTY_CATEGORIES_TABLE);
        db.execSQL(CREATE_PERSONAL_DICTIONARY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Удаление старых таблиц, если версия базы данных изменилась
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENGLISH_WORDS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RUSSIAN_WORDS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DICTIONARY_RELATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DIFFICULTY_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSONAL_DICTIONARY);
        onCreate(db);
    }

    // Вставка английского слова с возвратом ID
    public long insertEnglishWord(String word, String audioFileName, int difficultyCategoryId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("word", word);
        values.put("audio_url", audioFileName);
        values.put("difficulty_category_id", difficultyCategoryId);
        long id = db.insert(TABLE_ENGLISH_WORDS, null, values);  // Возвращаем ID
        db.close();
        return id;
    }

    // Вставка русского слова с возвратом ID
    public long insertRussianWord(String word) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("word", word);
        long id = db.insert(TABLE_RUSSIAN_WORDS, null, values);  // Возвращаем ID
        db.close();
        return id;
    }

    // Вставка связи между английским и русским словом
    public void insertDictionaryRelation(long englishWordId, long russianWordId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("english_word_id", englishWordId);
        values.put("russian_word_id", russianWordId);
        db.insert(TABLE_DICTIONARY_RELATIONS, null, values);
        db.close();
    }

    // Вставка категории сложности
    public void insertDifficultyCategory(int difficultyLevel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("difficulty_level", difficultyLevel); // Число, представляющее уровень сложности
        db.insert(TABLE_DIFFICULTY_CATEGORIES, null, values);
        db.close();
    }

    // Вставка в личный словарь
    public void insertPersonalDictionary(int englishWordId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("english_word_id", englishWordId);
        db.insert(TABLE_PERSONAL_DICTIONARY, null, values);
        db.close();
    }
    public List<Map<String, String>> getEnglishWordsWithTranslationsByDifficulty(int difficultyLevel) {
        List<Map<String, String>> wordList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT ew.word, ew.audio_url, rw.word AS translation " +
                "FROM " + TABLE_ENGLISH_WORDS + " ew " +
                "JOIN " + TABLE_DIFFICULTY_CATEGORIES + " dc " +
                "ON ew.difficulty_category_id = dc.id " +
                "JOIN " + TABLE_DICTIONARY_RELATIONS + " dr " +
                "ON ew.id = dr.english_word_id " +
                "JOIN " + TABLE_RUSSIAN_WORDS + " rw " +
                "ON dr.russian_word_id = rw.id " +
                "WHERE dc.difficulty_level = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(difficultyLevel)});

        if (cursor.moveToFirst()) {
            Map<String, Map<String, String>> wordMap = new HashMap<>();

            do {
                String englishWord = cursor.getString(cursor.getColumnIndex("word"));
                String audioUrl = cursor.getString(cursor.getColumnIndex("audio_url"));
                String translation = cursor.getString(cursor.getColumnIndex("translation"));

                // Проверяем, если слово уже добавлено в список
                if (!wordMap.containsKey(englishWord)) {
                    wordMap.put(englishWord, new HashMap<>());
                    wordMap.get(englishWord).put("audio_url", audioUrl);
                }

                // Добавляем перевод в список (или строку с переводами)
                String currentTranslations = wordMap.get(englishWord).get("translations");
                if (currentTranslations == null) {
                    currentTranslations = translation;
                } else {
                    currentTranslations += ", " + translation;  // Добавляем новый перевод
                }
                wordMap.get(englishWord).put("translations", currentTranslations);

            } while (cursor.moveToNext());

            // Конвертируем в список
            for (Map.Entry<String, Map<String, String>> entry : wordMap.entrySet()) {
                Map<String, String> wordData = entry.getValue();
                wordData.put("english_word", entry.getKey());
                wordList.add(wordData);
            }
        }

        cursor.close();
        db.close();

        return wordList;
    }
    public List<String> getAllRussianWords() {
        List<String> russianWords = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT word FROM " + TABLE_RUSSIAN_WORDS;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String word = cursor.getString(cursor.getColumnIndex("word"));
                russianWords.add(word);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return russianWords;
    }
    public List<String> getAllEnglishWords() {
        List<String> englishWords = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // SQL-запрос для получения всех английских слов
        String query = "SELECT word FROM " + TABLE_ENGLISH_WORDS;
        Cursor cursor = db.rawQuery(query, null);

        // Перебираем все строки, если они есть
        if (cursor.moveToFirst()) {
            do {
                String word = cursor.getString(cursor.getColumnIndex("word"));
                englishWords.add(word);  // Добавляем слово в список
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return englishWords;
    }
    public List<Integer> getAllDifficultyCategories() {
        List<Integer> difficultyCategories = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // SQL-запрос для получения всех категорий сложности
        String query = "SELECT difficulty_level FROM " + TABLE_DIFFICULTY_CATEGORIES;
        Cursor cursor = db.rawQuery(query, null);

        // Перебираем все строки, если они есть
        if (cursor.moveToFirst()) {
            do {
                int difficultyLevel = cursor.getInt(cursor.getColumnIndex("difficulty_level"));
                difficultyCategories.add(difficultyLevel);  // Добавляем уровень сложности в список
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return difficultyCategories;
    }
    public List<String> getRussianWordsByEnglishDifficultyRange(int minLevel, int maxLevel) {
        List<String> russianWords = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT rw.word " +
                "FROM " + TABLE_RUSSIAN_WORDS + " rw " +
                "JOIN " + TABLE_DICTIONARY_RELATIONS + " dr ON rw.id = dr.russian_word_id " +
                "JOIN " + TABLE_ENGLISH_WORDS + " ew ON dr.english_word_id = ew.id " +
                "JOIN " + TABLE_DIFFICULTY_CATEGORIES + " dc ON ew.difficulty_category_id = dc.id " +
                "WHERE dc.difficulty_level BETWEEN ? AND ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(minLevel), String.valueOf(maxLevel)});

        if (cursor.moveToFirst()) {
            do {
                String word = cursor.getString(cursor.getColumnIndex("word"));
                russianWords.add(word);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return russianWords;
    }
    public List<Map<String, String>> getRussianWordsWithTranslationsByDifficulty(int difficultyLevel) {
        List<Map<String, String>> wordList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT rw.word, ew.word AS translation, ew.audio_url " +
                "FROM " + TABLE_RUSSIAN_WORDS + " rw " +
                "JOIN " + TABLE_DICTIONARY_RELATIONS + " dr ON rw.id = dr.russian_word_id " +
                "JOIN " + TABLE_ENGLISH_WORDS + " ew ON dr.english_word_id = ew.id " +
                "JOIN " + TABLE_DIFFICULTY_CATEGORIES + " dc ON ew.difficulty_category_id = dc.id " +
                "WHERE dc.difficulty_level = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(difficultyLevel)});

        if (cursor.moveToFirst()) {
            Map<String, Map<String, String>> wordMap = new HashMap<>();

            do {
                String russianWord = cursor.getString(cursor.getColumnIndex("word"));
                String englishWord = cursor.getString(cursor.getColumnIndex("translation"));
                String audioUrl = cursor.getString(cursor.getColumnIndex("audio_url"));

                // Проверяем, если слово уже добавлено в список
                if (!wordMap.containsKey(russianWord)) {
                    wordMap.put(russianWord, new HashMap<>());
                    wordMap.get(russianWord).put("audio_url", audioUrl);
                }

                // Добавляем перевод в список (или строку с переводами)
                String currentTranslations = wordMap.get(russianWord).get("translations");
                if (currentTranslations == null) {
                    currentTranslations = englishWord;
                } else {
                    currentTranslations += ", " + englishWord;  // Добавляем новый перевод
                }
                wordMap.get(russianWord).put("translations", currentTranslations);

            } while (cursor.moveToNext());

            // Конвертируем в список
            for (Map.Entry<String, Map<String, String>> entry : wordMap.entrySet()) {
                Map<String, String> wordData = entry.getValue();
                wordData.put("english_word", entry.getKey());
                wordList.add(wordData);
            }
        }

        cursor.close();
        db.close();

        return wordList;
    }
    public List<String> getEnglishWordsByDifficultyRange(int minLevel, int maxLevel) {
        List<String> englishWords = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT ew.word " +
                "FROM " + TABLE_ENGLISH_WORDS + " ew " +
                "JOIN " + TABLE_DIFFICULTY_CATEGORIES + " dc ON ew.difficulty_category_id = dc.id " +
                "WHERE dc.difficulty_level BETWEEN ? AND ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(minLevel), String.valueOf(maxLevel)});

        if (cursor.moveToFirst()) {
            do {
                String englishWord = cursor.getString(cursor.getColumnIndex("word"));
                englishWords.add(englishWord);  // Добавляем слово в список
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return englishWords;
    }
    public List<Map<String, String>> getEnglishWordsWithTranslationsFromPersonalDictionary() {
        List<Map<String, String>> wordList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT ew.word, ew.audio_url, rw.word AS translation " +
                "FROM " + TABLE_ENGLISH_WORDS + " ew " +
                "JOIN " + TABLE_DICTIONARY_RELATIONS + " dr ON ew.id = dr.english_word_id " +
                "JOIN " + TABLE_RUSSIAN_WORDS + " rw ON dr.russian_word_id = rw.id " +
                "WHERE ew.id IN (SELECT english_word_id FROM " + TABLE_PERSONAL_DICTIONARY + ")";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            Map<String, Map<String, String>> wordMap = new HashMap<>();

            do {
                String englishWord = cursor.getString(cursor.getColumnIndex("word"));
                String audioUrl = cursor.getString(cursor.getColumnIndex("audio_url"));
                String translation = cursor.getString(cursor.getColumnIndex("translation"));

                // Проверяем, если слово уже добавлено в список
                if (!wordMap.containsKey(englishWord)) {
                    wordMap.put(englishWord, new HashMap<>());
                    wordMap.get(englishWord).put("audio_url", audioUrl);
                }

                // Добавляем перевод в список (или строку с переводами)
                String currentTranslations = wordMap.get(englishWord).get("translations");
                if (currentTranslations == null) {
                    currentTranslations = translation;
                } else {
                    currentTranslations += ", " + translation;  // Добавляем новый перевод
                }
                wordMap.get(englishWord).put("translations", currentTranslations);

            } while (cursor.moveToNext());

            // Конвертируем в список
            for (Map.Entry<String, Map<String, String>> entry : wordMap.entrySet()) {
                Map<String, String> wordData = entry.getValue();
                wordData.put("english_word", entry.getKey());
                wordList.add(wordData);
            }
        }

        cursor.close();
        db.close();

        return wordList;
    }
    public void addWordToPersonalDictionary(String englishWord) {
        SQLiteDatabase db = this.getReadableDatabase();

        // SQL-запрос для поиска ID английского слова
        String query = "SELECT id FROM " + TABLE_ENGLISH_WORDS + " WHERE word = ?";
        Cursor cursor = db.rawQuery(query, new String[]{englishWord});

        if (cursor.moveToFirst()) {
            // Получаем ID найденного английского слова
            long englishWordId = cursor.getLong(cursor.getColumnIndex("id"));

            // Вставляем это ID в таблицу персонального словаря
            insertPersonalDictionary((int) englishWordId);
        } else {
            // Слово не найдено в базе данных, выводим ошибку или сообщение
            System.out.println("Word not found in the English words table");
        }

        cursor.close();
        db.close();
    }
    public boolean hasEnglishWords() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM " + TABLE_ENGLISH_WORDS;
        Cursor cursor = db.rawQuery(query, null);

        boolean hasWords = false;
        if (cursor.moveToFirst()) {
            int count = cursor.getInt(0); // Получаем значение из первой колонки (результат COUNT)
            hasWords = count > 0; // Проверяем, есть ли записи
        }

        cursor.close();
        db.close();
        return hasWords;
    }

}


