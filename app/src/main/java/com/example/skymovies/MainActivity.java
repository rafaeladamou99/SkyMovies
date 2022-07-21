package com.example.skymovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.ItemClickListener{

    MoviesAdapter adapter;
    ArrayList<Movie> mArrayList = new ArrayList<>();
    Handler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mHandler = new Handler(Looper.getMainLooper());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.moviesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MoviesAdapter(this, mArrayList);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://saman-mb.github.io/SkyMobileDeveloperAcademy/assessmentDayTask/movies.json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                } else {
                    // do something wih the result
                    String movies = response.body().string();
                    Log.d("AAAAAAAAA",movies);
                    try {

                        JSONObject moviesDetails = new JSONObject(movies);

                        Log.d("My App", moviesDetails.toString());

                        JSONArray moviesArray = moviesDetails.getJSONArray("movies");

                        Log.e("EEEEEEEEE", moviesArray.toString());


                        ArrayList <Movie> arrayList2 = new ArrayList<>();
                        for (int i = 0 ; i < moviesArray.length(); i++) {
                            JSONObject obj = moviesArray.getJSONObject(i);
                            String title = obj.getString("title");
                            String uuid = obj.getString("uuid");
                            String rating = obj.getString("rating");
                            String image = obj.getString("image");

                            arrayList2.add(new Movie(title,uuid,rating,image));
                            Log.e("MOVIEEEEEE", arrayList2.get(i).getTitle());
                        }
                        Log.d("SIZEEEE", String.valueOf(arrayList2.size()));





                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                adapter.updateMovies(arrayList2);
                                Log.d("SIZEEEE", String.valueOf(arrayList2.size()));
                            }
                        });


                    } catch (Throwable t) {
                        Log.e("My App", t.toString() + movies + "\"");

                    }

                }
            }



        });





    }

    @Override
    public void onItemClick(View view, int position) {
        Intent change = new Intent(MainActivity.this, MovieDetails.class);
        change.putExtra("uuid", adapter.getItem(position).getUuid());
        startActivity(change);
    }
}