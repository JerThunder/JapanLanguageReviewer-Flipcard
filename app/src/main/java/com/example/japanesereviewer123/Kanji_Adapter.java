package com.example.japanesereviewer123;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Kanji_Adapter extends ArrayAdapter<DataModel> {


    private Context mContext;
    private int mResource;
    public Kanji_Adapter(@NonNull Context context, int resource, @NonNull ArrayList<DataModel> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(mResource,parent,false);

        TextView txtkanjiwords = convertView.findViewById(R.id.txtKanjiwords);
        TextView txthiraganawords = convertView.findViewById(R.id.txtHiraganaWords);

        txtkanjiwords.setText("Kanji: " + getItem(position).getkanji());
        txthiraganawords.setText("Hiragana: " + getItem(position).gethiragana());

        return convertView;
    }
}
