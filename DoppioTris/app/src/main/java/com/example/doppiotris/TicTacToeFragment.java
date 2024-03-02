package com.example.doppiotris;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TicTacToeFragment extends Fragment {
    private char player;
    private static final int[] ALL_BUTTON_IDS = new int[] {
            R.id.button1, R.id.button2, R.id.button3,
            R.id.button4, R.id.button5, R.id.button6,
            R.id.button7, R.id.button8, R.id.button9
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tictactoe_board, container, false);

        // Imposta onClickListener per ogni bottone della scacchiera
        view.findViewById(R.id.button1).setOnClickListener(this::onBoardClick);
        view.findViewById(R.id.button2).setOnClickListener(this::onBoardClick);
        view.findViewById(R.id.button3).setOnClickListener(this::onBoardClick);
        view.findViewById(R.id.button4).setOnClickListener(this::onBoardClick);
        view.findViewById(R.id.button5).setOnClickListener(this::onBoardClick);
        view.findViewById(R.id.button6).setOnClickListener(this::onBoardClick);
        view.findViewById(R.id.button7).setOnClickListener(this::onBoardClick);
        view.findViewById(R.id.button8).setOnClickListener(this::onBoardClick);
        view.findViewById(R.id.button9).setOnClickListener(this::onBoardClick);

        return view;
    }

    private void onBoardClick(View view) {
        MainActivity activity = (MainActivity) getActivity();
        if (activity != null && activity.getCurrentTurn() == player) {
            if (!((Button) view).getText().toString().equals("")) {
                return; // La cella è già occupata
            }

            ((Button) view).setText(String.valueOf(player));
            activity.updateOtherFragment(view.getId());
        }

        if (checkForWin()) {
            // Il giocatore corrente ha vinto
            endGame(player + " wins!");
            return;
        } else if (checkForDraw()) {
            // Pareggio
            endGame("It's a draw!");
            return;
        }
    }

    public void updateButton(int buttonId, char currentPlayer) {
        // Trova il bottone nel frammento utilizzando il suo ID
        View view = getView();
        if (view != null) {
            Button button = view.findViewById(buttonId);
            if (button != null) {
                // Aggiorna il testo del bottone con il simbolo corretto
                getActivity().runOnUiThread(() -> button.setText(String.valueOf(currentPlayer)));
            }
        }
    }

    public void setPlayer(char player) {
        this.player = player;
    }

    private boolean checkForWin() {
        Button[][] buttons = new Button[3][3];
        // Inizializzazione dell'array dei bottoni per accedere facilmente

        // Ottiene i riferimenti ai bottoni
        buttons[0][0] = getView().findViewById(R.id.button1);
        buttons[0][1] = getView().findViewById(R.id.button2);
        buttons[0][2] = getView().findViewById(R.id.button3);
        buttons[1][0] = getView().findViewById(R.id.button4);
        buttons[1][1] = getView().findViewById(R.id.button5);
        buttons[1][2] = getView().findViewById(R.id.button6);
        buttons[2][0] = getView().findViewById(R.id.button7);
        buttons[2][1] = getView().findViewById(R.id.button8);
        buttons[2][2] = getView().findViewById(R.id.button9);

        // Controlla righe, colonne e diagonali per una vittoria
        for (int i = 0; i < 3; i++) {
            if (checkEquals(buttons[i][0], buttons[i][1], buttons[i][2]) ||
                    checkEquals(buttons[0][i], buttons[1][i], buttons[2][i])) {
                return true;
            }
        }

        // Controlla le diagonali
        return checkEquals(buttons[0][0], buttons[1][1], buttons[2][2]) ||
                checkEquals(buttons[0][2], buttons[1][1], buttons[2][0]);
    }

    private boolean checkEquals(Button b1, Button b2, Button b3) {
        return !b1.getText().toString().isEmpty() &&
                b1.getText().toString().equals(b2.getText().toString()) &&
                b1.getText().toString().equals(b3.getText().toString());
    }

    private boolean checkForDraw() {
        for (int id : ALL_BUTTON_IDS) {
            Button button = getView().findViewById(id);
            if (button.getText().toString().isEmpty()) {
                return false; // Se c'è almeno una cella vuota, non è pareggio
            }
        }
        return true; // Tutte le celle sono riempite
    }


    private void endGame(String message) {
        new AlertDialog.Builder(getContext())
                .setTitle("Game Over")
                .setMessage(message)
                .setPositiveButton("Restart", (dialog, which) -> {
                    MainActivity activity = (MainActivity) getActivity();
                    if (activity != null) {
                        activity.restartBothFragments();
                    }
                })
                .setNegativeButton("Close", (dialog, which) -> getActivity().finish())
                .show();
    }

    void restartGame() {
        View view = getView();
        if (view != null) {
            // Ripulisci il testo di tutti i bottoni
            for (int id : ALL_BUTTON_IDS) {
                Button button = view.findViewById(id);
                button.setText(""); // Rimuove il testo dal bottone
                button.setEnabled(true); // Riabilita il bottone se era stato disabilitato
            }

            // Resettare eventuali variabili di stato o logiche di gioco qui
            // Per esempio, se cambi il turno del giocatore o mantieni uno stato di gioco
            MainActivity activity = (MainActivity) getActivity();
            if (activity != null) {
                activity.resetCurrentTurn(); // Metodo ipotetico per resettare il turno a 'X'
            }
        }
    }
}
