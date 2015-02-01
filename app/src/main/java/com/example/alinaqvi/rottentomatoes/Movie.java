package com.example.alinaqvi.rottentomatoes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by alinaqvi on 1/20/15.
 */
public class Movie implements Parcelable {
    public String title;
    public String cast;
    public int year;
    public String posterUrl;
    public int criticsScore;
    public int audienceScore;
    public String synopsis;
    public String rating;
    public int runningTime;
    public String releaseDate;

    public Movie() {

    }

    public Movie(Parcel in) {
        Object[] data = in.readArray(this.getClass().getClassLoader());
        title = (String) data[0];
        cast = (String) data[1];
        year = (Integer) data[2];
        posterUrl = (String) data[3];
        criticsScore = (Integer) data[4];
        audienceScore = (Integer) data[5];
        synopsis = (String) data[6];
        rating = (String) data[7];
        runningTime = (Integer) data[8];
        releaseDate = (String) data[9];
    }

    @Override
    public String toString() {
        return title + " - " + audienceScore;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeArray(new Object[] {title, cast, year, posterUrl, criticsScore, audienceScore, synopsis, rating, runningTime, releaseDate});
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
