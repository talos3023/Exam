package com.example.exam;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.exam.data.UserStorage;
import com.example.exam.services.GetMovieDataAsyncTask;
import com.example.exam.services.Movie;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    private ListView mMovies;
    Toolbar mToolbar;
    private MovieArrayAdapter movieArrayAdapter;
    FragmentAdvertisement fragmentAdvertisement;
    private EditText mSearchText;
    public int pageNumber;
    public boolean loadingMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = findViewById(R.id.toolbar);
        initActionBar(mToolbar);
        mMovies = findViewById(R.id.movies);
        mSearchText = findViewById(R.id.search_text);
        pageNumber = 0;
        loadingMore = false;
        fragmentAdvertisement = (FragmentAdvertisement) getSupportFragmentManager().findFragmentById(R.id.advertisement);
        fragmentAdvertisement.setCallback(new FragmentAdvertisement.Callback() {
            @Override
            public void onAdvertisementClick() {Intent intent = new Intent();
                intent.setAction(FragmentAdvertisement.NOTIFICATION);
                intent.putExtra(FragmentAdvertisement.NOTIFICATION_DATA, "You naughty boy!!!");
                sendBroadcast(intent);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.hide(fragmentAdvertisement);
                ft.commit();
            }
        });
        movieArrayAdapter = new MovieArrayAdapter(this, 0, new ArrayList<Movie>());
        getMovies();
        UserStorage storage = new UserStorage();
        if (storage.getLoggedUser(this) == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    public void searchMovie(View view) {
        pageNumber = 0;
        findViewById(R.id.progress).setVisibility(View.VISIBLE);
        mMovies.setOnScrollListener(null);
        GetMovieDataAsyncTask getMovieDataAsyncTask = new GetMovieDataAsyncTask();
        GetMovieDataAsyncTask.Callback callback = new GetMovieDataAsyncTask.Callback() {
            @Override
            public void onDataReceived(Movie[] movies) {
                mMovies.setAdapter(movieArrayAdapter);
                movieArrayAdapter.clear();
                movieArrayAdapter.addAll(movies);
                findViewById(R.id.progress).setVisibility(View.GONE);
            }
        };
        getMovieDataAsyncTask.setCallback(callback);
        getMovieDataAsyncTask.execute("https://api.imovies.cc/api/v1/search-advanced?filters[type]=movie&keywords=" + mSearchText.getText().toString() + "&page=1&per_page=20");
    }

    private void getMovies() {
        loadingMore = true;
        findViewById(R.id.progress).setVisibility(View.VISIBLE);
        mMovies.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) { }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int lastInScreen = firstVisibleItem + visibleItemCount;
                if(lastInScreen == totalItemCount && !loadingMore) {
                    getMovies();
                }
            }
        });

        GetMovieDataAsyncTask getMovieDataAsyncTask = new GetMovieDataAsyncTask();
        GetMovieDataAsyncTask.Callback callback = new GetMovieDataAsyncTask.Callback() {
            @Override
            public void onDataReceived(Movie[] movies) {
                mMovies.setAdapter(movieArrayAdapter);
                if (pageNumber != 0) {
                    mMovies.setSelection(mMovies.getCount() - 2);
                }
                movieArrayAdapter.addAll(movies);
                findViewById(R.id.progress).setVisibility(View.GONE);
                loadingMore = false;
            }
        };
        getMovieDataAsyncTask.setCallback(callback);
        getMovieDataAsyncTask.execute("https://api.imovies.cc/api/v1/movies?page=" + (++pageNumber));
    }

    class MovieArrayAdapter extends ArrayAdapter<Movie> {

        private Context context;

        public MovieArrayAdapter(Context context, int resource, List<Movie> objects) {
            super(context, resource, objects);
            this.context = context;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.view_movie_preview_item, parent, false);
            LinearLayout moviesLayout = view.findViewById(R.id.movies);
            final Movie movie = getItem(position);
            moviesLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), MovieActivity.class);
                    String jsonMovie = new Gson().toJson(movie);
                    intent.putExtra("movie", jsonMovie);
                    startActivity(intent);
                }
            });
            ImageView imageView = view.findViewById(R.id.movieImage);
            Picasso.get().load(movie.getPosters().getData().getPosterUrl()).into(imageView);
            TextView secondaryNameView = moviesLayout.findViewById(R.id.secondaryName);
            secondaryNameView.setText(movie.getSecondaryName());
            TextView releaseDateView = moviesLayout.findViewById(R.id.releaseDate);
            releaseDateView.setText(movie.getReleaseDate());
            return view;
        }
    }
}