package com.example.giococonclassifica;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int counter;
    private TextView counterTV;
    private boolean gameRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        counterTV = findViewById(R.id.counter);
        initializeGame();
    }

    private void initializeGame(){
        counter = 0;
        updateCounter();
        gameRunning = true;
    }


    private void updateCounter(){
        counterTV.setText(""+counter);
    }

    public void decreaseCounter(View view) {
        if(gameRunning){
            counter--;
            updateCounter();
        }
    }

    public void increaseCounter(View view) {
        if(gameRunning){
            counter++;
            updateCounter();
        }
    }

    public void endGame(View view){
        if(gameRunning){
            gameRunning = false;
            SharedPreferences prefs = getSharedPreferences("Classifica", MODE_PRIVATE);
            int score1 = prefs.getInt("score1", 0);
            int score2 = prefs.getInt("score2", 0);
            int score3 = prefs.getInt("score3", 0);

            // Controlla se il punteggio corrente è tra i primi tre
            if (counter > score1 || counter > score2 || counter > score3) {
                // Chiede all'utente di inserire il suo nome
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Nuovo punteggio alto!");

                // Imposta un EditText per l'input dell'utente
                final EditText input = new EditText(this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                // Imposta il bottone di invio
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String playerName = input.getText().toString();
                        saveNewHighScore(playerName, counter);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            } else {
                Toast.makeText(this, "Punteggio non sufficientemente alto per la classifica.", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void saveNewHighScore(String playerName, int newScore) {
        SharedPreferences prefs = getSharedPreferences("Classifica", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        int score1 = prefs.getInt("score1", 0);
        int score2 = prefs.getInt("score2", 0);
        int score3 = prefs.getInt("score3", 0);

        // Logica per determinare la posizione e spostare i punteggi se necessario
        if (newScore > score1) {
            editor.putInt("score3", score2);
            editor.putString("name3", prefs.getString("name2", ""));

            editor.putInt("score2", score1);
            editor.putString("name2", prefs.getString("name1", ""));

            editor.putInt("score1", newScore);
            editor.putString("name1", playerName);
        } else if (newScore > score2) {
            editor.putInt("score3", score2);
            editor.putString("name3", prefs.getString("name2", ""));

            editor.putInt("score2", newScore);
            editor.putString("name2", playerName);
        } else if (newScore > score3) {
            editor.putInt("score3", newScore);
            editor.putString("name3", playerName);
        }

        editor.apply();

        Toast.makeText(this, "Punteggio salvato!", Toast.LENGTH_SHORT).show();
    }

    public void showLeaderboard(View view) {
        if(!gameRunning){
            Intent intent = new Intent(this, ClassificaActivity.class);
            startActivity(intent);
        }
    }

    public void resetGame(View view) {
        if(!gameRunning){
            initializeGame();
        }
    }
}