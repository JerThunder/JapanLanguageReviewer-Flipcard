package com.example.japanesereviewer123.ui.contentkanji;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.japanesereviewer123.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class KanjiFragmentP3 extends Fragment {
    ListView listview;
    TextView txtkanji,txtbreakdownmeaning,txtbreakdownhiragana;
    private ArrayList<Kanjibreakdown_Model> originalData;
    private ArrayList<Kanjibreakdown_Model> filteredData;
    private ArrayList<DataModel> originalDataFragment2;
    private ArrayList<DataModel> filteredDataFragment2;
    private Kanjibreakdown_Adapter kanjibreakdown_adapter;
    String kanji,meaning,hiragana, level;
    private int currentIndexOriginal;
    private int currentIndexFiltered;
    ImageView imgbanner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View loadingView = inflater.inflate(R.layout.loading_screen, container, false);



        originalData = new ArrayList<>();
        filteredData = new ArrayList<>();


        // Retrieve the arguments bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            // Retrieve the string from the bundle

            kanji = bundle.getString("kanji");
            hiragana = bundle.getString("hiragana");
            meaning = bundle.getString("meaning");
            level = bundle.getString("level");
            originalDataFragment2 = bundle.getParcelableArrayList("originalData");
            filteredDataFragment2 = bundle.getParcelableArrayList("filteredData");
            currentIndexOriginal = bundle.getInt("currentIndexOriginal");
            currentIndexFiltered = bundle.getInt("currentIndexFiltered");

        }

        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("JLPT " + level + " Kanji");
        }



        //ExecutorService Functions (Alternative of AsyncTask)
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
            String data = "";

            @Override
            public void run() {
                try {
                    URL url = new URL("https://jer101.shop/110796/indexjapan110796b.php?s=" + kanji);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;

                    while ((line = bufferedReader.readLine()) != null) {
                        data += line;
                    }

                    JSONObject jsonObject = new JSONObject(data);

                    synchronized (originalData) {
                        originalData.clear();
                        filteredData.clear();

                        // Iterate over the keys (Kanji characters) in the JSONObject
                        Iterator<String> keys = jsonObject.keys();
                        while (keys.hasNext()) {
                            String kanjiChar = keys.next();
                            JSONArray jsonArray = jsonObject.getJSONArray(kanjiChar);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject kanjiObject = jsonArray.getJSONObject(i);
                                String id = kanjiObject.getString("id");
                                String kanjidesc = kanjiObject.getString("kanjidesc");
                                String kanjiMeaning = kanjiObject.getString("kanjimeaning");
                                String imageurl = kanjiObject.getString("imageurl");
                                String kunyomi = kanjiObject.getString("kunyomi");
                                String onyomi = kanjiObject.getString("onyomi");
                                String level = kanjiObject.getString("level");
                                originalData.add(new Kanjibreakdown_Model(id, kanjiChar,kanjiMeaning, kanjidesc, imageurl,kunyomi,onyomi,level));
                                filteredData.add(new Kanjibreakdown_Model(id, kanjiChar,kanjiMeaning, kanjidesc, imageurl,kunyomi,onyomi,level));
                            }
                        }
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            switchtoMainLayout();
                            // listviewOnClick();
                            kanjibreakdown_adapter.notifyDataSetChanged();
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });



        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Get the NavController
                NavController navController = Navigation.findNavController(requireView());

                // Assuming you have an action to navigate back
                int actionId = R.id.action_kanjifragmentp3_to_Backp2;

                // Create a Bundle to hold your data
                Bundle bundle = new Bundle();
                bundle.putString("hiragana", hiragana);
                bundle.putString("kanji", kanji);
                bundle.putString("meaning", meaning);
                bundle.putString("level", level);
                bundle.putParcelableArrayList("originalData",originalDataFragment2);
                bundle.putParcelableArrayList("filteredData",filteredDataFragment2);
                bundle.putInt("currentIndexOriginal",currentIndexOriginal);
                bundle.putInt("currentIndexFiltered",currentIndexFiltered);



                // Log the values of the variables before navigation
                Log.d("NavigationLog", "Action ID: " + actionId);
                Log.d("NavigationLog", "Bundle: " + bundle.toString());

                // Navigate back to the previous destination and pass the arguments
                navController.navigate(actionId, bundle);
            }
        });






        return loadingView;
    }

    @SuppressLint("MissingInflatedId")
    private void switchtoMainLayout() {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_kanji_p3, (ViewGroup) getView(), false);


        // Get a reference to the ListView in the main layout
        listview = rootView.findViewById(R.id.breakdownlist);
        txtkanji = rootView.findViewById(R.id.txtkanji);
        txtbreakdownhiragana = rootView.findViewById(R.id.txtbreakdownhiragana);
        txtbreakdownmeaning = rootView.findViewById(R.id.txtbreakdownMeaning);
        kanjibreakdown_adapter = new Kanjibreakdown_Adapter(getContext(), R.layout.kanjibreakdown_listrow, filteredData);
        listview.setAdapter(kanjibreakdown_adapter);
        txtkanji.setText(kanji);
        txtbreakdownmeaning.setText(meaning);
        txtbreakdownhiragana.setText(hiragana);


        imgbanner = rootView.findViewById(R.id.idbanner);
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


        ViewGroup containerView = (ViewGroup) getView();
        containerView.removeAllViews();
        containerView.addView(rootView);

    }



}