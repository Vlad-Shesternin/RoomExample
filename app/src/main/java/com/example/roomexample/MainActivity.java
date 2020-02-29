package com.example.roomexample;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.roomexample.room.database.WordDatabase;
import com.example.roomexample.room.models.Word;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText mWord;
    EditText mNewWord;
    Button mInsert;
    Button mDelete;
    Button mUpdate;
    Button mGet;
    TextView mListWords;
    WordDatabase db;
    Executor executor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWord = findViewById(R.id.edit_word);
        mNewWord = findViewById(R.id.edit_new_word);

        mInsert = findViewById(R.id.btn_insert);
        mDelete = findViewById(R.id.btn_delete);
        mUpdate = findViewById(R.id.btn_update);
        mGet = findViewById(R.id.btn_get);

        mListWords = findViewById(R.id.tv_list);

        mInsert.setOnClickListener(this);
        mDelete.setOnClickListener(this);
        mUpdate.setOnClickListener(this);
        mGet.setOnClickListener(this);

        db = WordDatabase.getInstance(this);
        executor = Executors.newSingleThreadExecutor();
    }

    @Override
    public void onClick(View v) {
        String _word = mWord.getText().toString();
        String new_word = mNewWord.getText().toString();
        Word word = new Word(_word);
        switch (v.getId()) {
            case R.id.btn_insert:
                executor.execute(() -> {
                            db.wordDao().insertWord(word);
                        }
                );
                break;

            case R.id.btn_delete:
                executor.execute(() -> {
                    db.wordDao().deleteAllWord();
                });
                break;

            case R.id.btn_update:
                executor.execute(() -> {
                    db.wordDao().updateWord(_word, new_word);
                });
                break;

            case R.id.btn_get:
                //System.err.println("Thread-------------1_______" + Thread.currentThread().getName());
                executor.execute(() -> {
                    //System.err.println("Thread-------------2_______" + Thread.currentThread().getName());
                    List<Word> words = db.wordDao().getAllWords();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //System.err.println("Thread-------------3_______" + Thread.currentThread().getName());
                            for (Word word : words) {
                                mListWords.append(word.getId() + ": " + word.getWord() + "\n");
                            }
                        }
                    });
                });
                break;
        }
    }
}
