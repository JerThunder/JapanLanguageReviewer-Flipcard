package com.example.japanesereviewer123.ui.contentkanji;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.japanesereviewer123.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Random;

import me.panavtec.drawableview.DrawableView;
import me.panavtec.drawableview.DrawableViewConfig;


public class KanjiFragmentDoodle extends Fragment {

DrawableView drawableView;
TextView undo;
DrawableViewConfig config;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View viewRoot = inflater.inflate(R.layout.fragment_kanji_doodle, container, false);



        drawableView = viewRoot.findViewById(R.id.paintview);


        undo = viewRoot.findViewById(R.id.btnUndo);

        config = new DrawableViewConfig();

        config.setStrokeColor(getResources().getColor(android.R.color.holo_red_dark));

        //config.setShowCanvasBounds(true);
        config.setMinZoom(1.0f);
        config.setMaxZoom(3.0f);
        config.setCanvasHeight(1920);
        config.setStrokeWidth(30);
        config.setCanvasWidth(1920);
        drawableView.setConfig(config);



        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawableView.undo();
            }
        });




        return viewRoot;
    }
}