package com.example.rubrica;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.browse.MediaBrowser;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    private ListView contactsListView;
    //private ArrayAdapter<Contact> adapter;
    private CustomAdapter customAdapter;
    private ArrayList<Contact> contacts;
    private ContactGenerator contactGenerator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState!=null){
            contacts = (ArrayList<Contact>) savedInstanceState.getSerializable("contacts");
        }
        else{
            contactGenerator = new ContactGenerator(this);
            contacts = (ArrayList<Contact>) contactGenerator.generateContactCollection(20);
            Collections.sort(contacts);
        }


        contactsListView = findViewById(R.id.contactsLV);

        // Inizializza l'ArrayAdapter con la lista di contatti
        customAdapter = new CustomAdapter(this, R.layout.list_element, contacts);

        // Imposta l'ArrayAdapter sul ListView
        contactsListView.setAdapter(customAdapter);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle savedInstanceState){
        //salva lo stato dell`app
        savedInstanceState.putSerializable("contacts",contacts);
        super.onSaveInstanceState(savedInstanceState);
    }

    public void onPictureClick(View v) {
        Log.d("DEBUG","Picture has been clicked: position="+v.getTag());
        int position = Integer.parseInt(v.getTag().toString());
        Contact c = customAdapter.getItem(position);
        Toast.makeText(getApplicationContext(),"Click su Foto - posizione n."+position+": " +c.getName(), Toast.LENGTH_LONG).show();
    }

    public void onNameClick(View v) {
        Log.d("DEBUG","Name has been clicked position="+v.getTag());
        int position = Integer.parseInt(v.getTag().toString());
        Contact c = customAdapter.getItem(position);
        Toast.makeText(getApplicationContext(), "Click su Nome - posizione n."+position+": " +c.getName(), Toast.LENGTH_LONG).show();
    }

    public void onTelClick(View v) {
        Log.d("DEBUG","Tel has been clicked position="+v.getTag());
        int position = Integer.parseInt(v.getTag().toString());
        Contact c = customAdapter.getItem(position);
        Toast.makeText(getApplicationContext(), "Click su Tel - posizione n."+position+": " +c.getName(), Toast.LENGTH_LONG).show();
    }
}