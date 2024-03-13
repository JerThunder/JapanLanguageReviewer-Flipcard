package com.example.japanesereviewer123.ui.contentkanji;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.japanesereviewer123.R;

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
    private Kanjibreakdown_Adapter kanjibreakdown_adapter;
    String kanji,meaning,hiragana;

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
        }

        //ExecutorService Functions (Alternative of AsyncTask)
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
            String data = "";

            @Override
            public void run() {
                try {
                    URL url = new URL("https://animerepository.com/110796/test.php?s=" + kanji);
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
        ViewGroup containerView = (ViewGroup) getView();
        containerView.removeAllViews();
        containerView.addView(rootView);

    }
}