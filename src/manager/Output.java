package manager;

import java.util.ArrayList;

public final class Output {

    private String error = null;

    private ArrayList<Movie> currentMoviesList;

    private User currentUser = null;

    public String getError() {
        return error;
    }

    public void setError(final String error) {
        this.error = error;
    }

    public ArrayList<Movie> getCurrentMoviesList() {
        return currentMoviesList;
    }

    public void setCurrentMoviesList(final ArrayList<Movie> currentMoviesList) {
        this.currentMoviesList = currentMoviesList;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(final User currentUser) {
        this.currentUser = currentUser;
    }

    public Output(final String error, final ArrayList<Movie> currentMoviesList,
                  final User currentUser) {
        this.error = error;
        this.currentMoviesList = null;
        if (currentMoviesList != null) {
            this.currentMoviesList = new ArrayList<>();
            for (Movie movie : currentMoviesList) {
                this.currentMoviesList.add(new Movie(movie));
            }
        }

        if (currentUser != null) {
            this.currentUser = new User(currentUser);
        }
    }

    /**
     * Adds output to ArrayNode to be later printed
     * @param error used to modify the error parameter
     */
    public static void printOutput(final String error) {
        Output output;
        if (error != null) {
            output = new Output(error,
                    new ArrayList<>(), null);
        } else {
            output = new Output(null,
                    AppManager.getInstance().getCurrentMoviesList(), AppManager.getInstance().getCurrentUser());
        }
        AppManager.getInstance().getOutput().addPOJO(output);
    }
}
