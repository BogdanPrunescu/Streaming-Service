package fileio;

import java.util.ArrayList;

public final class Filters {

    private Sort sort;

    public static final class Sort {
        private String rating;
        private String duration;

        public String getRating() {
            return rating;
        }

        public void setRating(final String rating) {
            this.rating = rating;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(final String duration) {
            this.duration = duration;
        }
    }

    private Contains contains;

    public static final class Contains {
        private ArrayList<String> actors;

        private ArrayList<String> genre;

        public ArrayList<String> getGenre() {
            return genre;
        }

        public void setGenre(final ArrayList<String> genre) {
            this.genre = genre;
        }

        public ArrayList<String> getActors() {
            return actors;
        }

        public void setActors(final ArrayList<String> actors) {
            this.actors = actors;
        }
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(final Sort sort) {
        this.sort = sort;
    }

    public Contains getContains() {
        return contains;
    }

    public void setContains(final Contains contains) {
        this.contains = contains;
    }
}
