package com.example.pintofrancescoalessandro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ToggleButton modToggle;
    private LinearLayout digitsContainer;
    private TextView[] digits;
    private int number;
    private Button shuffle2Button;
    private Button shuffle4Button;

    private TextView intDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        modToggle = findViewById(R.id.modToggle);
        digitsContainer = findViewById(R.id.digitsContainer);
        intDisplay = findViewById(R.id.intDisplay);

        digits = new TextView[8];
        for (int i = 0; i < digits.length; i++) {
            int resId = getResources().getIdentifier("digit" + (i + 1), "id", getPackageName());
            digits[i] = findViewById(resId);
            int index = i;
            digits[i].setOnClickListener(v -> performShift(index));
        }

        shuffle2Button = findViewById(R.id.shuffle2);
        shuffle2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shuffle2();
            }
        });

        shuffle4Button = findViewById(R.id.shuffle4);
        shuffle4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shuffle4();
            }
        });

        // Genera valore casuale
        number = new Random().nextInt(256); // 0-255
        updateDisplay();
    }

    //Aggiorna entrambi i display
    private void updateDisplay() {
        // Converte il numero in una stringa binaria
        String binaryString = String.format("%8s", Integer.toBinaryString(number & 0xFF)).replace(' ', '0');
        for (int i = 0; i < digits.length; i++) {
            digits[i].setText(String.valueOf(binaryString.charAt(i)));
        }

        intDisplay.setText(String.valueOf(number));

    }

    private void performShift(int bitIndex) {
        boolean shiftLeft = modToggle.isChecked();
        int shiftAmount = bitIndex + 1;

        if (shiftLeft) {
            // Shift a sinistra
            number = number << shiftAmount | number >>> (8 - shiftAmount);
        } else {
            // Shift a destra
            number = number >>> shiftAmount | number << (8 - shiftAmount);
        }

        number &= 0xFF; //aggiornamento number
        updateDisplay(); // Aggiornamento display
    }


    //Shuffle a coppie
    private void shuffle2() {
        int shuffled = 0;
        //scambia bit delle coppie
        for (int i = 0; i < 8; i += 2) {
            int pair = (number >> (6 - i)) & 0x3;
            shuffled |= (pair >> 1 | (pair & 0x1) << 1) << (6 - i);
        }
        number = shuffled & 0xFF; //per aggiornare number
        updateDisplay();
    }

    //Shuffle a 4 bit
    private void shuffle4() {
        int firstHalf = (number & 0xF0) >> 4;
        int secondHalf = (number & 0x0F) << 4;

        number = firstHalf | secondHalf;
        updateDisplay();
    }
}