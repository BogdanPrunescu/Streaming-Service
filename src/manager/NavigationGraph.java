package manager;

import pages.Authpage;
import pages.Homepage;
import pages.Page;

import java.util.*;

public final class NavigationGraph {
    private static Map<String, List<String>> adjPages = new HashMap<>();
    private Page currentPage;

    private static NavigationGraph instance = null;

    private LinkedList<ChangePageCommand> history;
    private NavigationGraph() { }

    public static NavigationGraph getInstance() {
        if (instance == null) {
            instance = new NavigationGraph();
        }
        return instance;
    }

    public void addPage(final String pageName) {
        adjPages.putIfAbsent(pageName, new ArrayList<String>());
    }
    public void addLinksToPage(final String pageName, final ArrayList<String> links) {
        adjPages.get(pageName).addAll(links);
    }
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

    public void changePage(ChangePageCommand command) {
        if (instance.history != null && AppManager.getInstance().getCurrentUser() != null)
            instance.history.push(command);
        command.execute();
    }

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

    public void deleteHistory() {
        instance.history = null;
    }

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
        return adjPages.get(instance.currentPage.pageName).contains(dest);
    }

    public Page getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(final Page currentPage) {
        this.currentPage = currentPage;
    }

}
