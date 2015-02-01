package com.example.alinaqvi.rottentomatoes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by alinaqvi on 1/20/15.
 */
public class MoviesAdapter extends ArrayAdapter<Movie> {

    public MoviesAdapter(Context context) {
        super(context, R.layout.item_movie);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = getItem(position);
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_movie, parent, false);
        }
        ImageView ivPoster = (ImageView) convertView.findViewById(R.id.ivPoster);
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        TextView tvScore = (TextView) convertView.findViewById(R.id.tvScore);
        TextView tvCast = (TextView) convertView.findViewById(R.id.tvCast);

        tvTitle.setText(movie.title);
        Picasso.with(getContext()).load(movie.posterUrl).into(ivPoster);
        tvScore.setText("Score: " + movie.audienceScore + "%");
        tvCast.setText(movie.cast);
        return convertView;
    }
}
