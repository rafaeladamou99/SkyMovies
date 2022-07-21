package com.example.skymovies;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {
    private ArrayList<Movie> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    MoviesAdapter(Context context, ArrayList<Movie> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.movie_items, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String title = mData.get(position).getTitle();
        holder.title.setText(title);
        String rating = mData.get(position).getRating();
        holder.rating.setText(rating);
        String url = mData.get(position).getImage();
        Picasso.get().load(url).into(holder.image);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void updateMovies(ArrayList<Movie> movies){
        mData.clear();
        mData.addAll(movies);
        notifyDataSetChanged();
        Log.d("MSGGGG",String.valueOf(mData.size()));
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        TextView rating;
        ImageView image;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.movietitle);
            rating = itemView.findViewById(R.id.movierating);
            image = itemView.findViewById(R.id.movieimage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    Movie getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
