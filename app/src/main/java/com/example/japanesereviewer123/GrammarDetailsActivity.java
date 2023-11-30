package com.example.japanesereviewer123;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class GrammarDetailsActivity extends AppCompatActivity {

    TextView txttopic,txtcontent;
    Button nextBtn,prevBtn;
    AnimatorSet rightwardExitAnim,leftwardentranceAnim,rightEntranceAnim,leftExitAnim;
    private int currentIndexOriginal;
    LinearLayout linearidmiddle;
    private int currentIndexFiltered;
    private ArrayList<GrammarDataModel> originalData;
    private ArrayList<GrammarDataModel> filteredData;
    boolean isShow = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grammardetails_layout);

        txttopic = findViewById(R.id.idtopic);
        txtcontent = findViewById(R.id.idcontent);
        linearidmiddle = findViewById(R.id.idmiddle);

        Intent intent = getIntent();

        String id = intent.getStringExtra("id");
        String topic = intent.getStringExtra("topic");
        String content = intent.getStringExtra("content");
        originalData = intent.getParcelableArrayListExtra("originalData"); // The original data
        filteredData = intent.getParcelableArrayListExtra("filteredData"); // The filtered data

        Log.d("OriginalData", "OriginalData " + originalData);
        Log.d("filterdData","FilteredData " + filteredData);



        currentIndexFiltered = intent.getIntExtra("currentIndexFiltered", -1); // Default value if not found
        currentIndexOriginal = intent.getIntExtra("currentIndexOriginal", -2);





        txttopic.setText(topic);
        txtcontent.setText(content);

        nextBtn = findViewById(R.id.idNextBtn);
        prevBtn = findViewById(R.id.idPrevBtn);

        rightwardExitAnim = new AnimatorSet();
        leftwardentranceAnim = new AnimatorSet();
        rightEntranceAnim = new AnimatorSet();
        leftExitAnim = new AnimatorSet();

        Context context = getApplicationContext(); // Replace with the appropriate context source

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
                    Toast.makeText(getApplicationContext(),"You Reached the End",Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getApplicationContext(),"You Reached the Beginning",Toast.LENGTH_SHORT).show();
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



    }


    private void updateUI123() {
        GrammarDataModel selectedData = originalData.get(currentIndexOriginal);
        txttopic.setText(selectedData.getTopic());
        txtcontent.setText(selectedData.getContent());
    }

}
