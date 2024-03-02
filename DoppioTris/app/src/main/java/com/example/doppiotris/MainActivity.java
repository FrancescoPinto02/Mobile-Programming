package com.example.doppiotris;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    TicTacToeFragment fragmentX, fragmentO;
    private char currentTurn = 'X';

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentX = new TicTacToeFragment(); // Imposta il currentPlayer a 'X' nel frammento
        fragmentX.setPlayer('X');
        fragmentO = new TicTacToeFragment(); // Imposta il currentPlayer a 'O' nel frammento
        fragmentO.setPlayer('O');

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainerX, fragmentX)
                .add(R.id.fragmentContainerO, fragmentO)
                .commit();
    }

    public void updateOtherFragment(int buttonId) {
        // Trova il frammento che non ha effettuato la mossa e aggiornalo
        TicTacToeFragment targetFragment = (currentTurn == 'X') ? fragmentO : fragmentX;
        int targetButtonId = mapButtonId(buttonId);

        if (targetFragment != null) {
            targetFragment.updateButton(targetButtonId, currentTurn);
        }

        // Cambia turno
        currentTurn = (currentTurn == 'X') ? 'O' : 'X';
    }

    /**
     * Mappa l'ID del bottone di un frammento all'ID corrispondente nel frammento target.
     * Assumendo che gli ID dei bottoni siano direttamente mappabili, questa funzione può essere semplicemente
     * una corrispondenza uno-a-uno. Se necessario, implementa una logica di mappatura specifica qui.
     */
    private int mapButtonId(int buttonId) {
        // Implementazione di esempio; potrebbe essere necessario adattarla in base alla struttura degli ID dei bottoni
        return buttonId;
    }

    public char getCurrentTurn() {
        return currentTurn;
    }

    public void resetCurrentTurn() {
        currentTurn = 'X'; // Resettare il turno iniziale a 'X'
        // Puoi anche aggiungere logica qui per aggiornare l'UI se necessario
    }

    public void restartBothFragments() {
        if (fragmentX != null && fragmentO != null) {
            fragmentX.restartGame();
            fragmentO.restartGame();
        }

        resetCurrentTurn(); // Assicurati di resettare il turno al giocatore iniziale
    }

}