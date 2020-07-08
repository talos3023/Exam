package com.example.exam.services;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetMovieDataAsyncTask extends AsyncTask<String, Void, Movie[]> {
    private Callback callback;

    @Override
    protected Movie[] doInBackground(String... strings) {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(strings[0])
                    .build();
            Response response = client.newCall(request).execute();
            String jsonData = response.body().string();
            String jsonArray = new Gson().fromJson(jsonData, JsonObject.class).getAsJsonObject().getAsJsonArray("data").toString();
            return new Gson().fromJson(jsonArray, Movie[].class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Movie[] movies) {
        Log.d("onPostExecute", Thread.currentThread().getName());
        if (callback != null && movies != null) {
            callback.onDataReceived(movies);
        }
    }

    public interface Callback {

        void onDataReceived(Movie[] movies);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }
}
