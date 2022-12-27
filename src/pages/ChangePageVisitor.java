package pages;

import manager.AppManager;
import manager.Movie;
import manager.Output;

import java.util.ArrayList;
import java.util.Arrays;

public final class ChangePageVisitor {

    /**
     * Login visitor (does nothing)
     * @param visitor
     */
    public void visit(final Login visitor) {
        return;
    }

    /**
     * Register visitor (does nothing)
     * @param visitor
     */
    public void visit(final Register visitor) {
        return;
    }

    /**
     * Authpage visitor (does nothing)
     * @param visitor
     */
    public void visit(final Authpage visitor) {
        return;
    }

    /**
     * Homepage visitor (does nothing)
     * @param visitor
     */
    public void visit(final Homepage visitor) {
        return;
    }

    /**
     * Movies visitor -> prints movies when entering the page
     * @param visitor
     */
    public void visit(final Movies visitor) {
        AppManager.setCurrentMoviesList(AppManager.getMovieDB().
                getMoviesOfUser(AppManager.getCurrentUser()));
        Output.printOutput(null);
    }

    /**
     * Upgrades visitor (does nothing)
     * @param visitor
     */
    public void visit(final Upgrades visitor) {
        return;
    }

    /**
     * Logout visitor -> logs out the user sends him to auth page
     * @param visitor
     */
    public void visit(final Logout visitor) {
        AppManager.setCurrentMoviesList(new ArrayList<>());
        AppManager.setCurrentUser(null);
        AppManager.changePage("authpage");
    }
    /**
     * SeeDetails visitor show details about selected movie
     * @param visitor
     */
    public void visit(final SeeDetails visitor) {
        Movie selectedMovie = null;
        for (Movie movie : AppManager.getCurrentMoviesList()) {

            if (movie.getName().equals(AppManager.getSelectedMovie())) {
                selectedMovie = movie;
            }
        }
        if (selectedMovie == null) {
            AppManager.changePage("movies");
            Output.printOutput("Error");
        } else {
            AppManager.setCurrentMoviesList(new ArrayList<>(Arrays.asList(selectedMovie)));
            Output.printOutput(null);
        }
    }
}
