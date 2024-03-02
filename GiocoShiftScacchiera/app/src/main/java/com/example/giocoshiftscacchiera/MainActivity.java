package com.example.giocoshiftscacchiera;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private int[][] numbers = new int[3][3];
    private TextView[][] cells = new TextView[3][3];
    private Button[] shiftLeftButton = new Button[3];
    private Button[] shiftRightButton = new Button[3];
    private Button[] shiftUpButton = new Button[3];
    private Button[] shiftDownButton = new Button[3];
    private int shiftCount = 0;
    private TextView shiftCounterTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shiftCounterTV = findViewById(R.id.shiftCounterTextView);

        cells[0][0] = findViewById(R.id.cell1);
        cells[0][1] = findViewById(R.id.cell2);
        cells[0][2] = findViewById(R.id.cell3);
        cells[1][0] = findViewById(R.id.cell4);
        cells[1][1] = findViewById(R.id.cell5);
        cells[1][2] = findViewById(R.id.cell6);
        cells[2][0] = findViewById(R.id.cell7);
        cells[2][1] = findViewById(R.id.cell8);
        cells[2][2] = findViewById(R.id.cell9);

        shiftLeftButton[0] = findViewById(R.id.left1);
        shiftLeftButton[1] = findViewById(R.id.left2);
        shiftLeftButton[2] = findViewById(R.id.left3);
        for(int i=0; i<3; i++){
            int row = i;
            shiftLeftButton[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    shiftRowLeft(row);
                }
            });
        }

        shiftRightButton[0] = findViewById(R.id.right1);
        shiftRightButton[1] = findViewById(R.id.right2);
        shiftRightButton[2] = findViewById(R.id.right3);
        for(int i=0; i<3; i++){
            int row = i;
            shiftRightButton[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    shiftRowRight(row);
                }
            });
        }

        shiftUpButton[0] = findViewById(R.id.up1);
        shiftUpButton[1] = findViewById(R.id.up2);
        shiftUpButton[2] = findViewById(R.id.up3);
        for(int i=0; i<3; i++){
            int column = i;
            shiftUpButton[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    shiftColumnUp(column);
                }
            });
        }

        shiftDownButton[0] = findViewById(R.id.down1);
        shiftDownButton[1] = findViewById(R.id.down2);
        shiftDownButton[2] = findViewById(R.id.down3);
        for(int i=0; i<3; i++){
            int column = i;
            shiftDownButton[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    shiftColumnDown(column);
                }
            });
        }

        if (savedInstanceState != null) {
            int rows = savedInstanceState.getInt("rows");
            int cols = savedInstanceState.getInt("cols");
            int[] flatArray = savedInstanceState.getIntArray("numbers");
            numbers = new int[rows][cols];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    numbers[i][j] = flatArray[i * cols + j];
                }
            }
            shiftCount = savedInstanceState.getInt("shiftCount");
            updateGrid();
            updateShiftCounter();
        }
        else{
            initializeGame();
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("rows", numbers.length);
        outState.putInt("cols", numbers[0].length);
        int[] flatArray = new int[numbers.length * numbers[0].length];
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < numbers[i].length; j++) {
                flatArray[i * numbers[i].length + j] = numbers[i][j];
            }
        }
        outState.putIntArray("numbers", flatArray);
        outState.putInt("shiftCount", shiftCount);
    }


    private void initializeGame() {
        ArrayList<Integer> numberList = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            numberList.add(i);
        }

        Collections.shuffle(numberList);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                numbers[i][j] = numberList.get(i * 3 + j);
            }
        }

        updateGrid();
        shiftCount = 0;
        updateShiftCounter();
    }

    private void updateShiftCounter() {
        shiftCounterTV.setText("" + shiftCount);
    }

    private void updateGrid() {
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                cells[i][j].setText("" + numbers[i][j]);
            }
        }
    }


    private void shiftRowLeft(int row) {
        int first = numbers[row][0];
        for (int i = 0; i < numbers[row].length - 1; i++) {
            numbers[row][i] = numbers[row][i + 1];
        }
        numbers[row][numbers[row].length - 1] = first;
        shiftCount++;
        updateGrid();
        updateShiftCounter();
        if(checkForWin()){
            onGameWon();
        }
    }

    private void shiftRowRight(int row) {
        int last = numbers[row][numbers[row].length - 1];
        for (int i = numbers[row].length - 1; i > 0; i--) {
            numbers[row][i] = numbers[row][i - 1];
        }
        numbers[row][0] = last;
        shiftCount++;
        updateGrid();
        updateShiftCounter();
        if(checkForWin()){
            onGameWon();
        }
    }

    private void shiftColumnUp(int col) {
        int first = numbers[0][col];
        for (int i = 0; i < numbers.length - 1; i++) {
            numbers[i][col] = numbers[i + 1][col];
        }
        numbers[numbers.length - 1][col] = first;
        shiftCount++;
        updateGrid();
        updateShiftCounter();
        if(checkForWin()){
            onGameWon();
        }
    }

    private void shiftColumnDown(int col) {
        int last = numbers[numbers.length - 1][col];
        for (int i = numbers.length - 1; i > 0; i--) {
            numbers[i][col] = numbers[i - 1][col];
        }
        numbers[0][col] = last;
        shiftCount++;
        updateGrid();
        updateShiftCounter();
        if(checkForWin()){
            onGameWon();
        }
    }


    private boolean checkForWin() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                if (numbers[i][j] > numbers[i][j + 1]) {
                    return false;
                }
            }
        }

        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 2; i++) {
                if (numbers[i][j] > numbers[i + 1][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    public void winGameNow(View view) {
        int count = 1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                numbers[i][j] = count++;
            }
        }
        updateGrid();
        if(checkForWin()){
            onGameWon();
        }
    }


    private void onGameWon() {
        SharedPreferences prefs = getSharedPreferences("BestPlayer", MODE_PRIVATE);
        int bestScore = prefs.getInt("BestScore", Integer.MAX_VALUE);

        if (shiftCount < bestScore) {
            // Crea un AlertDialog con un EditText per inserire il nome
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Nuovo record!");

            // Imposta il messaggio e l'EditText per il dialogo
            final EditText inputField = new EditText(this);
            inputField.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(inputField);

            builder.setMessage("Hai ottenuto il punteggio più alto con " + shiftCount + " mosse. Inserisci il tuo nome:");

            // Imposta il bottone di invio e salva il nuovo record
            builder.setPositiveButton("Salva", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String playerName = inputField.getText().toString();
                    saveBestScore(playerName, shiftCount);
                }
            });

            builder.setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
            initializeGame();
        } else {
            // Mostra il messaggio di vittoria standard se non è un nuovo record
            showWinningMessage(shiftCount);
        }
    }

    private void saveBestScore(String playerName, int score) {
        SharedPreferences prefs = getSharedPreferences("BestPlayer", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("BestScore", score);
        editor.putString("BestPlayer", playerName);
        editor.apply();

        // Mostra un Toast o aggiorna l'interfaccia utente per riflettere il nuovo record
        Toast.makeText(this, "Record salvato: " + playerName + " con " + score + " mosse!", Toast.LENGTH_LONG).show();
    }

    private void showWinningMessage(int score) {
        // Mostra un AlertDialog o un Toast per congratularti con l'utente
        new AlertDialog.Builder(this)
                .setTitle("Complimenti!")
                .setMessage("Hai vinto il gioco con " + score + " mosse!")
                .setPositiveButton("Gioca ancora", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Resetta il gioco
                        initializeGame();
                    }
                })
                .setNegativeButton("Esci", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Esce dall'applicazione
                        finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

}