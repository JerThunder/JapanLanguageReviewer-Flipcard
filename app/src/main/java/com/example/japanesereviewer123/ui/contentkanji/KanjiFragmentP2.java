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

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.japanesereviewer123.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Random;


public class KanjiFragmentP2 extends Fragment {
    TextView textFront, textBack;
    TextView btndetails,btnPlay, btnrandom,nextBtn, prevBtn;
    AnimatorSet frontAnim, backAnim, upwardAnim, downwardAnim, rightwardExitAnim, leftwardentranceAnim, rightEntranceAnim, leftExitAnim;
    private int currentIndexOriginal;
    private int currentIndexFiltered;
    boolean isFront = true;
    private ArrayList<DataModel> originalData;
    private ArrayList<DataModel> filteredData;
    private TextToSpeech textToSpeech;
    private static final int TTS_CHECK_CODE = 1;
    String passKanji,passMeaning,passhiragana;
    String id,kanji,hiragana,meaning, level;
    ImageView imgbanner;
    Uri bannerUri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.fragment_kanji_p2, container, false);


        textFront = viewRoot.findViewById(R.id.idfrontcard);
        textBack = viewRoot.findViewById(R.id.idbackcard);
        nextBtn = viewRoot.findViewById(R.id.idNextBtn);
        prevBtn = viewRoot.findViewById(R.id.idPrevBtn);
        btndetails = viewRoot.findViewById(R.id.idDetails);
        btnPlay = viewRoot.findViewById(R.id.idPlay);
        btnrandom = viewRoot.findViewById(R.id.idbtnrandomizer);
        imgbanner = viewRoot.findViewById(R.id.idbanner);

        frontAnim = new AnimatorSet();
        backAnim = new AnimatorSet();
        upwardAnim = new AnimatorSet();
        downwardAnim = new AnimatorSet();
        rightwardExitAnim = new AnimatorSet();
        leftwardentranceAnim = new AnimatorSet();
        rightEntranceAnim = new AnimatorSet();


        float scale = getContext().getResources().getDisplayMetrics().density;


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


        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("JLPT " + level + " Kanji");
        }




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






        textToSpeech = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
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


        textFront.setText("KANJI \n" + kanji);
        textBack.setText("HIRAGANA \n" + hiragana + "\n\n" + "MEANING \n" + meaning);

        textFront.setCameraDistance(8000 * scale);
        textBack.setCameraDistance(8000 * scale);

        Context context = getContext(); // Replace with the appropriate context source

        frontAnim = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.front_animator);
        backAnim = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.back_animator);
        upwardAnim = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.upward_animator);
        downwardAnim = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.downward_animator);
        rightwardExitAnim = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.rightexit_animator);
        leftwardentranceAnim = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.leftentrance_animator);
        rightEntranceAnim = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.rightentrance_animator);
        leftExitAnim = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.leftexit_animator);



        btndetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                NavController navController = Navigation.findNavController(requireView());

                // Put the data into a bundle
                Bundle bundle123 = new Bundle();

                bundle123.putString("id",id);
                bundle123.putString("hiragana",hiragana);
                bundle123.putString("kanji",kanji);
                bundle123.putString("meaning",meaning);
                bundle123.putString("level",level);
                bundle123.putParcelableArrayList("originalData",originalData);
                bundle123.putParcelableArrayList("filteredData",filteredData);
                bundle123.putInt("currentIndexFiltered",currentIndexFiltered);
                bundle123.putInt("currentIndexOriginal",currentIndexOriginal);
                // Navigate to the kanjiFragment and pass the arguments
                navController.navigate(R.id.action_kanjifragment2_to_page3, bundle123);


            }
        });


        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavController navController = Navigation.findNavController(requireView());

                // Put the data into a bundle
                Bundle bundle123 = new Bundle();

                navController.navigate(R.id.action_kanjifragmentp2_to_doodle, bundle123);
            }
        });


        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer varOriginalDataSize = originalData.size() - 1;

                if (currentIndexOriginal >= varOriginalDataSize) {
                    Toast.makeText(getContext(), "You Reached the End", Toast.LENGTH_SHORT).show();
                } else {

                    currentIndexOriginal++;


                    if (isFront == true) {
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

                            if (isFront == true) {
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


                }

            }
        });


        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer varOriginalDataSize = originalData.size() - 1;


                if (currentIndexOriginal <= 0) {
                    Toast.makeText(getContext(), "You Reached the Beginning", Toast.LENGTH_SHORT).show();
                } else {
                    currentIndexOriginal--;


                    if (isFront == true) {
                        leftExitAnim.setTarget(textFront);
                        leftExitAnim.start();

                    } else {
                        leftExitAnim.setTarget(textBack);
                        leftExitAnim.start();
                    }


                    leftExitAnim.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(@NonNull Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(@NonNull Animator animation) {


                            if (isFront == true) {
                                rightEntranceAnim.setTarget(textFront);
                                rightEntranceAnim.start();
                            } else {
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


                } else {
                    frontAnim.setTarget(textBack);
                    backAnim.setTarget(textFront);
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



        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Get the NavController
                NavController navController = Navigation.findNavController(requireView());

                // Assuming you have an action to navigate back
                int actionId = R.id.action_kanjifragmentp2_to_Backp1;

                // Create a Bundle to hold your data
                Bundle bundle = new Bundle();

                bundle.putString("level", level);


                // Log the values of the variables before navigation
                Log.d("NavigationLog", "Action ID: " + actionId);
                Log.d("NavigationLog", "Bundle: " + bundle.toString());

                // Navigate back to the previous destination and pass the arguments
                navController.navigate(actionId, bundle);
            }
        });



        return viewRoot;

    }



   // protected void onDestroy() {
     //   if (textToSpeech != null) {
     //       textToSpeech.stop();
     //       textToSpeech.shutdown();
     //   }
     //   super.onDestroy();
 //   }


    private void showLanguageDataDownloadDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
        DataModel selectedData = originalData.get(currentIndexOriginal);
        textFront.setText("KANJI \n" + selectedData.getkanji());
        textBack.setText("HIRAGANA \n" + selectedData.gethiragana() + "\n\n" + "MEANING \n" + selectedData.getMeaning());
        kanji = selectedData.getkanji();
        hiragana = selectedData.gethiragana();
        meaning = selectedData.getMeaning();



    }



    private void randomizer() {

        Collections.shuffle(originalData, new Random());
        currentIndexOriginal = 0;


        if (isFront == true) {
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

                if (isFront == true) {
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
    }



    private void speakJapanese()
    {


        DataModel selectedData = originalData.get(currentIndexOriginal);
        String speakThis = selectedData.getkanji();
        textToSpeech.speak(speakThis,textToSpeech.QUEUE_FLUSH,null,null);
    }

}