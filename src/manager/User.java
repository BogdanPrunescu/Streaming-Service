package manager;

import fileio.Credentials;
import fileio.UserInput;

import java.util.ArrayList;
import java.util.Objects;

public final class User {

    private static final int PREMIUM_FREE_MOVIES = 15;

    private Credentials credentials;

    private int tokensCount;

    private int numFreePremiumMovies = PREMIUM_FREE_MOVIES;

    private ArrayList<Movie> purchasedMovies = new ArrayList<>();
    private ArrayList<Movie> watchedMovies = new ArrayList<>();
    private ArrayList<Movie> likedMovies = new ArrayList<>();
    private ArrayList<Movie> ratedMovies = new ArrayList<>();

    @Override
    public boolean equals(final Object o) {
        return this.getCredentials().getName().equals(((User) o).getCredentials().getName());
    }
    @Override
    public int hashCode() {
        return Objects.hash(credentials, tokensCount,
                numFreePremiumMovies, purchasedMovies,
                watchedMovies, likedMovies, ratedMovies);
    }

    public User(final UserInput userInput) {
        this.setCredentials(userInput.getCredentials());
        this.setTokensCount(0);
    }

    public User(final Credentials credentials) {
        this.setCredentials(credentials);
        setTokensCount(0);
    }

    public User(final User user) {
        this.credentials = new Credentials(user.credentials);
        this.tokensCount = user.tokensCount;
        this.numFreePremiumMovies = user.getNumFreePremiumMovies();

        for (Movie movie : user.purchasedMovies) {
            this.purchasedMovies.add(new Movie(movie));
        }
        for (Movie movie : user.watchedMovies) {
            this.watchedMovies.add(new Movie(movie));
        }
        for (Movie movie : user.likedMovies) {
            this.likedMovies.add(new Movie(movie));
        }
        for (Movie movie : user.ratedMovies) {
            this.ratedMovies.add(new Movie(movie));
        }

    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(final Credentials credentials) {
        this.credentials = credentials;
    }

    public Integer getTokensCount() {
        return tokensCount;
    }

    public void setTokensCount(final int tokensCount) {
        this.tokensCount = tokensCount;
    }

    public int getNumFreePremiumMovies() {
        return numFreePremiumMovies;
    }

    public void setNumFreePremiumMovies(final int numFreePremiumMovies) {
        this.numFreePremiumMovies = numFreePremiumMovies;
    }

    public ArrayList<Movie> getPurchasedMovies() {
        return purchasedMovies;
    }

    public void setPurchasedMovies(final ArrayList<Movie> purchasedMovies) {
        this.purchasedMovies = purchasedMovies;
    }

    public ArrayList<Movie> getWatchedMovies() {
        return watchedMovies;
    }

    public void setWatchedMovies(final ArrayList<Movie> watchedMovies) {
        this.watchedMovies = watchedMovies;
    }

    public ArrayList<Movie> getLikedMovies() {
        return likedMovies;
    }

    public void setLikedMovies(final ArrayList<Movie> likedMovies) {
        this.likedMovies = likedMovies;
    }

    public ArrayList<Movie> getRatedMovies() {
        return ratedMovies;
    }

    public void setRatedMovies(final ArrayList<Movie> ratedMovies) {
        this.ratedMovies = ratedMovies;
    }
}
