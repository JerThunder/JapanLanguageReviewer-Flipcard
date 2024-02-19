package com.example.japanesereviewer123;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class Vocabdetails_Acitivity extends AppCompatActivity {
    ImageView img1;
    String imageurl;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vocabdetails_layout);

        img1= findViewById(R.id.idfrontImage);
        imageurl = "https://i.imgur.com/F9xQ9gc.jpg";


        Picasso.get().load(imageurl).into(img1);


    }
}
