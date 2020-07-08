package com.example.exam;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.example.exam.services.Movie;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class MovieActivity extends ActionBarActivity {
    private ImageView mMovieImage;
    private TextView mPrimaryName;
    private TextView mSecondaryName;
    private TextView mReleaseDate;
    private TextView mDuration;
    private TextView mPlot;
    private FragmentAdvertisement fragmentAdvertisement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        Toolbar toolbar = findViewById(R.id.toolbar);
        initActionBar(toolbar);
        Movie movie = new Gson().fromJson(getIntent().getStringExtra("movie"), Movie.class);
        mMovieImage = findViewById(R.id.movieImage);
        Picasso.get().load(movie.getPosters().getData().getPosterUrl()).into(mMovieImage);
        mPrimaryName = findViewById(R.id.primaryName);
        mPrimaryName.setText(movie.getPrimaryName());
        mSecondaryName = findViewById(R.id.secondaryName);
        mSecondaryName.setText(movie.getSecondaryName());
        mDuration = findViewById(R.id.duration);
        mDuration.setText(Integer.toString(movie.getDuration()));
        mReleaseDate = findViewById(R.id.releaseDate);
        mReleaseDate.setText(movie.getReleaseDate());
        mPlot = findViewById(R.id.plot);
        mPlot.setText(movie.getPlot().getData().getDescription());
        fragmentAdvertisement = (FragmentAdvertisement) getSupportFragmentManager().findFragmentById(R.id.advertisement);
        fragmentAdvertisement.setCallback(new FragmentAdvertisement.Callback() {
            @Override
            public void onAdvertisementClick() {
                Intent intent = new Intent();
                intent.setAction(FragmentAdvertisement.NOTIFICATION);
                intent.putExtra(FragmentAdvertisement.NOTIFICATION_DATA, "You naughty boy!!!");
                sendBroadcast(intent);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.hide(fragmentAdvertisement);
                ft.commit();
            }
        });
    }
}