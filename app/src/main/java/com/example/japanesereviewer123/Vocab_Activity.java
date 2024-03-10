package com.example.japanesereviewer123;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Vocab_Activity extends AppCompatActivity {

    private ArrayList<GoiDataModel> originalData;
    private ArrayList<GoiDataModel> filteredData;
    private ListView listView;
    private ArrayAdapter<GoiDataModel> adapter;
    private Vocab_Adapter vocab_adapter;
    private SearchView searchView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vocabulary_layout);


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
                    URL url = new URL("https://animerepository.com/110796/indexjapan110796v.php");
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
                        String vocab = jsonObject.getString("vocab");
                        String hiragana = jsonObject.getString("hiragana");
                        String imageurl = jsonObject.getString("imageurl");
                        originalData.add(new GoiDataModel(id,vocab,hiragana,imageurl));
                        filteredData.add(new GoiDataModel(id,vocab,hiragana,imageurl));

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
                        vocab_adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    private void switchtoMainLayout() {
        setContentView(R.layout.vocabulary_layout);

        // Get a reference to the ListView in the main layout
        listView = findViewById(R.id.listView);

        listView = findViewById(R.id.listView);
        searchView = findViewById(R.id.searchView);

        vocab_adapter = new Vocab_Adapter(this, R.layout.vocab_listrow,filteredData);
        listView.setAdapter(vocab_adapter);

    }




    private void listviewOnClick(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                GoiDataModel selecteddata = filteredData.get(i);
                int indexInFilteredData = i;

                int indexInOriginalData = originalData.indexOf(selecteddata);


                if (indexInOriginalData == -1)
                {
                    indexInOriginalData = i;
                }

                Intent intent = new Intent(Vocab_Activity.this, Vocabdetails_Activity.class);
                intent.putParcelableArrayListExtra("filteredData", filteredData);
                intent.putParcelableArrayListExtra("originalData", originalData);
                intent.putExtra("currentIndexFiltered", indexInFilteredData);
                intent.putExtra("currentIndexOriginal", indexInOriginalData);
                intent.putExtra("id", selecteddata.getId());
                intent.putExtra("vocab", selecteddata.getVocab());
                intent.putExtra("hiragana", selecteddata.getHiragana());
                intent.putExtra("imageurl", selecteddata.getImageurl());
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
                for (GoiDataModel data : originalData) {
                    if (data.getVocab().toLowerCase().contains(newText.toLowerCase()) || data.getHiragana().toLowerCase().contains(newText.toLowerCase())) {
                        filteredData.add(data);
                    }
                }
                vocab_adapter.notifyDataSetChanged();

                return true;
            }
        });
    }


}
