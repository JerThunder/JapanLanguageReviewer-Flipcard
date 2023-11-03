package com.example.japanesereviewer123;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.Transliterator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.widget.SearchView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private ArrayList<DataModel> originalData;
    private ArrayList<DataModel> filteredData;
    private ListView listView;
    private ArrayAdapter<DataModel> adapter;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_screen);


        originalData = new ArrayList<>();
        filteredData = new ArrayList<>();


        //ExecutorService Functions (Alternative of Asynctask)
        ExecutorService service  = Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
            String data =  "";
            @Override
            public void run() {
                //onPreExecute
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });

                //do in background
                try {
                    URL url = new URL("https://animerepository.com/110796/indexjapan110796.php");
                    HttpURLConnection httpURLConnection  = (HttpURLConnection) url.openConnection();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String line = "";

                    while (line != null){
                        line = bufferedReader.readLine();
                        data = data + line;

                    }
                    Log.d("JSON Response", data);
                    JSONArray jsonArray = new JSONArray(data);
                    originalData.clear();
                    filteredData.clear();
                    for (int i = 0; i<jsonArray.length(); i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String kanji = jsonObject.getString("kanji");
                        String hiragana = jsonObject.getString("hiragana");
                        String meaning = jsonObject.getString("meaning");
                        originalData.add(new DataModel(id,kanji,hiragana,meaning));
                        filteredData.add(new DataModel(id, kanji, hiragana,meaning));

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                //OnPostExecute
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        switchtoMainLayout();
                        listviewOnClick();
                        searchFunctions();
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });


    }




    private void switchtoMainLayout() {
        setContentView(R.layout.activity_main);

        // Get a reference to the ListView in the main layout
        listView = findViewById(R.id.listView);

        listView = findViewById(R.id.listView);
        searchView = findViewById(R.id.searchView);


        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,filteredData);
        listView.setAdapter(adapter);

    }




    private void listviewOnClick(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                DataModel selecteddata = filteredData.get(i);
                int indexInFilteredData = i;

                int indexInOriginalData = originalData.indexOf(selecteddata);


                if (indexInOriginalData == -1)
                {
                    indexInOriginalData = i;
                }

                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.putParcelableArrayListExtra("filteredData", filteredData);
                intent.putParcelableArrayListExtra("originalData", originalData);
                intent.putExtra("currentIndexFiltered", indexInFilteredData);
                intent.putExtra("currentIndexOriginal", indexInOriginalData);
                intent.putExtra("id", selecteddata.getId());
                intent.putExtra("kanji", selecteddata.getkanji());
                intent.putExtra("hiragana", selecteddata.gethiragana());
                intent.putExtra("meaning", selecteddata.getMeaning());
                startActivity(intent);


            }
        });
    }


    private void searchFunctions(){
        searchView.setQueryHint("Search Here..");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                filteredData.clear();
                for (DataModel data : originalData) {
                    if (data.getkanji().toLowerCase().contains(newText.toLowerCase()) || data.gethiragana().toLowerCase().contains(newText.toLowerCase())) {
                        filteredData.add(data);
                    }
                }
                adapter.notifyDataSetChanged();

                return true;
            }
        });
    }



}


