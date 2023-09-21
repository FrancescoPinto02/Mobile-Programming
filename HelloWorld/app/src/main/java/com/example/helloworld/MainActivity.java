package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView tv;
    Random rand = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Trova la TextView con Hello World
        tv = findViewById(R.id.HWTextView);
    }

    public void changeColor(View v){

        //Cambia colore dello sfondo alla Text View
        int r = rand.nextInt(255)+1;
        int g = rand.nextInt(255)+1;
        int b = rand.nextInt(255)+1;
        tv.setBackgroundColor(Color.rgb(r,g,b));

        //Cambia colore del testo alla Text View
        r = rand.nextInt(255)+1;
        g = rand.nextInt(255)+1;
        b = rand.nextInt(255)+1;
        tv.setTextColor(Color.rgb(r,g,b));

        //Cambia dimensione del testo alla Text View
        int textSize = rand.nextInt(51)+10;
        tv.setTextSize(textSize);
    }
}