package com.example.listacontatti;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<Contact> {
    private LayoutInflater inflater;
    public CustomAdapter(Context context, int resourceId, List<Contact> objects){
        super(context, resourceId, objects);
        inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View v, ViewGroup parent){
        if(v==null){
            v = inflater.inflate(R.layout.list_item, null);
        }

        Contact c = getItem(position);

        TextView name = v.findViewById(R.id.contactName);
        TextView phone = v.findViewById(R.id.contactPhone);

        name.setText("" + c.getSurname() + " " + c.getName());
        phone.setText("" + c.getTelephone());

        name.setTag(position);
        phone.setTag(position);

        return v;
    }
}
