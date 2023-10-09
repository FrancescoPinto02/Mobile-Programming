package com.example.rubrica;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<Contact> {

    private LayoutInflater inflater;

    public CustomAdapter(Context context, int resourceId, List<Contact> objects){
        super(context, resourceId, objects);
        inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View v, ViewGroup parent){
        if(v==null){
            Log.d("DEBUG", "Inflating view");
            v = inflater.inflate(R.layout.list_element, null);
        }

        Contact c = getItem(position);
        Log.d("DEBUG","contact c="+c);

        Button nameButton = v.findViewById(R.id.contactName);
        Button telButton = v.findViewById(R.id.contactTel);
        ImageButton photoButton = v.findViewById(R.id.contactPhoto);

        photoButton.setImageDrawable(c.getPicture());
        nameButton.setText(c.getName());
        telButton.setText(c.getPhoneNumber());

        photoButton.setTag(position);
        nameButton.setTag(position);
        telButton.setTag(position);

        return v;
    }
}
