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

public class Vocab_Adapter extends ArrayAdapter<GoiDataModel> {


    private Context mContext;
    private int mResource;
    public Vocab_Adapter(@NonNull Context context, int resource, @NonNull ArrayList<GoiDataModel> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(mResource,parent,false);

        TextView txtWords = convertView.findViewById(R.id.txtWords);
        TextView txtGoi = convertView.findViewById(R.id.txtGoi);

        txtWords.setText("Words: " + getItem(position).getVocab());
        txtGoi.setText("Goi: " + getItem(position).getHiragana());

        return convertView;
    }
}
