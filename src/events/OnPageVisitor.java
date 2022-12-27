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
        int userTokens = AppManager.getInstance().getCurrentUser().getTokensCount();
        if (userTokens - PREMIUM_PRICE < 0) {
            Output.printOutput("Error");
        } else {
            AppManager.getInstance().getCurrentUser().setTokensCount(userTokens - PREMIUM_PRICE);
            AppManager.getInstance().getCurrentUser().getCredentials().setAccountType("premium");
        }
    }

    /**
     * Buy tokens event
     * @param buyTokensEv visitor
     * @param dependency what the event may need to execute
     */
    public void visit(final BuyTokensEv buyTokensEv, final Object dependency) {
         int count = ((ActionInput) dependency).getCount();
         int userBalance = AppManager.getInstance().getCurrentUser().getCredentials().getBalance();
         if (userBalance - count < 0) {
             Output.printOutput("Error");
         } else {
             AppManager.getInstance().getCurrentUser().getCredentials().setBalance(userBalance - count);
             AppManager.getInstance().getCurrentUser().setTokensCount(count);
         }
    }

    /**
     * Filter event
     * @param filterEv visitor
     * @param dependency what the event may need to execute
     */
    public void visit(final FilterEv filterEv, final Object dependency) {
        Filters filters = ((ActionInput) dependency).getFilters();
        ArrayList<Movie> filteredList = AppManager.getInstance().getMovieDB().getMoviesbyFilters(filters);

        AppManager.getInstance().setCurrentMoviesList(new ArrayList<>());
        for (Movie movie : filteredList) {
            AppManager.getInstance().getCurrentMoviesList().add(movie);
        }


        Output.printOutput(null);
    }

    /**
     * Like event
     * @param likeEv visitor
     * @param dependency what the event may need to execute
     */
    public void visit(final LikeEv likeEv, final Object dependency) {

        Movie movieSelected = AppManager.getInstance().getCurrentMoviesList().get(0);

        if (AppManager.getInstance().getCurrentUser().getWatchedMovies().contains(movieSelected)) {
            AppManager.getInstance().getMovieDB().likeMovie(movieSelected);
            AppManager.getInstance().getCurrentUser().getLikedMovies().add(movieSelected);
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
        if (!AppManager.getInstance().getUserDB().findElement(new User(credentials))) {
            AppManager.getInstance().changePage("authpage");
            Output.printOutput("Error");
        } else {
            AppManager.getInstance().changePage("homepage");
            AppManager.getInstance().setCurrentUser(
                    AppManager.getInstance().getUserDB().getElement(new User(credentials)));
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

        if (AppManager.getInstance().getCurrentUser().getCredentials().getAccountType().equals("premium")
                && AppManager.getInstance().getCurrentUser().getNumFreePremiumMovies() > 0) {
                AppManager.getInstance().getCurrentUser().setNumFreePremiumMovies(
                        AppManager.getInstance().getCurrentUser().getNumFreePremiumMovies() - 1);
                canBuyMovie = true;
        } else {
            if (AppManager.getInstance().getCurrentUser().getTokensCount() - 2 > 0) {
                AppManager.getInstance().getCurrentUser().setTokensCount(
                        AppManager.getInstance().getCurrentUser().getTokensCount() - 2);
                canBuyMovie = true;
            }
        }

        if (canBuyMovie) {
            AppManager.getInstance().getCurrentUser().getPurchasedMovies().addAll(
                    AppManager.getInstance().getCurrentMoviesList());
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

        Movie movieSelected = AppManager.getInstance().getCurrentMoviesList().get(0);

        int rate = ((ActionInput) dependency).getRate();

        if (rate > MAX_RATING) {
            Output.printOutput("Error");
            return;
        }

        if (AppManager.getInstance().getCurrentUser().getWatchedMovies().contains(movieSelected)) {
            AppManager.getInstance().getMovieDB().rateMovie(movieSelected, rate);
            AppManager.getInstance().getCurrentUser().getRatedMovies().add(movieSelected);
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
        AppManager.getInstance().getUserDB().addElement(newUser);
        AppManager.getInstance().setCurrentUser(newUser);
        AppManager.getInstance().changePage("homepage");
        Output.printOutput(null);
    }

    /**
     * Search event
     * @param searchEv visitor
     * @param dependency what the event may need to execute
     */
    public void visit(final SearchEv searchEv, final Object dependency) {
        String searchString = ((ActionInput) dependency).getStartsWith();
        AppManager.getInstance().setCurrentMoviesList(AppManager.getInstance().getMovieDB().getMoviesbyStart(searchString));
        Output.printOutput(null);
    }

    /**
     * Watch event
     * @param watchEv visitor
     * @param dependency what the event may need to execute
     */
    public void visit(final WatchEv watchEv, final Object dependency) {

        if (AppManager.getInstance().getCurrentUser().getPurchasedMovies().contains(
                AppManager.getInstance().getCurrentMoviesList().get(0))) {
            AppManager.getInstance().getCurrentUser().getWatchedMovies().addAll(
                    AppManager.getInstance().getCurrentMoviesList());
            Output.printOutput(null);
        } else {
            Output.printOutput("Error");
        }
    }
}
