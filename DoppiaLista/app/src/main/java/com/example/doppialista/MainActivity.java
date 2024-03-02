package com.example.doppialista;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private enum Name {Mario, Antonio, Luca, Luisa, Margherita, Sara, Mimmo, Pippo, Pluto, Paperino, Alfredo, Topolino, Gino, Pino}
    private ToggleButton modTB;
    private boolean mod;
    private ListView lv1, lv2;
    private Button populateB1, populateB2;
    ArrayAdapter<String> adapter1, adapter2;
    ArrayList<String> list1, list2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        modTB = findViewById(R.id.modToggle);
        updateMod();

        lv1 = findViewById(R.id.list1);
        lv2 = findViewById(R.id.list2);
        populateB1 = findViewById(R.id.populate1);
        populateB2 = findViewById(R.id.populate2);
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();

        adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list1);
        adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list2);
        lv1.setAdapter(adapter1);
        lv2.setAdapter(adapter2);

        populateB1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                populateList(list1, adapter1);
            }
        });

        populateB2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                populateList(list2, adapter2);
            }
        });

        modTB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                updateMod();
            }
        });

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(mod){
                    moveToList(list1, adapter1, i, list2, adapter2);
                }
                else{
                    removeFromList(list1, i, adapter1);
                }
            }
        });

        lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(mod){
                    moveToList(list2, adapter2, i, list1, adapter1);
                }
                else{
                    removeFromList(list2, i, adapter2);
                }
            }
        });


    }


    private void populateList(ArrayList<String> list, ArrayAdapter<String> adapter) {
        Name[] names = Name.values();
        Random random = new Random();
        int size = random.nextInt(5)+1;

        for(int i=0; i<size; i++){
            list.add("" + names[random.nextInt(names.length)]);
        }

        adapter.notifyDataSetChanged();
    }

    private void updateMod(){
        mod = modTB.isChecked();
    }

    private void removeFromList(ArrayList<String> list, int i, ArrayAdapter<String> adapter){
        list.remove(i);
        adapter.notifyDataSetChanged();
    }

    private void moveToList(ArrayList<String> sourceList, ArrayAdapter<String> sourceAdapter, int i, ArrayList<String> destList, ArrayAdapter<String> destAdapter){
        destList.add(sourceList.get(i));
        sourceList.remove(i);
        sourceAdapter.notifyDataSetChanged();
        destAdapter.notifyDataSetChanged();
    }
}