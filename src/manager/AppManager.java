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

    private static AppManager instance = null;

    public static AppManager getInstance() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }
    private UserDB userDB;
    private MovieDB movieDB;
    private User currentUser = null;
    private ArrayList<Movie> currentMoviesList;
    private ArrayNode output;
    private Page currentPage;
    private String selectedMovie = null;

    /**
     * Initialize the platform
     * @param input
     * @param myOutput
     */
    public void initiateApp(final Input input, final ArrayNode myOutput) {

       NavigationGraph.getInstance().initiateNavigationSystem();

       setUserDB(new UserDB());
       for (UserInput user : input.getUsers()) {
           getUserDB().addElement(user);
       }

       setMovieDB(new MovieDB());
       for (MovieInput movie : input.getMovies()) {
           getMovieDB().addElement(movie);
       }

       setOutput(myOutput);
       setCurrentMoviesList(new ArrayList<>());

        startApp(input);
    }

    private void startApp(final Input input) {

        for (ActionInput action : input.getActions()) {

            if (action.getType().equals("change page")) {

                ChangePageVisitor visitor = new ChangePageVisitor();
                if (NavigationGraph.getInstance().isPageAvailable(action.getPage())) {

                    Page newPage = PageFactory.getPageByName(action.getPage());
                    ChangePageCommand command = new ChangePageCommand(
                            NavigationGraph.getInstance(), NavigationGraph.getInstance().getCurrentPage(), newPage);
                    NavigationGraph.getInstance().changePage(command);

                    if (action.getMovie() != null) {
                        setSelectedMovie(action.getMovie());
                    }
                    NavigationGraph.getInstance().getCurrentPage().accept(visitor);
                    setSelectedMovie(null);
                } else {
                    Output.printOutput("Error");
                }
            } else if (action.getType().equals("on page")) {
                if (!NavigationGraph.getInstance().getCurrentPage().getEvents().isEmpty()
                        && NavigationGraph.getInstance().getCurrentPage().getEvents().contains(action.getFeature())) {

                    Event newEvent = PageFactory.getEventByName(action.getFeature());

                    OnPageVisitor visitor = new OnPageVisitor();
                    newEvent.accept(visitor, action);
                    continue;
                }
                Output.printOutput("Error");
            } else if (action.getType().equals("database")) {

            } else if (action.getType().equals("subscribe")) {

            } else if (action.getType().equals("back")) {
                if (currentUser != null) {
                    NavigationGraph.getInstance().back();
                    continue;
                }

                Output.printOutput("Error");

            }
        }
    }


    public UserDB getUserDB() {
        return userDB;
    }

    public void setUserDB(final UserDB userDB) {
        this.userDB = userDB;
    }

    public MovieDB getMovieDB() {
        return movieDB;
    }

    public void setMovieDB(final MovieDB movieDB) {
        this.movieDB = movieDB;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(final User currentUser) {
        this.currentUser = currentUser;
    }

    public ArrayList<Movie> getCurrentMoviesList() {
        return currentMoviesList;
    }

    public void setCurrentMoviesList(final ArrayList<Movie> currentMoviesList) {
        this.currentMoviesList = currentMoviesList;
    }

    public ArrayNode getOutput() {
        return output;
    }

    public void setOutput(final ArrayNode output) {
        this.output = output;
    }
    public String getSelectedMovie() {
        return selectedMovie;
    }

    public void setSelectedMovie(final String selectedMovie) {
        this.selectedMovie = selectedMovie;
    }
}
