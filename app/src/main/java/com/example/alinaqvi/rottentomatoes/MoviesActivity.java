package com.example.alinaqvi.rottentomatoes;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class MoviesActivity extends ActionBarActivity {
    MoviesAdapter moviesAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME);
        getSupportActionBar().setCustomView(R.layout.actionbar_title);
        moviesAdapter = new MoviesAdapter(this);
        ListView lvMovies = (ListView) findViewById(R.id.lvMovies);
        lvMovies.setAdapter(moviesAdapter);
        lvMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MoviesActivity.this, DetailActivity.class);
                i.putExtra("ITEM_DATA", moviesAdapter.getItem(position));
                startActivity(i);
            }
        });
        fetchMoviesAsync();
    }

    private void fetchMoviesAsync () {
        AsyncHttpClient httpClient = new AsyncHttpClient();
        httpClient.get("http://api.rottentomatoes.com/api/public/v1.0/lists/movies/box_office.json?apikey=akkt5uybpcsen9zd7edwc5sf", new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e(this.getClass().getName(), statusCode + " -- " + responseString, throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                List<Movie> movies = new ArrayList<Movie>();

                try {
                    moviesAdapter.clear();
                    JSONArray jsonArray = response.getJSONArray("movies");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject data = (JSONObject) jsonArray.get(i);
                        Movie movie = new Movie();
                        movie.title = data.getString("title");
                        movie.year = data.getInt("year");
                        movie.rating = data.getString("mpaa_rating");
                        movie.runningTime = data.getInt("runtime");
                        DateFormat fromFormat = new SimpleDateFormat("yyyy-MM-dd");
                        DateFormat toFormat = new SimpleDateFormat("MM/dd/yyyy");
                        movie.releaseDate = toFormat.format(fromFormat.parse(data.getJSONObject("release_dates").getString("theater")));
                        JSONObject ratings = data.getJSONObject("ratings");
                        movie.criticsScore = ratings.getInt("critics_score");
                        movie.audienceScore = ratings.getInt("audience_score");
                        movie.posterUrl = data.getJSONObject("posters").getString("original");
                        movie.synopsis = data.getString("synopsis");
                        JSONArray abridgedCast = data.getJSONArray("abridged_cast");
                        movie.cast = "";
                        for (int j = 0; j < abridgedCast.length(); j++) {
                            JSONObject cast = (JSONObject) abridgedCast.get(j);
                            movie.cast += cast.getString("name") + ", ";
                        }
                        movie.cast = movie.cast.substring(0, movie.cast.length() - 2);
                        moviesAdapter.add(movie);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                moviesAdapter.notifyDataSetChanged();
                super.onSuccess(statusCode, headers, response);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movies, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.miRefresh) {
            fetchMoviesAsync();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
