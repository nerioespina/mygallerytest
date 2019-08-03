package com.mygallery.mygallery;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.*;
import com.loopj.android.http.*;

import cz.msebera.android.httpclient.Header;

public class Main extends AppCompatActivity {

    int REQUEST_CODE_1 = 1;

    RecyclerView albumsContainer;
    ArrayList<AlbumModel> tempData;
    ArrayList<AlbumModel> initialData;
    boolean firstTime = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getAlbums();

        SearchView searchView = (SearchView) findViewById(R.id.searchBy);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                callSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                callSearch(newText);
                return true;
            }

        });

        if(savedInstanceState != null){
            String mySearchText = savedInstanceState.getString("mySearchText");
            searchView.setQuery(mySearchText,true);
            callSearch(mySearchText);
        }

    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        SearchView tView = (SearchView) findViewById(R.id.searchBy);
        String mySearchText = tView.getQuery().toString();
        savedInstanceState.putString("mySearchText",mySearchText);
        

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        SearchView tView = (SearchView) findViewById(R.id.searchBy);

        String mySearchText = savedInstanceState.getString("mySearchText");
        tView.setQuery(mySearchText,true);
        callSearch(mySearchText);
        
    }
    


    public void callSearch(String query) {
        if(initialData != null){
            tempData = (ArrayList<AlbumModel>) initialData.clone();
            for(int i = tempData.size() - 1;i>=0; i--){

                if(filterElement(tempData.get(i).getAlbumName(), query)!= true){
                    tempData.remove(i);
                }
            }
            fillAlbums(tempData);
        }

    }

    protected boolean filterElement(String value, String query){

        return value.contains(query);
    }

    protected void fillAlbums(ArrayList<AlbumModel> data){

        if(firstTime){
            initialData = (ArrayList<AlbumModel>) data.clone();
            tempData = (ArrayList<AlbumModel>) data.clone();
            firstTime = false;
        }


        albumsContainer = (RecyclerView)findViewById(R.id.albumContainer);
        LinearLayoutManager linearManager = new LinearLayoutManager(this);
        albumsContainer.setLayoutManager(linearManager);

        AlbumsAdapter adapter = new AlbumsAdapter(data);
        albumsContainer.setAdapter(adapter);
        adapter.setOnItemClickListener(new AlbumsAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if(position != -1){

                    showAlbum(position);
                }

            }
        });

    }

    public void getAlbums() {
        HttpClient.get("albums", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, org.json.JSONArray response) {
                // Pull out the first event on the public timeline
                try {
                    JSONObject result = null;
                    String albumName;
                    int albumId;
                    // Do something with the response
                    ArrayList data = new ArrayList<AlbumModel>();
                    for (int i = 0; i < response.length(); i++) {
                        result = response.getJSONObject(i);
                        albumName = result.getString("title");
                        albumId = result.getInt("id");
                        data.add(new AlbumModel(
                                albumId,
                                albumName
                        ));
                    }
                    fillAlbums(data);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
    protected void showAlbum(int id){

        Intent intent = new Intent(Main.this, Photos.class);

        intent.putExtra("albumId", tempData.get(id).getAlbumId());

        intent.putExtra("albumName", tempData.get(id).getAlbumName());

        startActivityForResult(intent, REQUEST_CODE_1);
    }

}
