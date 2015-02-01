package com.example.alinaqvi.rottentomatoes;

import android.app.ActionBar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class DetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME);
        getSupportActionBar().setCustomView(R.layout.actionbar_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView myTitle = (TextView) findViewById(R.id.myTitle);
        myTitle.setText("Movie");
        ImageView ivPosterLarge = (ImageView) findViewById(R.id.posterLarge);
        TextView tvTitleDetail = (TextView) findViewById(R.id.tvTitleDetail);
        TextView tvYear = (TextView) findViewById(R.id.tvYear);
        TextView tvCritics = (TextView) findViewById(R.id.tvCritics);
        TextView tvAudience = (TextView) findViewById(R.id.tvAudience);
        TextView tvSynopsis = (TextView) findViewById(R.id.tvSynopsis);
        TextView tvRating = (TextView) findViewById(R.id.tvRating);
        TextView tvRuntime = (TextView) findViewById(R.id.tvRuntime);
        TextView tvReleaseDate = (TextView) findViewById(R.id.tvReleaseDate);
        Movie movie = getIntent().getExtras().getParcelable("ITEM_DATA");

        Picasso.with(this).load(movie.posterUrl).into(ivPosterLarge);
        tvTitleDetail.setText(movie.title);
        tvYear.setText("(" + movie.year + ")");
        tvCritics.setText(movie.criticsScore + "% of critics");
        tvAudience.setText(movie.audienceScore + "% of audience");
        tvSynopsis.setText(movie.synopsis);
        tvRating.setText(Html.fromHtml("<b>RATING</b> " + movie.rating));
        tvRuntime.setText(Html.fromHtml("<b>RUNNING TIME</b> " + movie.runningTime + " min"));
        tvReleaseDate.setText(Html.fromHtml("<b>THEATER RELEASE</b> " + movie.releaseDate));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
