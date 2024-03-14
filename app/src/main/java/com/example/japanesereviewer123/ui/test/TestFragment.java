package com.example.japanesereviewer123.ui.test;

import android.animation.AnimatorSet;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.japanesereviewer123.R;
import com.example.japanesereviewer123.ui.contentkanji.DataModel;

import java.util.ArrayList;


public class TestFragment extends Fragment {
    private int currentIndexOriginal;
    private int currentIndexFiltered;
    private ArrayList<DataModel> originalData;
    private ArrayList<DataModel> filteredData;
    String id,kanji,hiragana,meaning, level;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View viewRoot = inflater.inflate(R.layout.fragment_test, container, false);


        // Retrieve the arguments bundle
        Bundle bundle = getArguments();
        if (bundle != null) {

            id = bundle.getString("id");
            kanji = bundle.getString("kanji");
            hiragana = bundle.getString("hiragana");
            meaning = bundle.getString("meaning");
            originalData = bundle.getParcelableArrayList("originalData"); // The original data
            filteredData = bundle.getParcelableArrayList("filteredData"); // The filtered data
            currentIndexFiltered = bundle.getInt("currentIndexFiltered", -1); // Default value if not found
            currentIndexOriginal = bundle.getInt("currentIndexOriginal", -2);
            level = bundle.getString("level");

        }


        Toast.makeText(getContext(), kanji, Toast.LENGTH_SHORT).show();



        return viewRoot;
    }
}