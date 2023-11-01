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
        setContentView(R.layout.activity_main);




        listView = findViewById(R.id.listView);
        searchView = findViewById(R.id.searchView);
        originalData = new ArrayList<>();
        filteredData = new ArrayList<>();


        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,filteredData);
        listView.setAdapter(adapter);

        searchView.setQueryHint("Search Here..");


            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {

                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {

                    filterData(newText);

                    return true;
                }
            });

        filterData("Doe"); // Passing an empty string to show all data initially



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               // DataModel selecteddata = adapter.getItem(i);
                DataModel selecteddata = filteredData.get(i);
                int indexInFilteredData = i;

                int indexInOriginalData = originalData.indexOf(selecteddata);


                if (indexInOriginalData == -1)
                {
                    indexInOriginalData = i;
                }

             //   Log.d("indexInOriginalData", String.valueOf(indexInOriginalData));
             //   Log.d("indexInFilteredData", String.valueOf(indexInFilteredData));

                Toast.makeText(getApplicationContext(), String.valueOf(filteredData.get(i)),Toast.LENGTH_SHORT).show();

                  Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                  intent.putParcelableArrayListExtra("filteredData", filteredData);
               intent.putParcelableArrayListExtra("originalData", originalData);
                intent.putExtra("currentIndexFiltered", indexInFilteredData);
                intent.putExtra("currentIndexOriginal", indexInOriginalData);
                intent.putExtra("id", selecteddata.getId());
                    intent.putExtra("firstname", selecteddata.getFirstname());
                 intent.putExtra("lastname", selecteddata.getLastname());
                   startActivity(intent);


            }
        });




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
                   URL url = new URL("http://192.168.100.64:80/japan/indexjapan2.php");
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
                       String firstname = jsonObject.getString("firstname");
                       String lastname = jsonObject.getString("lastname");
                       originalData.add(new DataModel(id,firstname,lastname));
                       filteredData.add(new DataModel(id, firstname, lastname));

                   }

               } catch (Exception e) {
                   e.printStackTrace();
               }

               //OnPostExecute
               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       adapter.notifyDataSetChanged();
                   }
               });
           }
       });



    }


    private void filterData(String query) {
        filteredData.clear();
        for (DataModel data : originalData) {
            if (data.getFirstname().toLowerCase().contains(query.toLowerCase())) {
                filteredData.add(data);
            }
        }
        adapter.notifyDataSetChanged();
    }




}


