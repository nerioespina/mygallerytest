package com.mygallery.mygallery;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class Photos extends AppCompatActivity {
    RecyclerView photosContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        int albumId = intent.getIntExtra("albumId",1);
        String albumName = intent.getStringExtra("albumName");

        TextView textV = findViewById(R.id.albumTitle);
        textV.setText(albumName.toUpperCase());

        getPhotos(albumId);


    }

    protected void fillPhotos(ArrayList<PhotoModel> data){

        photosContainer = (RecyclerView)findViewById(R.id.photoContainer);
        LinearLayoutManager linearManager = new LinearLayoutManager(this);
        photosContainer.setLayoutManager(linearManager);
        ArrayList<PhotoModel> resPhotos;

        PhotosAdapter adapter = new PhotosAdapter(data);
        photosContainer.setAdapter(adapter);
        /*adapter.setOnItemClickListener(new PhotosAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                showPhoto(position);
            }
        });*/
    }

    public void getPhotos(int albumId) {

        HttpClient.get("photos?albumId=" +String.valueOf(albumId), null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, org.json.JSONArray response) {
                // Pull out the first event on the public timeline
                try {
                    JSONObject result = null;
                    String photoName, photoUrl;
                    int photoId;
                    // Do something with the response
                    ArrayList data = new ArrayList<PhotoModel>();
                    for (int i = 0; i < response.length(); i++) {
                        result = response.getJSONObject(i);
                        photoName = result.getString("title");
                        photoId = result.getInt("id");
                        photoUrl = result.getString("url");
                        data.add(new PhotoModel(
                                photoId,
                                photoName,
                                photoUrl
                        ));
                    }
                    fillPhotos(data);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
