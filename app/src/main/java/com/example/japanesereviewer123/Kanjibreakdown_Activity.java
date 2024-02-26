package com.example.japanesereviewer123;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

public class Kanjibreakdown_Activity extends AppCompatActivity {
    ListView listview;
    TextView txtkanji,txtbreakdownmeaning,txtbreakdownhiragana;
    private ArrayList<Kanjibreakdown_Model> originalData;
    private ArrayList<Kanjibreakdown_Model> filteredData;
    private Kanjibreakdown_Adapter kanjibreakdown_adapter;
    String kanji,meaning,hiragana;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_screen);



        originalData = new ArrayList<>();
        filteredData = new ArrayList<>();



        Intent intent = getIntent();

        kanji = intent.getStringExtra("kanji");
        meaning = intent.getStringExtra("meaning");
        hiragana = intent.getStringExtra("hiragana");

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
                                originalData.add(new Kanjibreakdown_Model(id, kanjiChar,kanjiMeaning, kanjidesc, imageurl));
                                filteredData.add(new Kanjibreakdown_Model(id, kanjiChar,kanjiMeaning, kanjidesc, imageurl));
                            }
                        }
                    }

                    runOnUiThread(new Runnable() {
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
    }


    private void switchtoMainLayout() {
        setContentView(R.layout.kanji_breakdown);

        // Get a reference to the ListView in the main layout
        listview = findViewById(R.id.breakdownlist);
        txtkanji = findViewById(R.id.txtkanji);
        txtbreakdownhiragana = findViewById(R.id.txtbreakdownhiragana);
        txtbreakdownmeaning = findViewById(R.id.txtbreakdownMeaning);
        kanjibreakdown_adapter = new Kanjibreakdown_Adapter(this, R.layout.kanjibreakdown_listrow, filteredData);
        listview.setAdapter(kanjibreakdown_adapter);
        txtkanji.setText(kanji);
        txtbreakdownmeaning.setText(meaning);
        txtbreakdownhiragana.setText(hiragana);

    }


}
