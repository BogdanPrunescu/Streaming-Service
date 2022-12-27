package manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class NavigationGraph {
    private static Map<String, List<String>> adjPages;
    private String currentPage;

    void addPage(final String pageName) {
        adjPages.putIfAbsent(pageName, new ArrayList<String>());
    }
    void addLinksToPage(final String pageName, final ArrayList<String> links) {
        adjPages.get(pageName).addAll(links);
    }

    static NavigationGraph initiateNavigationSystem() {
        NavigationGraph graph = new NavigationGraph();
        adjPages = new HashMap<>();
        graph.addPage("authpage");
        graph.addPage("login");
        graph.addPage("register");
        graph.addPage("homepage");
        graph.addPage("movies");
        graph.addPage("see details");
        graph.addPage("upgrades");
        graph.addPage("logout");


        graph.addLinksToPage("authpage",
            new ArrayList<>(Arrays.asList("authpage", "login", "register")));

        graph.addLinksToPage("homepage",
                new ArrayList<>(Arrays.asList("homepage", "movies", "upgrades", "logout")));

        graph.addLinksToPage("movies",
                new ArrayList<>(Arrays.asList("movies", "homepage", "see details", "logout")));

        graph.addLinksToPage("see details",
                new ArrayList<>(Arrays.asList("see details", "homepage",
                        "movies", "upgrades", "logout")));

        graph.addLinksToPage("upgrades",
                new ArrayList<>(Arrays.asList("upgrades", "authpage", "movies", "logout")));

        graph.setCurrentPage("authpage");

        return graph;
    }

    /**
     * check if the current page is linked to dest page
     * @param dest
     * @return
     */
    public boolean isPageAvailable(final String dest) {
        return adjPages.get(getCurrentPage()).contains(dest);
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(final String currentPage) {
        this.currentPage = currentPage;
    }
}
