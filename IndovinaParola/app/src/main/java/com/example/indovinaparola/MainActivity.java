package com.example.indovinaparola;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private String[] words = {"ESEMPIO", "ANDROID", "INDOVINA", "GIOCO"};
    private String selectedWord;
    private String displayWord;
    private int attempts;
    private TextView wordToGuess;
    private EditText letterInput;
    private TextView attemptCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wordToGuess = findViewById(R.id.wordToGuess);
        letterInput = findViewById(R.id.letterInput);
        attemptCount = findViewById(R.id.attemptCount);
        Button guessButton = findViewById(R.id.guessButton);
        Button resetButton = findViewById(R.id.resetButton);
        Button helpButton = findViewById(R.id.helpButton);

        if (savedInstanceState != null) {
            // Ripristina lo stato
            selectedWord = savedInstanceState.getString("selectedWord");
            displayWord = savedInstanceState.getString("displayWord");
            attempts = savedInstanceState.getInt("attempts");
            boolean controlsEnabled = savedInstanceState.getBoolean("controlsEnabled");

            wordToGuess.setText(displayWord);
            updateAttempts();
            if (!controlsEnabled) {
                // Se i controlli erano disabilitati, il gioco era finito. Mostra il messaggio di vittoria e disabilita i controlli.
                attemptCount.setText("Hai Vinto con " + attempts + " tentativi!");
                disableGameControls();
            }
        } else {
            // Inizia una nuova partita se non c'è uno stato da ripristinare
            startNewGame();
        }

        guessButton.setOnClickListener(v -> {
            String letter = letterInput.getText().toString().toUpperCase();
            if (letter.length() == 1 && selectedWord.contains(letter)) {
                updateDisplayWord(letter);
                wordToGuess.setText(displayWord);
            }
            attempts++;
            updateAttempts();
            letterInput.setText("");
            if(checkWin()){
                attemptCount.setText("Hai Vinto con " + attempts + " tentativi!");
                disableGameControls();
            }
        });

        resetButton.setOnClickListener(v -> {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Nuova Partita")
                    .setMessage("Sei sicuro di voler iniziare una nuova partita?")
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                        // Inizia una nuova partita
                        startNewGame();
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        });

        helpButton.setOnClickListener(v -> {
            revealLetter();
            attempts += 5; // Aggiunge 5 tentativi
            updateAttempts(); // Aggiorna il conteggio dei tentativi visualizzato
            // Opzionalmente, puoi disabilitare il pulsante di aiuto dopo il suo uso o limitarne l'uso
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("selectedWord", selectedWord);
        outState.putString("displayWord", displayWord);
        outState.putInt("attempts", attempts);
        outState.putBoolean("controlsEnabled", letterInput.isEnabled());
    }

    private void updateAttempts(){
        attemptCount.setText("Tentativi: " + attempts);
    }

    private boolean checkWin(){
        return !displayWord.contains("_");
    }


    private void startNewGame() {
        Random rand = new Random();
        selectedWord = words[rand.nextInt(words.length)];
        displayWord = selectedWord.replaceAll(".", "_ ");
        attempts = 0;
        wordToGuess.setText(displayWord);
        attemptCount.setText("Tentativi: 0");
        enableGameControls();
    }

    private void updateDisplayWord(String letter) {
        char[] displayArray = displayWord.toCharArray();
        for (int i = 0; i < selectedWord.length(); i++) {
            if (selectedWord.charAt(i) == letter.charAt(0)) {
                displayArray[i*2] = letter.charAt(0); // Perché ogni carattere è seguito da uno spazio
            }
        }
        displayWord = new String(displayArray);
    }

    private void disableGameControls() {
        letterInput.setEnabled(false);
        findViewById(R.id.guessButton).setEnabled(false);
    }

    private void enableGameControls() {
        letterInput.setEnabled(true);
        findViewById(R.id.guessButton).setEnabled(true);
    }

    private void revealLetter() {
        List<Integer> hiddenLettersIndexes = new ArrayList<>();
        for (int i = 0; i < selectedWord.length(); i++) {
            if (displayWord.charAt(i*2) == '_') { // Ricorda che ogni carattere visualizzato è seguito da uno spazio
                hiddenLettersIndexes.add(i);
            }
        }
        if (!hiddenLettersIndexes.isEmpty()) {
            int randomIndex = hiddenLettersIndexes.get(new Random().nextInt(hiddenLettersIndexes.size()));
            char letterToReveal = selectedWord.charAt(randomIndex);
            updateDisplayWord(String.valueOf(letterToReveal));
            wordToGuess.setText(displayWord);
        }
    }
}