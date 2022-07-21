package com.example.skymovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class MovieDetails extends AppCompatActivity {
    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        mHandler = new Handler(Looper.getMainLooper());
        Intent intent = getIntent();
        String uuid = intent.getStringExtra("uuid");
        TextView title1 = findViewById(R.id.title);
        TextView rating1 = findViewById(R.id.rating);
        TextView synopsis1 = findViewById(R.id.synopsis);
        ImageView image1 = findViewById(R.id.imageView);


        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://saman-mb.github.io/SkyMobileDeveloperAcademy/assessmentDayTask/movies/"+ uuid+".json")
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

                        String title = moviesDetails.getString("title");
                        String synopsis = moviesDetails.getString("synopsis");
                        String rating = moviesDetails.getString("rating");
                        String image = moviesDetails.getString("image");
                        String trailer = moviesDetails.getString("trailer");










                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    title1.setText(title);
                                    rating1.setText(rating);
                                    synopsis1.setText(synopsis);
                                    Picasso.get().load(image).into(image1);
                                }
                            });


                    } catch (Throwable t) {
                        Log.e("My App", t.toString() + movies + "\"");

                    }

                }
            }



        });
    }
}