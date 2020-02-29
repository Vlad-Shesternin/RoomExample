package com.example.roomexample.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.roomexample.room.models.Word;

import java.util.List;

@Dao
public interface WordDao {

    @Query("SELECT * FROM word_table")
    List<Word> getAllWords();

    @Insert
    void insertWord(Word word);

    @Query("UPDATE word_table SET word = :new_world WHERE word = :old_word")
    void updateWord(String old_word, String new_world);

    @Query("DELETE FROM word_table")
    void deleteAllWord();
}
