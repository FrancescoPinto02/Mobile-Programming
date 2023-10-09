package com.example.rubrica;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

public class ContactGenerator {

    private static String[] names = {"Antonio", "Alberto", "Carlo", "Francesco", "Giuseppe", "Giovanni", "Giulio", "Mauro", "Stefano", "Salvatore"};
    private static String[] surnames = {"Bianchi", "Rossi", "Neri", "Pinto", "Martino", "Citro", "De Pascale", "Di Sarno", "Guida", "Cirillo"};

    private static Random random = new Random();
    private static final int STRING_COUNT = 10;

    private Context context;

    public ContactGenerator(Context context){
        this.context = context;
    }

    //Genera un contatto casuale
    public Contact generateContact(){
        //genera nome casuale (nome cognome)
        String name = names[random.nextInt(STRING_COUNT)] + " " + surnames[random.nextInt(STRING_COUNT)];
        //genera numero di telefono casuale (123-123-1234)
        String phoneNumber = random.nextInt(999+1) + "-" + random.nextInt(999+1) + "-" + random.nextInt(9999+1);
        return new Contact(name,phoneNumber, context);
    }

    //Genera number contatti casuali e li restituisce all`interno di una Collection
    public Collection<Contact> generateContactCollection(int number){
        ArrayList<Contact> contacts = new ArrayList<>();

        for(int i=0; i<number; i++){
            contacts.add(generateContact());
        }
        return contacts;
    }
}
