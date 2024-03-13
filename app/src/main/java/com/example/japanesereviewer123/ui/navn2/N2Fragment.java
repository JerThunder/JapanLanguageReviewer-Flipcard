package com.example.japanesereviewer123.ui.navn2;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.japanesereviewer123.R;
import com.example.japanesereviewer123.databinding.FragmentN2Binding;



public class N2Fragment extends Fragment {
    private FragmentN2Binding binding;
    private CardView cardViewKanji,cardViewGrammar,cardViewVocab,cardViewExercise;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        binding = FragmentN2Binding.inflate(inflater, container, false);
        View root = binding.getRoot();

        cardViewKanji = root.findViewById(R.id.cardViewKanji);
        cardViewGrammar = root.findViewById(R.id.cardViewGrammar);
        cardViewVocab = root.findViewById(R.id.cardViewVocab);
        cardViewExercise = root.findViewById(R.id.cardViewExercises);

        cardViewKanji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_n4Fragment_to_kanjiFragment);
            }
        });

        cardViewGrammar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_n4Fragment_to_grammarFragment);
            }
        });


        cardViewVocab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_n4Fragment_to_vocabFragment);
            }
        });

        cardViewExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(getContext(), "Under Construction", Toast.LENGTH_SHORT).show();


            }
        });

        // Inflate the layout for this fragment
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}