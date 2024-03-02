package com.example.giocogriglia;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button[] cells = new Button[9];
    private ToggleButton modToggle;
    private Button bonusButton, ripartiButton;
    private boolean isRowMode = true; // true per modalità riga, false per colonna
    private int movesCount;
    private TextView movesTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        modToggle = findViewById(R.id.modToggle);
        bonusButton = findViewById(R.id.BonusButton);
        ripartiButton = findViewById(R.id.RipartiButton);
        movesTV = findViewById(R.id.CounterTextView);

        // Inizializzazione delle celle
        for (int i = 0; i < cells.length; i++) {
            String cellId = "cell" + (i + 1);
            int resId = getResources().getIdentifier(cellId, "id", getPackageName());
            cells[i] = findViewById(resId);
            final int finalI = i;
            cells[i].setOnClickListener(view -> cellClicked(finalI));
        }

        modToggle.setOnCheckedChangeListener((buttonView, isChecked) -> isRowMode = !isChecked);

        bonusButton.setOnClickListener(view -> applyBonus());

        ripartiButton.setOnClickListener(view -> initGame());

        initGame();
    }

    private void initGame() {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);
        for (int i = 0; i < cells.length; i++) {
            cells[i].setText(String.valueOf(numbers.get(i)));
        }
        movesCount = 0;
        updateMoves();

        for (Button cell : cells) {
            cell.setEnabled(true);
        }
        bonusButton.setEnabled(true);
        modToggle.setEnabled(true);
    }

    public void updateMoves(){
        movesTV.setText("" + movesCount);
    }

    private void cellClicked(int index) {
        int row = index / 3;
        int col = index % 3;
        int incrementValue = Integer.parseInt(cells[index].getText().toString());

        if (isRowMode) {
            for (int i = 0; i < 3; i++) {
                incrementCellValue(row * 3 + i, incrementValue);
            }
        } else {
            for (int i = 0; i < 3; i++) {
                incrementCellValue(col + (i * 3), incrementValue);
            }
        }

        movesCount++;
        updateMoves();
        endGame();
    }

    private void incrementCellValue(int cellIndex, int incrementValue) {
        Button cell = cells[cellIndex];
        int currentValue = Integer.parseInt(cell.getText().toString());
        int newValue = (currentValue + incrementValue) % 10;
        cell.setText(String.valueOf(newValue));
    }

    private void applyBonus() {
        // Scegli una riga o colonna a caso e azzera i valori
        int randomLine = (int) (Math.random() * 3);
        if (isRowMode) {
            for (int i = 0; i < 3; i++) {
                cells[randomLine * 3 + i].setText("0");
            }
        } else {
            for (int i = 0; i < 3; i++) {
                cells[randomLine + (i * 3)].setText("0");
            }
        }
        movesCount += 15;
        updateMoves();
        endGame();
    }

    private void endGame() {
        if (isGameWon()) {
            // Disabilita i pulsanti della griglia e il pulsante bonus
            for (Button cell : cells) {
                cell.setEnabled(false);
            }
            bonusButton.setEnabled(false);
            modToggle.setEnabled(false); // Opzionale, se vuoi disabilitare anche il toggle

            // Mostra un dialog box di vittoria
            new AlertDialog.Builder(this)
                    .setTitle("Complimenti!")
                    .setMessage("Hai vinto! Vuoi giocare di nuovo?")
                    .setPositiveButton("Riparti", (dialog, which) -> initGame())
                    .setNegativeButton("Esci", (dialog, which) -> finish())
                    .show();
        }
    }

    private boolean isGameWon() {
        for (Button cell : cells) {
            if (!cell.getText().toString().equals("0")) {
                return false;
            }
        }
        return true;
    }
}