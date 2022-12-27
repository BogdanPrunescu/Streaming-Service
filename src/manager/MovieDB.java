package manager;

import fileio.Filters;
import fileio.MovieInput;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public final class MovieDB {
    private ArrayList<Movie> movies;

    /**
     * Add element into movie database
     * @param movieInput movie that needs to be added
     */
    void addElement(final MovieInput movieInput) {
        if (movies == null) {
            movies = new ArrayList<>();
        }
        Movie movie = new Movie(movieInput);
        movies.add(movie);
    }

    /**
     * Remove element into movie database
     * @param movieInput movie that needs to be added
     */
    void removeElement(final MovieInput movieInput) {
        if (movies != null) {
            movies.removeIf((Movie m) -> m.getName().equals(movieInput.getName()));
        }
    }

    /**
     * apply filters to the movie databate
     * @param filter filters
     * @return a new array containing movies filtered
     */
    public ArrayList<Movie> getMoviesbyFilters(final Filters filter) {

        ArrayList<Movie> filteredMovies = new ArrayList<>();
        for (Movie movie : AppManager.getInstance().getMovieDB().getMoviesOfUser(AppManager.getInstance().getCurrentUser())) {
            filteredMovies.add(movie);
        }
        if (filter.getSort() != null) {


            /* First sort by duration*/
            if (filter.getSort().getDuration() != null) {

                Collections.sort(filteredMovies, new Comparator<Movie>() {
                    @Override
                    public int compare(final Movie o1, final Movie o2) {

                        boolean ratingComp = false;

                        /* Get rating comparator in case durations are equal */
                        if (filter.getSort().getRating() != null) {

                            if (o2.getRating() == o1.getRating()
                                    && o2.getDuration() == o1.getDuration()) {
                                return 0;
                            }

                            if (filter.getSort().getRating().equals("decreasing")) {
                                ratingComp = o1.getRating() < o2.getRating();
                            } else {
                                ratingComp = o1.getRating() > o2.getRating();
                            }
                        }

                        boolean durationComp = false;


                        if (o1.getDuration() == o2.getDuration()
                                && filter.getSort().getRating() != null) {
                            durationComp = ratingComp;
                        } else {

                            if (o1.getDuration() == o2.getDuration()) {
                                return 0;
                            }

                            if (filter.getSort().getDuration().equals("decreasing")) {
                                durationComp = o1.getDuration() < o2.getDuration();
                            } else {
                                durationComp = o2.getDuration() >= o2.getDuration();
                            }
                        }

                        if (durationComp) {
                            return 1;
                        } else {
                            return -1;
                        }
                    }
                });

            /* Sort by rating */
            } else if (filter.getSort().getRating() != null) {
                Collections.sort(filteredMovies, new Comparator<Movie>() {
                    @Override
                    public int compare(final Movie o1, final Movie o2) {

                        boolean ratingComp = false;

                        if (o2.getRating() == o1.getRating()) {
                            return 0;
                        }

                        if (filter.getSort().getRating().equals("decreasing")) {
                            ratingComp = o1.getRating() < o2.getRating();
                        } else {
                            ratingComp = o1.getRating() > o2.getRating();
                        }

                        if (ratingComp) {
                            return 1;
                        } else {
                            return -1;
                        }
                    }
                });
            }

        }

        if (filter.getContains() != null) {

            /* filter by actors */
            if (filter.getContains().getActors() != null) {
                for (String actor : filter.getContains().getActors()) {
                    for (Movie movie : movies) {
                        if (!movie.getActors().contains(actor)) {
                            filteredMovies.remove(movie);
                        }
                    }
                }
            }

            /* filter by genres */
            if (filter.getContains().getGenre() != null) {
                for (String genre : filter.getContains().getGenre()) {
                    for (Movie movie : movies) {
                        if (!movie.getGenres().contains(genre)) {
                            filteredMovies.remove(movie);
                        }
                    }
                }
            }
        }

        return filteredMovies;
    }

    /**
     * get movies that are available to the user
     * @param user
     * @return a new array that contains user's movies
     */
    public ArrayList<Movie> getMoviesOfUser(final User user) {
        ArrayList<Movie> filteredMovies = new ArrayList<>();
        String country = user.getCredentials().getCountry();
        for (Movie movie : movies) {
            if (!movie.getCountriesBanned().contains(country)) {
                filteredMovies.add(movie);
            }
        }

        return filteredMovies;
    }

    /**
     * Get movies by start of title
     * @param search string that must match with movie's title beginning
     * @return a new array that contains matching movies
     */
    public ArrayList<Movie> getMoviesbyStart(final String search) {
        ArrayList<Movie> filteredMovies = new ArrayList<>();
        for (Movie movie : AppManager.getInstance().getCurrentMoviesList()) {
            if (movie.getName().startsWith(search)) {
                filteredMovies.add(movie);
            }
        }

        return filteredMovies;
    }

    /**
     * Action that gives a like to a movie
     * @param likedMovie
     */
    public void likeMovie(final Movie likedMovie) {

        for (Movie movie : movies) {
            if (movie.getName().equals(likedMovie.getName())) {
                movie.setNumLikes(movie.getNumLikes() + 1);
                break;
            }
        }
    }

    /**
     * Action that rates a movie
     * @param ratedMovie
     * @param rate
     */
    public void rateMovie(final Movie ratedMovie, final int rate) {

        for (Movie movie : movies) {
            if (movie.getName().equals(ratedMovie.getName())) {

                movie.getRatings().add(rate);

                int sum = 0;

                for (Integer rateidx : movie.getRatings()) {
                    sum += rateidx;
                }

                movie.setRating(1. * sum / movie.getRatings().size());

                movie.setNumRatings(movie.getNumRatings() + 1);

                break;
            }
        }

    }
}
