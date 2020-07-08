package com.example.exam.services;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Movie {
    String primaryName;
    String secondaryName;
    String releaseDate;
    int duration;
    Poster posters;
    Plot plot;
    //List<String> genres;

    public String getPrimaryName() {
        return primaryName;
    }

    public void setPrimaryName(String primaryName) {
        this.primaryName = primaryName;
    }

    public String getSecondaryName() {
        return secondaryName;
    }

    public void setSecondaryName(String secondaryName) {
        this.secondaryName = secondaryName;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Poster getPosters() {
        return posters;
    }

    public void Poster(Poster posters) {
        this.posters = posters;
    }

    public Plot getPlot() {
        return plot;
    }

    public void setPlot(Plot plot) {
        this.plot = plot;
    }

//    public List<String> getGenres() {
//        return genres;
//    }
//
//    public void setGenres(List<String> genres) {
//        this.genres = genres;
//    }

    public static class Plot {
        Data data;

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }

        public static class Data {
            String description;
            String language;

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getLanguage() {
                return language;
            }
        }
    }

    public static class Poster {
        Data data;

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }

        public static class Data {
            @SerializedName("240")
            String posterUrl;

            public String getPosterUrl() {
                return posterUrl;
            }

            public void setPosterUrl(String posterUrl) {
                this.posterUrl = posterUrl;
            }
        }
    }
}
