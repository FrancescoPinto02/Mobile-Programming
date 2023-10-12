package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView questionNum;
    TextView questionText;
    TextView correct;
    TextView invalid;
    TextView total;
    ArrayList<Question> questions;
    int currentQuestion;
    int correctNum;
    int invalidNum;
    int totalNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        questionNum = findViewById(R.id.questionNumberTV);
        questionText = findViewById(R.id.questionTextTV);
        correct = findViewById(R.id.correctTV);
        invalid = findViewById(R.id.invalidTV);
        total = findViewById(R.id.totalTV);
        questions = new ArrayList<>();
        questions.add(new Question("2 + 2 = 4", true));
        questions.add(new Question("2 + 3 = 6", false));
        questions.add(new Question("2 + 1 = 3", true));
        questions.add(new Question("5 + 2 = 1", false));
        questions.add(new Question("6 + 2 = 8", true));
        currentQuestion = 0;
        correctNum = 0;
        invalidNum = 0;
        totalNum = 0;

        updateLayout();
    }

    public void updateLayout(){
        questionNum.setText("Domanda n. " + (currentQuestion+1));
        questionText.setText(questions.get(currentQuestion).getText());
        correct.setText("Risposte corrette: "+ correctNum);
        invalid.setText("Risposte corrette non valide: " + invalidNum);
        total.setText("Risposte totali: " + totalNum);
    }

    public void sucClick(View v){
        if(currentQuestion == questions.size()-1){
            currentQuestion = 0;
        }
        else{
            currentQuestion++;
        }

        updateLayout();
    }

    public void preClick(View v){
        if(currentQuestion == 0){
            currentQuestion = questions.size()-1;
        }
        else{
            currentQuestion--;
        }

        updateLayout();
    }

    public void trueClick(View v){
        Question q = questions.get(currentQuestion);
        if(!q.isAnswered()){
            if(q.getAnswer()==true){
                if(q.isValid()){
                    correctNum++;
                }
                else{
                    invalidNum++;
                }
            }
            totalNum++;
            q.setAnswered(true);
            sucClick(null);
        }
    }

    public void falseClick(View v){
        Question q = questions.get(currentQuestion);
        if(!q.isAnswered()){
            if(q.getAnswer()==false){
                if(q.isValid()){
                    correctNum++;
                }
                else{
                    invalidNum++;
                }
            }
            totalNum++;
            q.setAnswered(true);
            sucClick(null);
        }
    }

    public void suggestClick(View v){
        Intent i = new Intent(getApplicationContext(), SuggestActivity.class);
        i.putExtra("question", questions.get(currentQuestion).getText());
        i.putExtra("suggest" , questions.get(currentQuestion).getAnswer());
        startActivityForResult(i, 1234);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1234 && resultCode == Activity.RESULT_OK && data != null){
            if(data.getBooleanExtra("showed", false)==true)
                questions.get(currentQuestion).setValid(false);
        }
    }
}