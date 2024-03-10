package com.example.japanesereviewer123;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Random;

public class Vocabdetails_Activity extends AppCompatActivity {
    ImageView img1;
    String imageurl;
    TextView textBack, txtvocab;
    Button nextBtn, prevBtn, btnPlay, btnrandom;
    ConstraintLayout testlayout;
    AnimatorSet frontAnim, backAnim, upwardAnim, downwardAnim, rightwardExitAnim, leftwardentranceAnim, rightEntranceAnim, leftExitAnim;
    private int currentIndexOriginal;
    private int currentIndexFiltered;
    boolean isFront = true;
    boolean isShow = false;
    private ArrayList<GoiDataModel> originalData;
    private ArrayList<GoiDataModel> filteredData;
    private TextToSpeech textToSpeech;
    private static final int TTS_CHECK_CODE = 1;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vocabdetails_layout);

        img1= findViewById(R.id.idfrontImage);

        txtvocab = findViewById(R.id.txtvocab);
        textBack = findViewById(R.id.idbackcard);
        nextBtn = findViewById(R.id.idNextBtn);
        prevBtn = findViewById(R.id.idPrevBtn);
        btnPlay = findViewById(R.id.idPlay);
        btnrandom = findViewById(R.id.idbtnrandomizer);
        testlayout = findViewById(R.id.testlayout);


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
        String vocab = intent.getStringExtra("vocab");
        String hiragana = intent.getStringExtra("hiragana");
        String imageurl = intent.getStringExtra("imageurl");


        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = textToSpeech.setLanguage(Locale.JAPAN);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        // Handle language data missing or not supported.

                        showLanguageDataDownloadDialog();

                    }
                } else {
                    // Handle TTS initialization failure.
                }
            }
        });


        txtvocab.setText("Goi \n" + vocab );
        textBack.setText("HIRAGANA \n" + hiragana);
        Picasso.get().load(imageurl).into(img1);


        img1.setCameraDistance(8000 * scale);
        textBack.setCameraDistance(8000 * scale);

        Context context = getApplicationContext(); // Replace with the appropriate context source

        frontAnim = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.front_animator);
        backAnim = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.back_animator);
        upwardAnim = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.upward_animator);
        downwardAnim = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.downward_animator);
        rightwardExitAnim = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.rightexit_animator);
        leftwardentranceAnim = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.leftentrance_animator);
        rightEntranceAnim = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.rightentrance_animator);
        leftExitAnim = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.leftexit_animator);


        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                speakJapanese();
                // String textSpeak = "こにちは";
                //   textToSpeech.speak(textSpeak,textToSpeech.QUEUE_FLUSH,null,null);
            }
        });


        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer varOriginalDataSize = originalData.size() - 1;

                if (currentIndexOriginal >= varOriginalDataSize) {
                    Toast.makeText(getApplicationContext(), "You Reached the End", Toast.LENGTH_SHORT).show();
                } else {

                    currentIndexOriginal++;

                    if (isFront == true) {
                        rightwardExitAnim.setTarget(testlayout);

                        rightwardExitAnim.start();


                    } else {
                        rightwardExitAnim.setTarget(testlayout);

                        rightwardExitAnim.start();

                    }


                    rightwardExitAnim.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(@NonNull Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(@NonNull Animator animation) {

                            if (isFront == true) {
                                leftwardentranceAnim.setTarget(testlayout);

                                leftwardentranceAnim.start();
                            } else {
                                leftwardentranceAnim.setTarget(testlayout);

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


                }

            }
        });


        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer varOriginalDataSize = originalData.size() - 1;


                if (currentIndexOriginal <= 0) {
                    Toast.makeText(getApplicationContext(), "You Reached the Beginning", Toast.LENGTH_SHORT).show();
                } else {
                    currentIndexOriginal--;


                    if (isFront == true) {
                        leftExitAnim.setTarget(testlayout);
                        leftExitAnim.start();

                    } else {
                        leftExitAnim.setTarget(testlayout);
                        leftExitAnim.start();
                    }


                    leftExitAnim.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(@NonNull Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(@NonNull Animator animation) {


                            if (isFront == true) {
                                rightEntranceAnim.setTarget(testlayout);
                                rightEntranceAnim.start();
                            } else {
                                rightEntranceAnim.setTarget(testlayout);
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


        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFront == true) {
                    frontAnim.setTarget(img1);
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
                            isShow = false;
                        }

                        @Override
                        public void onAnimationCancel(@NonNull Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(@NonNull Animator animation) {
                        }
                    });


                } else {

                    backAnim.setTarget(textBack);
                    backAnim.start();

                    backAnim.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(@NonNull Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(@NonNull Animator animation) {
                            frontAnim.setTarget(img1);
                            frontAnim.start();
                            isFront = true;
                            isShow = false;
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
                    frontAnim.setTarget(img1);
                    backAnim.setTarget(textBack);
                    frontAnim.start();
                    backAnim.start();
                    isFront = false;

                } else {
                    frontAnim.setTarget(textBack);
                    backAnim.setTarget(img1);
                    backAnim.start();
                    frontAnim.start();
                    isFront = true;

                }

            }
        });



        btnrandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                randomizer();

            }
        });


    }


    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }


    private void showLanguageDataDownloadDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Language Data Missing");
        builder.setMessage("The required language data is missing. Do you want to download it?");
        builder.setPositiveButton("Download", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Launch an intent to download the language data.
                Intent installIntent = new Intent();
                installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installIntent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle the user's choice to cancel the download.
            }
        });
        builder.create().show();
    }


    private void updateUI123() {
        GoiDataModel selectedData = originalData.get(currentIndexOriginal);
        txtvocab.setText("GOI \n" + selectedData.getVocab());
        textBack.setText("HIRAGANA \n" + selectedData.getHiragana());
        imageurl = selectedData.getImageurl();
        Picasso.get().load(imageurl).into(img1);
    }



    private void randomizer() {

        Collections.shuffle(originalData, new Random());
        currentIndexOriginal = 0;


        if (isFront == true) {
            rightwardExitAnim.setTarget(testlayout);
            rightwardExitAnim.start();


        } else {
            rightwardExitAnim.setTarget(testlayout);
            rightwardExitAnim.start();

        }


        rightwardExitAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(@NonNull Animator animation) {

            }

            @Override
            public void onAnimationEnd(@NonNull Animator animation) {

                if (isFront == true) {
                    leftwardentranceAnim.setTarget(testlayout);
                    leftwardentranceAnim.start();
                } else {
                    leftwardentranceAnim.setTarget(testlayout);
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
    }



    private void speakJapanese()
    {


        GoiDataModel selectedData = originalData.get(currentIndexOriginal);
        String speakThis = selectedData.getHiragana();
        textToSpeech.speak(speakThis,textToSpeech.QUEUE_FLUSH,null,null);
    }


}