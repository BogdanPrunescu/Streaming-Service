package manager;

import pages.Authpage;
import pages.Homepage;
import pages.Login;
import pages.Logout;
import pages.Movies;
import pages.Page;
import pages.Register;
import pages.SeeDetails;
import pages.Upgrades;
import events.BuyPremiumEv;
import events.BuyTokensEv;
import events.Event;
import events.FilterEv;
import events.LikeEv;
import events.LoginEv;
import events.PurchaseEv;
import events.RateEv;
import events.RegisterEv;
import events.SearchEv;
import events.WatchEv;

public final class PageFactory {

    private PageFactory() { }

    /**
     * Change page class by the String given as parameter
     * @param pageName
     * @return the new class given to the page
     */
    public static Page getPageByName(final String pageName) {
        switch (pageName) {
            case "authpage" -> {
                return new Authpage();
            }
            case "login" -> {
                return new Login();
            }
            case "register" -> {
                return new Register();
            }
            case "homepage" -> {
                return new Homepage();
            }
            case "movies" -> {
                return new Movies();
            }
            case "see details" -> {
                return new SeeDetails();
            }
            case "upgrades" -> {
                return new Upgrades();
            }
            case "logout" -> {
                return new Logout();
            }
            default -> {
                return null;
            }
        }
    }

    /**
     * Change event class by the String given as parameter
     * @param eventName
     * @return the new class given to the event
     */
    public static Event getEventByName(final String eventName) {
        switch (eventName) {
            case "login" -> {
                return new LoginEv();
            }
            case "register" -> {
                return new RegisterEv();
            }
            case "search" -> {
                return new SearchEv();
            }
            case "filter" -> {
                return new FilterEv();
            }
            case "purchase" -> {
                return new PurchaseEv();
            }
            case "watch" -> {
                return new WatchEv();
            }
            case "like" -> {
                return new LikeEv();
            }
            case "rate" -> {
                return new RateEv();
            }
            case "buy premium account" -> {
                return new BuyPremiumEv();
            }
            case "buy tokens" -> {
                return new BuyTokensEv();
            }
            default -> {
                return null;
            }
        }
    }
}
