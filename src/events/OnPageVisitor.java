package events;

import manager.AppManager;
import manager.Movie;
import manager.Output;
import manager.User;
import fileio.ActionInput;
import fileio.Credentials;
import fileio.Filters;

import java.util.ArrayList;

public final class OnPageVisitor {

    private static final int PREMIUM_PRICE = 10;
    private static final int MAX_RATING = 5;

    /**
     * Buy premium event
     * @param buyPremiumEv visitor
     * @param dependency what the event may need to execute
     */
    public void visit(final BuyPremiumEv buyPremiumEv, final Object dependency) {
        int userTokens = AppManager.getCurrentUser().getTokensCount();
        if (userTokens - PREMIUM_PRICE < 0) {
            Output.printOutput("Error");
        } else {
            AppManager.getCurrentUser().setTokensCount(userTokens - PREMIUM_PRICE);
            AppManager.getCurrentUser().getCredentials().setAccountType("premium");
        }
    }

    /**
     * Buy tokens event
     * @param buyTokensEv visitor
     * @param dependency what the event may need to execute
     */
    public void visit(final BuyTokensEv buyTokensEv, final Object dependency) {
         int count = ((ActionInput) dependency).getCount();
         int userBalance = AppManager.getCurrentUser().getCredentials().getBalance();
         if (userBalance - count < 0) {
             Output.printOutput("Error");
         } else {
             AppManager.getCurrentUser().getCredentials().setBalance(userBalance - count);
             AppManager.getCurrentUser().setTokensCount(count);
         }
    }

    /**
     * Filter event
     * @param filterEv visitor
     * @param dependency what the event may need to execute
     */
    public void visit(final FilterEv filterEv, final Object dependency) {
        Filters filters = ((ActionInput) dependency).getFilters();
        ArrayList<Movie> filteredList = AppManager.getMovieDB().getMoviesbyFilters(filters);

        AppManager.setCurrentMoviesList(new ArrayList<>());
        for (Movie movie : filteredList) {
            AppManager.getCurrentMoviesList().add(movie);
        }


        Output.printOutput(null);
    }

    /**
     * Like event
     * @param likeEv visitor
     * @param dependency what the event may need to execute
     */
    public void visit(final LikeEv likeEv, final Object dependency) {

        Movie movieSelected = AppManager.getCurrentMoviesList().get(0);

        if (AppManager.getCurrentUser().getWatchedMovies().contains(movieSelected)) {
            AppManager.getMovieDB().likeMovie(movieSelected);
            AppManager.getCurrentUser().getLikedMovies().add(movieSelected);
            Output.printOutput(null);
        } else {
            Output.printOutput("Error");
        }
    }

    /**
     * Login event
     * @param loginEv visitor
     * @param dependency what the event may need to execute
     */
    public void visit(final LoginEv loginEv, final Object dependency) {
        Credentials credentials = ((ActionInput) dependency).getCredentials();
        if (!AppManager.getUserDB().findElement(new User(credentials))) {
            AppManager.changePage("authpage");
            Output.printOutput("Error");
        } else {
            AppManager.changePage("homepage");
            AppManager.setCurrentUser(AppManager.getUserDB().getElement(new User(credentials)));
            Output.printOutput(null);
        }
    }

    /**
     * Purchase event
     * @param purchaseEv visitor
     * @param dependency what the event may need to execute
     */
    public void visit(final PurchaseEv purchaseEv, final Object dependency) {

        boolean canBuyMovie = false;

        if (AppManager.getCurrentUser().getCredentials().getAccountType().equals("premium")
                && AppManager.getCurrentUser().getNumFreePremiumMovies() > 0) {
                AppManager.getCurrentUser().setNumFreePremiumMovies(
                        AppManager.getCurrentUser().getNumFreePremiumMovies() - 1);
                canBuyMovie = true;
        } else {
            if (AppManager.getCurrentUser().getTokensCount() - 2 > 0) {
                AppManager.getCurrentUser().setTokensCount(
                        AppManager.getCurrentUser().getTokensCount() - 2);
                canBuyMovie = true;
            }
        }

        if (canBuyMovie) {
            AppManager.getCurrentUser().getPurchasedMovies().addAll(
                    AppManager.getCurrentMoviesList());
            Output.printOutput(null);
        } else {
            Output.printOutput("Error");
        }
    }

    /**
     * Rate event
     * @param rateEv visitor
     * @param dependency what the event may need to execute
     */
    public void visit(final RateEv rateEv, final Object dependency) {

        Movie movieSelected = AppManager.getCurrentMoviesList().get(0);

        int rate = ((ActionInput) dependency).getRate();

        if (rate > MAX_RATING) {
            Output.printOutput("Error");
            return;
        }

        if (AppManager.getCurrentUser().getWatchedMovies().contains(movieSelected)) {
            AppManager.getMovieDB().rateMovie(movieSelected, rate);
            AppManager.getCurrentUser().getRatedMovies().add(movieSelected);
            Output.printOutput(null);
        } else {
            Output.printOutput("Error");
        }
    }

    /**
     * Register event
     * @param registerEv visitor
     * @param dependency what the event may need to execute
     */
    public void visit(final RegisterEv registerEv, final Object dependency) {
        Credentials credentials = ((ActionInput) dependency).getCredentials();
        User newUser = new User(credentials);
        AppManager.getUserDB().addElement(newUser);
        AppManager.setCurrentUser(newUser);
        AppManager.changePage("homepage");
        Output.printOutput(null);
    }

    /**
     * Search event
     * @param searchEv visitor
     * @param dependency what the event may need to execute
     */
    public void visit(final SearchEv searchEv, final Object dependency) {
        String searchString = ((ActionInput) dependency).getStartsWith();
        AppManager.setCurrentMoviesList(AppManager.getMovieDB().getMoviesbyStart(searchString));
        Output.printOutput(null);
    }

    /**
     * Watch event
     * @param watchEv visitor
     * @param dependency what the event may need to execute
     */
    public void visit(final WatchEv watchEv, final Object dependency) {

        if (AppManager.getCurrentUser().getPurchasedMovies().contains(
                AppManager.getCurrentMoviesList().get(0))) {
            AppManager.getCurrentUser().getWatchedMovies().addAll(
                    AppManager.getCurrentMoviesList());
            Output.printOutput(null);
        } else {
            Output.printOutput("Error");
        }
    }
}
