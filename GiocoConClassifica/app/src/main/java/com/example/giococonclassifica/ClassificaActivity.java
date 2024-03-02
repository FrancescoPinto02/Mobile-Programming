package com.example.giococonclassifica;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ClassificaActivity extends AppCompatActivity {

    private TextView[] names;
    private TextView[] scores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.classifica);

        names = new TextView[]{
                findViewById(R.id.nome1),
                findViewById(R.id.nome2),
                findViewById(R.id.nome3)
        };
        scores = new TextView[]{
                findViewById(R.id.score1),
                findViewById(R.id.score2),
                findViewById(R.id.score3),
        };

        loadScoresAndNames();
    }

    private void loadScoresAndNames() {
        SharedPreferences prefs = getSharedPreferences("Classifica", MODE_PRIVATE);

        // Carica i punteggi e i nomi dai SharedPreferences e li imposta nelle TextView
        for (int i = 0; i < 3; i++) {
            int score = prefs.getInt("score" + (i + 1), 0);
            String name = prefs.getString("name" + (i + 1), "N/A");

            if (names[i] != null) {
                names[i].setText(name);
            }
            if (scores[i] != null) {
                scores[i].setText(String.valueOf(score));
            }
        }
    }


/*
    private void updateButtonStates() {
        Button startGameButton = findViewById(R.id.startGameButton); // Assicurati che questo sia l'ID del tuo pulsante di inizio partita
        Button endGameButton = findViewById(R.id.endGameButton); // Assicurati che questo sia l'ID del tuo pulsante di fine partita
        Button showLeaderboardButton = findViewById(R.id.showLeaderboardButton); // Assicurati che questo sia l'ID del tuo pulsante per la classifica

        //SOLUZIONE MIGLIORE PER DISABILITARE I PULSANTI
        startGameButton.setEnabled(!isGameInProgress);
        showLeaderboardButton.setEnabled(!isGameInProgress);
        endGameButton.setEnabled(isGameInProgress);
    }
 */
}
