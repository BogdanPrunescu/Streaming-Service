package manager;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import fileio.MovieInput;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class Movie {

    private String name;
    private int year;
    private int duration;
    private ArrayList<String> genres;
    private ArrayList<String> actors;
    private ArrayList<String> countriesBanned;
    @JsonIgnore
    private Map<String, Integer> userRatings = new HashMap<>();
    private int numLikes;
    private double rating;
    private int numRatings;

    public Movie(final Movie movie) {
        this.name = movie.name;
        this.year = movie.year;
        this.duration = movie.duration;
        this.genres = movie.genres;
        this.actors = movie.actors;
        this.countriesBanned = movie.countriesBanned;
        this.userRatings = movie.userRatings;
        this.numLikes = movie.numLikes;
        this.rating = movie.rating;
        this.numRatings = movie.numRatings;
    }
    public Movie(final MovieInput movieInput) {
        this.setName(movieInput.getName());
        this.setYear(movieInput.getYear());
        this.setDuration(movieInput.getDuration());
        this.setActors(movieInput.getActors());
        this.setGenres(movieInput.getGenres());
        this.setCountriesBanned(movieInput.getCountriesBanned());
    }

    /**
     * Function used to print rating with 2 decimals
     * @return
     */
    @JsonProperty("rating")
    public BigDecimal jsonPrintRating() {
        BigDecimal bd = new BigDecimal(rating);
        bd = bd.setScale(2, RoundingMode.DOWN);
        return bd;
    }

    /**
     * Function used to print year
     * @return
     */
    @JsonProperty("year")
    public String jsonPrintYear() {
        return ((Integer) year).toString();
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(final int year) {
        this.year = year;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    public int getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(final int numLikes) {
        this.numLikes = numLikes;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(final double rating) {
        this.rating = rating;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(final ArrayList<String> genres) {
        this.genres = genres;
    }

    public ArrayList<String> getActors() {
        return actors;
    }

    public void setActors(final ArrayList<String> actors) {
        this.actors = actors;
    }

    public ArrayList<String> getCountriesBanned() {
        return countriesBanned;
    }

    public void setCountriesBanned(final ArrayList<String> countriesBanned) {
        this.countriesBanned = countriesBanned;
    }

    public Map<String, Integer> getUserRatings() {
        return userRatings;
    }

    public void setUserRatings(final Map<String, Integer> userRatings) {
        this.userRatings = userRatings;
    }

    public int getNumRatings() {
        return numRatings;
    }

    public void setNumRatings(final int numRatings) {
        this.numRatings = numRatings;
    }
}
