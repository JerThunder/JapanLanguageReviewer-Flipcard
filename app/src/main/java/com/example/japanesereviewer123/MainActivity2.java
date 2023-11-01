package com.example.japanesereviewer123;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
        TextView textFront,textBack,textSpecial;

        Button flipbtn,nextBtn,prevBtn;

    AnimatorSet frontAnim,backAnim,upwardAnim,downwardAnim,rightwardExitAnim,leftwardentranceAnim,rightEntranceAnim,leftExitAnim;
    private int currentIndexOriginal;
    private int currentIndexFiltered;
    boolean isFront = true;
    boolean isShow = false;
    private ArrayList<DataModel> originalData;
    private ArrayList<DataModel> filteredData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        textFront = findViewById(R.id.idfrontcard);
        textBack = findViewById(R.id.idbackcard);
        textSpecial = findViewById(R.id.idSpecial);
        flipbtn = findViewById(R.id.flipbtn);
        nextBtn = findViewById(R.id.idNextBtn);
        prevBtn = findViewById(R.id.idPrevBtn);



        frontAnim = new AnimatorSet();
        backAnim = new AnimatorSet();
        upwardAnim = new AnimatorSet();
        downwardAnim = new AnimatorSet();
        rightwardExitAnim = new AnimatorSet();
        leftwardentranceAnim = new AnimatorSet();
        rightEntranceAnim = new AnimatorSet();


        float scale = getApplicationContext().getResources().getDisplayMetrics().density;

        Intent intent = getIntent();

             originalData = intent.getParcelableArrayListExtra("originalData"); // The original data
             filteredData = intent.getParcelableArrayListExtra("filteredData"); // The filtered data

             currentIndexFiltered = intent.getIntExtra("currentIndexFiltered", -1); // Default value if not found
           currentIndexOriginal = intent.getIntExtra("currentIndexOriginal", -2);
           String id = intent.getStringExtra("id");
            String firstname = intent.getStringExtra("firstname");
            String lastname = intent.getStringExtra("lastname");


            Log.d("Debug", "MainActivity2_currentIndexOriginal: " + currentIndexOriginal);
            Log.d("Debug", "MainActivity2_currentIndexFiltered: " + currentIndexFiltered);

            Log.d("Debug","MainAcvitiy2_OriginalData: " + originalData);
        Log.d("Debug","MainAcvitiy2_FilteredData: " + filteredData);

            textFront.setText(firstname);
            textBack.setText(lastname);
        textFront.setCameraDistance(8000 * scale);
        textBack.setCameraDistance(8000 * scale);

        Context context = getApplicationContext(); // Replace with the appropriate context source

        frontAnim = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.front_animator);
        backAnim = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.back_animator);
        upwardAnim = (AnimatorSet) AnimatorInflater.loadAnimator(context,R.animator.upward_animator);
        downwardAnim = (AnimatorSet) AnimatorInflater.loadAnimator(context,R.animator.downward_animator);
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


                    if (isFront == true)
                    {
                        rightwardExitAnim.setTarget(textFront);
                        rightwardExitAnim.start();
                    } else {
                        rightwardExitAnim.setTarget(textBack);
                        rightwardExitAnim.start();
                    }


                     rightwardExitAnim.addListener(new Animator.AnimatorListener() {
                         @Override
                         public void onAnimationStart(@NonNull Animator animation) {

                         }

                         @Override
                         public void onAnimationEnd(@NonNull Animator animation) {

                             if (isFront == true)
                             {
                                 leftwardentranceAnim.setTarget(textFront);
                                 leftwardentranceAnim.start();
                             } else {
                                 leftwardentranceAnim.setTarget(textBack);
                                 leftwardentranceAnim.start();
                             }

                             updateUI123();




                         }

                         @Override
                         public void onAnimationCancel(@NonNull Animator animation) {

                         }

                         @Override
                         public void onAnimationRepeat(@NonNull Animator animation) {

                         }
                     });




                    Log.d("Debug","currentIndexOriginal: " + currentIndexOriginal);
                    Log.d("Debug","currentIndexOriginal: " + varOriginalDataSize);

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


                    if(isFront == true){
                        leftExitAnim.setTarget(textFront);
                        leftExitAnim.start();
                    } else{
                        leftExitAnim.setTarget(textBack);
                        leftExitAnim.start();
                    }




                    leftExitAnim.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(@NonNull Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(@NonNull Animator animation) {


                            if(isFront == true){
                                rightEntranceAnim.setTarget(textFront);
                                rightEntranceAnim.start();
                            } else{
                                rightEntranceAnim.setTarget(textBack);
                                rightEntranceAnim.start();
                            }


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




        textFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFront == true) {
                    frontAnim.setTarget(textFront);
                    frontAnim.start();

                    frontAnim.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(@NonNull Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(@NonNull Animator animation) {

                            backAnim.setTarget(textBack);
                            backAnim.start();
                            isFront = false;
                            textSpecial.setAlpha(0.0f);
                            isShow= false;
                        }

                        @Override
                        public void onAnimationCancel(@NonNull Animator animation) {

                        }
                        @Override
                        public void onAnimationRepeat(@NonNull Animator animation) {
                        }
                    });


                } else {

                    backAnim.setTarget(textFront);
                    backAnim.start();

                    backAnim.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(@NonNull Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(@NonNull Animator animation) {
                            frontAnim.setTarget(textBack);
                            frontAnim.start();
                            isFront = true;
                            textSpecial.setAlpha(0.0f);
                            isShow= false;
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


        textBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFront) {
                    frontAnim.setTarget(textFront);
                    backAnim.setTarget(textBack);
                    frontAnim.start();
                    backAnim.start();
                    isFront = false;

                    if(isShow == true)
                    {
                        downwardAnim.setTarget(textSpecial);
                        downwardAnim.start();
                        isShow = false;
                    }



                } else {
                    frontAnim.setTarget(textBack);
                    backAnim.setTarget(textFront);
                    backAnim.start();
                    frontAnim.start();
                    isFront = true;

                    if(isShow == true)
                    {
                        downwardAnim.setTarget(textSpecial);
                        downwardAnim.start();
                        isShow = false;
                    }
                }

            }
        });



        flipbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)   {
                if (!isShow)
                {
                    textSpecial.setAlpha(1.0f);
                    upwardAnim.setTarget(textSpecial);
                    upwardAnim.start();
                    isShow= true;
                }else{

                   textSpecial.setAlpha(0.0f);
                    downwardAnim.setTarget(textSpecial);
                    downwardAnim.start();
                    isShow = false;
                }
            }
        });






    }


    private void updateUI123() {
        DataModel selectedData = originalData.get(currentIndexOriginal);
        textFront.setText(selectedData.getFirstname());
        textBack.setText(selectedData.getLastname());
    }


}
