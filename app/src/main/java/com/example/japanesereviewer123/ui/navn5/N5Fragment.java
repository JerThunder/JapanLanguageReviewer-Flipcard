package com.example.japanesereviewer123.ui.navn5;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;


import com.example.japanesereviewer123.R;
import com.example.japanesereviewer123.databinding.FragmentN5Binding;

public class N5Fragment extends Fragment {

    private FragmentN5Binding binding;
    private CardView cardViewKanji,cardViewGrammar,cardViewVocab,cardViewExercise;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentN5Binding.inflate(inflater, container, false);
        View root = binding.getRoot();

        cardViewKanji = root.findViewById(R.id.cardViewKanji);
        cardViewGrammar = root.findViewById(R.id.cardViewGrammar);
        cardViewVocab = root.findViewById(R.id.cardViewVocab);
        cardViewExercise = root.findViewById(R.id.cardViewExercises);

        cardViewKanji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavController navController = Navigation.findNavController(requireView());
                // Put the data into a bundle
                Bundle bundle = new Bundle();
                bundle.putString("level","N5");
                navController.navigate(R.id.action_n5Fragment_to_kanjiFragment, bundle);



            }
        });

        cardViewGrammar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavController navController = Navigation.findNavController(requireView());
                // Put the data into a bundle
                Bundle bundle = new Bundle();
                bundle.putString("level","N5");
                navController.navigate(R.id.action_n5Fragment_to_grammarFragment, bundle);
            }
        });


        cardViewVocab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(requireView());
                // Put the data into a bundle
                Bundle bundle = new Bundle();
                bundle.putString("level","N5");
                navController.navigate(R.id.action_n5Fragment_to_vocabFragment, bundle);
            }
        });

        cardViewExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Toast.makeText(getContext(), "Under Construction", Toast.LENGTH_SHORT).show();
            }
        });



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}