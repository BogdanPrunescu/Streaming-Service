package manager;

import pages.Authpage;
import pages.Homepage;
import pages.Page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class NavigationGraph {
    private static Map<String, List<String>> adjPages = new HashMap<>();
    private Page currentPage;

    private static NavigationGraph instance = null;

    private LinkedList<ChangePageCommand> history;
    private NavigationGraph() { }

    /**
     * get NavigationGraph's instance
     * @return instance
     */
    public static NavigationGraph getInstance() {
        if (instance == null) {
            instance = new NavigationGraph();
        }
        return instance;
    }

    /**
     * Adds a page to the map
     * @param pageName name of the page that needs to be added
     */
    public void addPage(final String pageName) {
        adjPages.putIfAbsent(pageName, new ArrayList<String>());
    }

    /**
     * Link a page to one or more pages
     * @param pageName name of the page the will link to the other pages
     * @param links list of the pages that will be linked
     */
    public void addLinksToPage(final String pageName, final ArrayList<String> links) {
        adjPages.get(pageName).addAll(links);
    }

    /**
     * Initiate application's page links
     */
    public void initiateNavigationSystem() {
        instance.addPage("authpage");
        instance.addPage("login");
        instance.addPage("register");
        instance.addPage("homepage");
        instance.addPage("movies");
        instance.addPage("see details");
        instance.addPage("upgrades");
        instance.addPage("logout");


        instance.addLinksToPage("authpage",
            new ArrayList<>(Arrays.asList("authpage", "login", "register")));

        instance.addLinksToPage("homepage",
                new ArrayList<>(Arrays.asList("homepage", "movies", "upgrades", "logout")));

        instance.addLinksToPage("movies",
                new ArrayList<>(Arrays.asList("movies", "homepage", "see details", "logout")));

        instance.addLinksToPage("see details",
                new ArrayList<>(Arrays.asList("see details", "homepage",
                        "movies", "upgrades", "logout")));

        instance.addLinksToPage("upgrades",
                new ArrayList<>(Arrays.asList("upgrades", "authpage", "movies", "logout")));

        instance.currentPage = new Authpage();
    }

    /**
     * Invoker's function for the Command Pattern that execute the change page command
     * @param command command that will be executed
     */
    public void changePage(final ChangePageCommand command) {
        if (instance.history != null && AppManager.getInstance().getCurrentUser() != null) {
            instance.history.push(command);
        }
        command.execute();
    }

    /**
     * Invoker's function for the Command Pattern that calls undo function
     */
    public void back() {
        if (instance.history.isEmpty()) {
            Output.printOutput("Error");
        } else {
            ChangePageCommand command = instance.history.pop();

            if (command != null) {
                command.undo();
            }
        }
    }

    /**
     * Delete application navigation's history
     */
    public void deleteHistory() {
        instance.history = null;
    }

    /**
     * initiate navigation's history
     */
    public void historyInit() {
        instance.history = new LinkedList<>();
        instance.history.push(new ChangePageCommand(instance, null, new Homepage()));
    }

    /**
     * check if the current page is linked to dest page
     * @param dest
     * @return
     */
    public boolean isPageAvailable(final String dest) {
        return adjPages.get(instance.currentPage.getPageName()).contains(dest);
    }

    public Page getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(final Page currentPage) {
        this.currentPage = currentPage;
    }

}
