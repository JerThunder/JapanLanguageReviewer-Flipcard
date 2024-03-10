package com.example.japanesereviewer123;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Kanjibreakdown_Adapter extends ArrayAdapter<Kanjibreakdown_Model> {

    private Context mContext;
    private int mResource;

    public Kanjibreakdown_Adapter(@NonNull Context context, int resource, @NonNull ArrayList<Kanjibreakdown_Model> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(mResource,parent,false);

        ImageView imageView = convertView.findViewById(R.id.kanjiImage);
        TextView txtTitle = convertView.findViewById(R.id.txtTitle);
        TextView txtKunyomi = convertView.findViewById(R.id.txtkunyomi);
        TextView txtOnyomi = convertView.findViewById(R.id.txtonyomi);
        TextView txtlevel = convertView.findViewById(R.id.txtlevel);

        Picasso.get().load(getItem(position).getImageUrl()).into(imageView);
        txtTitle.setText(getItem(position).getKanjiMeaning());
        txtKunyomi.setText("Kun: " + getItem(position).getKunyomi());
        txtOnyomi.setText("On: " + getItem(position).getOnyomi());
        txtlevel.setText("Level: " + getItem(position).getLevel());

        return convertView;

    }
}
