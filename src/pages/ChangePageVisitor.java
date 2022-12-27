package pages;

import manager.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

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
        NavigationGraph.getInstance().historyInit();
        return;
    }

    /**
     * Movies visitor -> prints movies when entering the page
     * @param visitor
     */
    public void visit(final Movies visitor) {
        AppManager.getInstance().setCurrentMoviesList(AppManager.getInstance().getMovieDB().
                getMoviesOfUser(AppManager.getInstance().getCurrentUser()));
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
        AppManager.getInstance().setCurrentMoviesList(new ArrayList<>());
        AppManager.getInstance().setCurrentUser(null);

        Page newPage = PageFactory.getPageByName("authpage");
        ChangePageCommand command = new ChangePageCommand(
                NavigationGraph.getInstance(), NavigationGraph.getInstance().getCurrentPage(), newPage);
        NavigationGraph.getInstance().changePage(command);
        NavigationGraph.getInstance().deleteHistory();
    }
    /**
     * SeeDetails visitor show details about selected movie
     * @param visitor
     */
    public void visit(final SeeDetails visitor) {
        Movie selectedMovie = null;
        for (Movie movie : AppManager.getInstance().getCurrentMoviesList()) {

            if (movie.getName().equals(AppManager.getInstance().getSelectedMovie())) {
                selectedMovie = movie;
            }
        }
        if (selectedMovie == null) {
            Page newPage = PageFactory.getPageByName("movies");
            ChangePageCommand command = new ChangePageCommand(
                    NavigationGraph.getInstance(), NavigationGraph.getInstance().getCurrentPage(), newPage);
            NavigationGraph.getInstance().changePage(command);
            Output.printOutput("Error");
        } else {
            AppManager.getInstance().setCurrentMoviesList(new ArrayList<>(Arrays.asList(selectedMovie)));
            Output.printOutput(null);
        }
    }
}
