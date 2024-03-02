package com.example.listacaricamentoimmagini;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private enum Item {
        Smartphone(R.drawable.phone),
        Book(R.drawable.book),
        Dog(R.drawable.dog);

        private final int drawableId;

        Item(int drawableId) {
            this.drawableId = drawableId;
        }

        public int getDrawableId() {
            return drawableId;
        }
    }
    private ImageView imageView;
    private ListView lv;
    private ProgressBar pb;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pb = findViewById(R.id.progressBar);
        imageView = findViewById(R.id.picture);
        lv = findViewById(R.id.list);
        list = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        lv.setAdapter(adapter);
        initializeList();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String itemName = (String) adapterView.getItemAtPosition(i);
                Item item = Item.valueOf(itemName);
                new SimulateImageLoadingTask().execute(item.getDrawableId());
            }
        });
    }

    public void initializeList(){
        Random random = new Random();
        Item[] items = Item.values();
        for(int i=0; i<10; i++){
            list.add(String.valueOf(items[random.nextInt(items.length)]));
        }
        adapter.notifyDataSetChanged();
    }

    private class SimulateImageLoadingTask extends AsyncTask<Integer, Integer, Drawable> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Mostra la ProgressBar prima dell'inizio del caricamento
            pb.setVisibility(View.VISIBLE);
            pb.setProgress(0);
        }

        @Override
        protected Drawable doInBackground(Integer... params) {
            int drawableId = params[0];
            for(int i=1; i<=5; i++){
            }
            try {
                for(int i=1; i<=5; i++){
                    Thread.sleep(1000);
                    publishProgress(i);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return getResources().getDrawable(drawableId, getApplicationContext().getTheme());
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            pb.setProgress(values[0]*20);
        }

        @Override
        protected void onPostExecute(Drawable result) {
            super.onPostExecute(result);
            // Nasconde la ProgressBar
            pb.setVisibility(View.GONE);
            // Imposta l'immagine caricata sull'ImageView
            imageView.setImageDrawable(result);
        }
    }


}