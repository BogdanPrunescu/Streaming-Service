package manager;

import pages.Authpage;
import pages.ChangePageVisitor;
import pages.Page;
import com.fasterxml.jackson.databind.node.ArrayNode;
import events.Event;
import events.OnPageVisitor;
import fileio.ActionInput;
import fileio.Input;
import fileio.MovieInput;
import fileio.UserInput;

import java.util.ArrayList;

public final class AppManager {

    private AppManager() { }

    private static UserDB userDB;
    private static MovieDB movieDB;
    private static NavigationGraph navigationGraph;
    private static User currentUser = null;
    private static ArrayList<Movie> currentMoviesList;
    private static ArrayNode output;
    private static Page currentPage;
    private static String selectedMovie = null;

    /**
     * Initialize the platform
     * @param input
     * @param myOutput
     */
    public static void initiateApp(final Input input, final ArrayNode myOutput) {

       setNavigationGraph(NavigationGraph.initiateNavigationSystem());

       setUserDB(new UserDB());
       for (UserInput user : input.getUsers()) {
           getUserDB().addElement(user);
       }

       setMovieDB(new MovieDB());
       for (MovieInput movie : input.getMovies()) {
           getMovieDB().addElement(movie);
       }

       AppManager.setOutput(myOutput);
       setCurrentPage(new Authpage());
       setCurrentMoviesList(new ArrayList<>());

        startApp(input);
    }

    private static void startApp(final Input input) {

        for (ActionInput action : input.getActions()) {

            if (action.getType().equals("change page")) {

                ChangePageVisitor visitor = new ChangePageVisitor();
                if (getNavigationGraph().isPageAvailable(action.getPage())) {
                    changePage(action.getPage());
                    if (action.getMovie() != null) {
                        setSelectedMovie(action.getMovie());
                    }
                    getCurrentPage().accept(visitor);
                    setSelectedMovie(null);
                } else {
                    Output.printOutput("Error");
                }
            } else if (action.getType().equals("on page")) {
                if (!getCurrentPage().getEvents().isEmpty()
                        && getCurrentPage().getEvents().contains(action.getFeature())) {

                    Event newEvent = PageFactory.getEventByName(action.getFeature());

                    OnPageVisitor visitor = new OnPageVisitor();
                    newEvent.accept(visitor, action);
                    continue;
                }

                Output.printOutput("Error");
            }
        }
    }

    /**
     * Changes the page to a new page
     * Must verify first is the mutation is possible
     * @param dest destination page
     */
    public static void changePage(final String dest) {
        getNavigationGraph().setCurrentPage(dest);
        setCurrentPage(PageFactory.getPageByName(dest));
    }

    public static UserDB getUserDB() {
        return userDB;
    }

    public static void setUserDB(final UserDB userDB) {
        AppManager.userDB = userDB;
    }

    public static MovieDB getMovieDB() {
        return movieDB;
    }

    public static void setMovieDB(final MovieDB movieDB) {
        AppManager.movieDB = movieDB;
    }

    public static NavigationGraph getNavigationGraph() {
        return navigationGraph;
    }

    public static void setNavigationGraph(final NavigationGraph navigationGraph) {
        AppManager.navigationGraph = navigationGraph;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(final User currentUser) {
        AppManager.currentUser = currentUser;
    }

    public static ArrayList<Movie> getCurrentMoviesList() {
        return currentMoviesList;
    }

    public static void setCurrentMoviesList(final ArrayList<Movie> currentMoviesList) {
        AppManager.currentMoviesList = currentMoviesList;
    }

    public static ArrayNode getOutput() {
        return output;
    }

    public static void setOutput(final ArrayNode output) {
        AppManager.output = output;
    }

    public static Page getCurrentPage() {
        return currentPage;
    }

    public static void setCurrentPage(final Page currentPage) {
        AppManager.currentPage = currentPage;
    }

    public static String getSelectedMovie() {
        return selectedMovie;
    }

    public static void setSelectedMovie(final String selectedMovie) {
        AppManager.selectedMovie = selectedMovie;
    }
}
