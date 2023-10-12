package com.example.quiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SuggestActivity extends AppCompatActivity {

    TextView suggestTextTV;
    String questionText;
    boolean questionAnswer;
    boolean suggestShowed;

    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest);
        suggestTextTV = findViewById(R.id.TVsuggest);
        Intent i = getIntent();
        questionText = i.getStringExtra("question");
        questionAnswer = i.getBooleanExtra("suggest", false);
        suggestShowed = false;

        suggestTextTV.setText(questionText);
    }

    public void returnClick(View v){
        onBackPressed();
    }

    public void showClick(View v){
        suggestTextTV.setText(questionText + ": " + questionAnswer);
        suggestShowed = true;

        Intent i = new Intent();
        i.putExtra("showed", suggestShowed);
        setResult(Activity.RESULT_OK, i);

        Toast.makeText(this,"Suggerimento Mostrato: la risposta non è più valida", Toast.LENGTH_LONG).show();
    }
}
