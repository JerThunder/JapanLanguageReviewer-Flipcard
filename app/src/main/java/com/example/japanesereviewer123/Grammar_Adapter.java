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

public class Grammar_Adapter extends ArrayAdapter<GrammarDataModel> {


    private Context mContext;
    private int mResource;
    public Grammar_Adapter(@NonNull Context context, int resource, @NonNull ArrayList<GrammarDataModel> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(mResource,parent,false);

        TextView txtgrammar123 = convertView.findViewById(R.id.txtgrammar123);

        txtgrammar123.setText(getItem(position).getTopic());
        return convertView;
    }
}
