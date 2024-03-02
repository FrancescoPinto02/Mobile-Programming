package com.example.listacontatti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText nameET;
    EditText surnameET;
    EditText phoneET;
    ListView contactLV;
    CustomAdapter customAdapter;

    ArrayList<Contact> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameET = findViewById(R.id.inputName);
        surnameET = findViewById(R.id.inputSurname);
        phoneET = findViewById(R.id.inputPhone);
        contactLV = findViewById(R.id.contactList);
        contacts = new ArrayList<>();

        if(savedInstanceState!=null){
            contacts = (ArrayList<Contact>) savedInstanceState.getSerializable("CONTACTS_LIST");
        }

        customAdapter = new CustomAdapter(this, R.layout.list_item, contacts);
        contactLV.setAdapter(customAdapter);
        contactLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                deleteConfirm(i);
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("CONTACTS_LIST", contacts);
    }

    public void addContact(View v){
        String name = "" + nameET.getText();
        String surname = "" + surnameET.getText();
        String phone = "" + phoneET.getText();
        contacts.add(new Contact(name, surname, phone));
        customAdapter.notifyDataSetChanged();
        nameET.setText("");
        surnameET.setText("");
        phoneET.setText("");
    }

    //Eliminazione Contatto con conferma
    public void deleteConfirm(int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Conferma cancellazione");
        builder.setMessage("Sei sicuro di voler cancellare questo elemento?");

        // Bottone di conferma
        builder.setPositiveButton("Cancella", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Qui implementi la logica per cancellare l'elemento
                // Ad esempio, rimuovere l'elemento dalla lista e notificare l'adapter
                contacts.remove(position);
                customAdapter.notifyDataSetChanged();
            }
        });

        // Bottone di annullamento
        builder.setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Annulla il dialog, non fare nulla
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}