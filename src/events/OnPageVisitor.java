package events;

import manager.*;
import fileio.ActionInput;
import fileio.Credentials;
import fileio.Filters;
import pages.Page;

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
         User currentUser = AppManager.getInstance().getCurrentUser();

         if (userBalance - count < 0) {
             Output.printOutput("Error");
         } else {
             currentUser.getCredentials().setBalance(userBalance - count);
             currentUser.setTokensCount(currentUser.getTokensCount() + count);
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
        User currentUser = AppManager.getInstance().getCurrentUser();

        if (currentUser.getLikedMovies().contains(movieSelected)) {
            Output.printOutput("Error");
            return;
        }

        if (currentUser.getWatchedMovies().contains(movieSelected)) {
            AppManager.getInstance().getMovieDB().likeMovie(movieSelected);
            currentUser.getLikedMovies().add(movieSelected);
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

            Page newPage = PageFactory.getPageByName("authpage");
            ChangePageCommand command = new ChangePageCommand(
                    NavigationGraph.getInstance(), NavigationGraph.getInstance().getCurrentPage(), newPage);
            NavigationGraph.getInstance().changePage(command);

            Output.printOutput("Error");
        } else {
            Page newPage = PageFactory.getPageByName("homepage");
            ChangePageCommand command = new ChangePageCommand(
                    NavigationGraph.getInstance(), NavigationGraph.getInstance().getCurrentPage(), newPage);
            NavigationGraph.getInstance().changePage(command);

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
        User currentUser = AppManager.getInstance().getCurrentUser();

        // film was already bought
        if (currentUser.getPurchasedMovies().contains(
                AppManager.getInstance().getCurrentMoviesList().get(0))) {
            Output.printOutput("Error");
            return;
        }

        if (currentUser.getCredentials().getAccountType().equals("premium")
                && currentUser.getNumFreePremiumMovies() > 0) {
            currentUser.setNumFreePremiumMovies(currentUser.getNumFreePremiumMovies() - 1);
                canBuyMovie = true;
        } else {
            if (currentUser.getTokensCount() - 2 > 0) {
                currentUser.setTokensCount(currentUser.getTokensCount() - 2);
                canBuyMovie = true;
            }
        }

        if (canBuyMovie) {
            currentUser.getPurchasedMovies().addAll(
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
        User currentUser = AppManager.getInstance().getCurrentUser();

        int rate = ((ActionInput) dependency).getRate();

        if (rate > MAX_RATING) {
            Output.printOutput("Error");
            return;
        }

        if (currentUser.getWatchedMovies().contains(movieSelected)) {
            boolean hadRated = AppManager.getInstance().getMovieDB().rateMovie(movieSelected, rate);
            if (!hadRated)
                currentUser.getRatedMovies().add(movieSelected);
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

        if (AppManager.getInstance().getUserDB().findElement(newUser)) {
            Output.printOutput("Error");
            return;
        }

        AppManager.getInstance().getUserDB().addElement(newUser);
        AppManager.getInstance().setCurrentUser(newUser);

        Page newPage = PageFactory.getPageByName("homepage");
        ChangePageCommand command = new ChangePageCommand(
                NavigationGraph.getInstance(), NavigationGraph.getInstance().getCurrentPage(), newPage);
        NavigationGraph.getInstance().changePage(command);

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

        User currentUser = AppManager.getInstance().getCurrentUser();

        if (currentUser.getWatchedMovies().contains(
                AppManager.getInstance().getCurrentMoviesList().get(0))) {
            return;
        }

        if (currentUser.getPurchasedMovies().contains(
                AppManager.getInstance().getCurrentMoviesList().get(0))) {
            currentUser.getWatchedMovies().addAll(
                    AppManager.getInstance().getCurrentMoviesList());
            Output.printOutput(null);
        } else {
            Output.printOutput("Error");
        }
    }
}
