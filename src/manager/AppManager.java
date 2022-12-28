package manager;

import fileio.*;
import pages.Authpage;
import pages.ChangePageVisitor;
import pages.Page;
import com.fasterxml.jackson.databind.node.ArrayNode;
import events.Event;
import events.OnPageVisitor;
import subscribeaction.SubscribeManager;

import java.util.*;

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
    private String selectedMovie = null;
    private SubscribeManager subscribeManager;

    /**
     * Initialize the platform
     * @param input
     * @param myOutput
     */
    public void initiateApp(final Input input, final ArrayNode myOutput) {

       NavigationGraph.getInstance().initiateNavigationSystem();

       subscribeManager = new SubscribeManager();

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

                if (NavigationGraph.getInstance().isPageAvailable(action.getPage())) {

                    Page newPage = PageFactory.getPageByName(action.getPage());
                    ChangePageCommand command = new ChangePageCommand(
                            NavigationGraph.getInstance(), NavigationGraph.getInstance().getCurrentPage(), newPage);
                    NavigationGraph.getInstance().changePage(command);

                    if (action.getMovie() != null) {
                        setSelectedMovie(action.getMovie());
                    }

                    ChangePageVisitor visitor = new ChangePageVisitor();
                    NavigationGraph.getInstance().getCurrentPage().accept(visitor);

                    setSelectedMovie(null);
                    continue;
                }
                Output.printOutput("Error");

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

                if (action.getFeature().equals("add")) {
                    movieDB.addElement(action.getAddedMovie());

                } else if (action.getFeature().equals("delete")) {
                    movieDB.removeElement(action.getDeletedMovie());
                }

            } else if (action.getType().equals("subscribe")) {
                boolean canSubscribe = false;
                if (NavigationGraph.getInstance().getCurrentPage().pageName.equals("see details")) {
                    for (String genre : currentMoviesList.get(0).getGenres()) {
                        if (genre.equals(action.getSubscribedGenre())) {
                            subscribeManager.subscribe(currentUser, genre);
                            canSubscribe = true;
                        }
                    }
                }
                if (!canSubscribe) {
                    Output.printOutput("Error");
                }

            } else if (action.getType().equals("back")) {
                if (currentUser != null) {
                    NavigationGraph.getInstance().back();

                    ChangePageVisitor visitor = new ChangePageVisitor();
                    NavigationGraph.getInstance().getCurrentPage().accept(visitor);

                    continue;
                }
                Output.printOutput("Error");

            }
        }
        if (currentUser != null) {
            if (currentUser.getCredentials().getAccountType().equals("premium")) {
                ArrayList<Movie> likedMovies = currentUser.getLikedMovies();
                ArrayList<Movie> movieArrayList = movieDB.getMoviesOfUser(currentUser);
                Map<String, List<Integer>> likedgenres = new HashMap<>();

                for (Movie m : likedMovies) {
                    for (String genre : m.getGenres()) {


                    }
                }
                Collections.sort(movieArrayList, new Comparator<Movie>() {
                    @Override
                    public int compare(Movie o1, Movie o2) {
                        return 0;
                    }
                });
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

    public SubscribeManager getSubscribeManager() {
        return subscribeManager;
    }

    public void setSubscribeManager(SubscribeManager subscribeManager) {
        this.subscribeManager = subscribeManager;
    }
}
