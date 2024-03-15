package com.example.japanesereviewer123.ui.contentvocab;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class VocabFragment extends Fragment {

    private ArrayList<GoiDataModel> originalData;
    private ArrayList<GoiDataModel> filteredData;
    private ListView listView;
    private ArrayAdapter<GoiDataModel> adapter;
    private Vocab_Adapter vocab_adapter;
    private SearchView searchView;
    String level;
    ImageView imgbanner;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View loadingView = inflater.inflate(R.layout.loading_screen, container, false);



        originalData = new ArrayList<>();
        filteredData = new ArrayList<>();



        // Retrieve the arguments bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            // Retrieve the string from the bundle
            level = bundle.getString("level");
        }

        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("JLPT " + level + " Vocabulary");
        }


        //ExecutorService Functions (Alternative of Asynctask)
        ExecutorService service  = Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
            String data =  "";
            @Override
            public void run() {

                //do in background
                try {
                    URL url = new URL("https://jer101.shop/110796/indexjapan110796v.php?n=" + level);
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
                getActivity().runOnUiThread(new Runnable() {
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

        return loadingView;
    }

    @SuppressLint("MissingInflatedId")
    private void switchtoMainLayout() {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_vocab, (ViewGroup) getView(), false);

        // Get a reference to the ListView in the main layout


        listView = rootView.findViewById(R.id.listView);
        searchView = rootView.findViewById(R.id.searchView);

        vocab_adapter = new Vocab_Adapter(getActivity(), R.layout.vocab_listrow,filteredData);
        listView.setAdapter(vocab_adapter);

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



                NavController navController = Navigation.findNavController(requireView());

                // Put the data into a bundle
                Bundle bundle = new Bundle();

                bundle.putParcelableArrayList("filteredData", filteredData);
                bundle.putParcelableArrayList("originalData",originalData);
                bundle.putInt("currentIndexFiltered",indexInFilteredData);
                bundle.putInt("currentIndexOriginal",indexInOriginalData);
                bundle.putString("id",selecteddata.getId());
                bundle.putString("vocab",selecteddata.getVocab());
                bundle.putString("hiragana",selecteddata.getHiragana());
                bundle.putString("imageurl",selecteddata.getImageurl());
                bundle.putString("level",level);
                // Navigate to the kanjiFragment and pass the arguments
                navController.navigate(R.id.action_vocabfragment_to_page2, bundle);
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