package com.example.japanesereviewer123.ui.contentgrammar;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.japanesereviewer123.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class GrammarFragmentP2 extends Fragment {
    TextView txttopic,txtcontent;

    TextView nextBtn,prevBtn;
    AnimatorSet rightwardExitAnim,leftwardentranceAnim,rightEntranceAnim,leftExitAnim;
    private int currentIndexOriginal;
    LinearLayout linearidmiddle;
    private int currentIndexFiltered;
    private ArrayList<GrammarDataModel> originalData;
    private ArrayList<GrammarDataModel> filteredData;
    boolean isShow = false;
    String id,topic,content, level;
    ImageView imgbanner;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View viewRoot = inflater.inflate(R.layout.fragment_grammar_p2, container, false);

        txttopic = viewRoot.findViewById(R.id.idtopic);
        txtcontent = viewRoot.findViewById(R.id.idcontent);
        linearidmiddle = viewRoot.findViewById(R.id.idmiddle);



        // Retrieve the arguments bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            // Retrieve the string from the bundle
            id = bundle.getString("id");
            topic = bundle.getString("topic");
            content = bundle.getString("content");
            originalData = bundle.getParcelableArrayList("originalData"); // The original data
            filteredData = bundle.getParcelableArrayList("filteredData"); // The filtered data
            currentIndexFiltered = bundle.getInt("currentIndexFiltered", -1); // Default value if not found
            currentIndexOriginal = bundle.getInt("currentIndexOriginal", -2);
            level = bundle.getString("level");

        }



        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("JLPT " + level + " Grammar");
        }



        imgbanner = viewRoot.findViewById(R.id.idbanner);
        Uri bannerUri;

        switch (level) {
            case "N1":
                bannerUri = Uri.parse("android.resource://com.example.japanesereviewer123/" + R.drawable.n1banner);
                break;
            case "N2":
                bannerUri = Uri.parse("android.resource://com.example.japanesereviewer123/" + R.drawable.n2banner);
                break;
            case "N3":
                bannerUri = Uri.parse("android.resource://com.example.japanesereviewer123/" + R.drawable.n3banner);
                break;
            case "N4":
                bannerUri = Uri.parse("android.resource://com.example.japanesereviewer123/" + R.drawable.n4banner);
                break;
            case "N5":
                bannerUri = Uri.parse("android.resource://com.example.japanesereviewer123/" + R.drawable.n5banner);
                break;
            default:
                // Handle default case or set a default image
                bannerUri = null;
                break;
        }

        if (bannerUri != null) {
            Picasso.get()
                    .load(bannerUri)
                    .resize(2000, 0) // Resize the image to a smaller size
                    .onlyScaleDown() // Only scale down, don't scale up
                    .into(imgbanner); // imgbanner is your ImageView where you want to display the image
        }



        txttopic.setText(topic);
        txtcontent.setText(content);

        nextBtn = viewRoot.findViewById(R.id.idNextBtn);
        prevBtn = viewRoot.findViewById(R.id.idPrevBtn);

        rightwardExitAnim = new AnimatorSet();
        leftwardentranceAnim = new AnimatorSet();
        rightEntranceAnim = new AnimatorSet();
        leftExitAnim = new AnimatorSet();

        Context context = getContext(); // Replace with the appropriate context source

        rightwardExitAnim = (AnimatorSet) AnimatorInflater.loadAnimator(context,R.animator.rightexit_animator);
        leftwardentranceAnim = (AnimatorSet) AnimatorInflater.loadAnimator(context,R.animator.leftentrance_animator);
        rightEntranceAnim = (AnimatorSet) AnimatorInflater.loadAnimator(context,R.animator.rightentrance_animator);
        leftExitAnim = (AnimatorSet) AnimatorInflater.loadAnimator(context,R.animator.leftexit_animator);




        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer varOriginalDataSize = originalData.size() - 1;



                if (currentIndexOriginal >= varOriginalDataSize )
                {
                    Toast.makeText(getContext(),"You Reached the End",Toast.LENGTH_SHORT).show();
                }else {

                    currentIndexOriginal++;







                    rightwardExitAnim.setTarget(linearidmiddle);
                    rightwardExitAnim.start();



                    rightwardExitAnim.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(@NonNull Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(@NonNull Animator animation) {

                            leftwardentranceAnim.setTarget(linearidmiddle);
                            leftwardentranceAnim.start();

                            updateUI123();

                        }

                        @Override
                        public void onAnimationCancel(@NonNull Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(@NonNull Animator animation) {

                        }
                    });



                }

            }
        });


        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer varOriginalDataSize = originalData.size() - 1;


                if (currentIndexOriginal <= 0) {
                    Toast.makeText(getContext(),"You Reached the Beginning",Toast.LENGTH_SHORT).show();
                }else {
                    currentIndexOriginal--;





                    leftExitAnim.setTarget(linearidmiddle);
                    leftExitAnim.start();



                    leftExitAnim.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(@NonNull Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(@NonNull Animator animation) {



                            rightEntranceAnim.setTarget(linearidmiddle);
                            rightEntranceAnim.start();


                            updateUI123();

                        }

                        @Override
                        public void onAnimationCancel(@NonNull Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(@NonNull Animator animation) {

                        }
                    });




                }
            }
        });

        return viewRoot;


    }

    private void updateUI123() {
        GrammarDataModel selectedData = originalData.get(currentIndexOriginal);
        txttopic.setText(selectedData.getTopic());
        txtcontent.setText(selectedData.getContent());
    }

}