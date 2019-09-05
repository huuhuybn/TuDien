package com.dotplays.tudien;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText edtText;
    private RecyclerView lvList;


    private DataBaseHelper dataBaseHelper;

    private WordAdapter wordAdapter;

    private LinearLayoutManager linearLayoutManager;


    private List<Word> wordList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtText = findViewById(R.id.edtText);
        lvList = findViewById(R.id.lvList);

        dataBaseHelper = new DataBaseHelper(this);
        dataBaseHelper.createDataBase();


        wordAdapter = new WordAdapter(wordList, this);
        linearLayoutManager = new LinearLayoutManager(this);

        lvList.setAdapter(wordAdapter);

        lvList.setLayoutManager(linearLayoutManager);


        edtText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                search(charSequence.toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    public void search(String word) {

        List<Word> wordList = dataBaseHelper.searchWord(word);

        this.wordList.addAll(wordList);
        wordAdapter.notifyDataSetChanged();
        
    }

}
