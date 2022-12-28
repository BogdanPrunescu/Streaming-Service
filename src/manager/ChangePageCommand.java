package manager;

import pages.Page;

public class ChangePageCommand {

    private final NavigationGraph navigationGraph;

    public Page previousPage;

    public Page newPage;

    public ChangePageCommand(NavigationGraph navigationGraph, Page previousPage, Page newPage) {
        this.navigationGraph = navigationGraph;
        this.previousPage = previousPage;
        this.newPage = newPage;
    }

    public void execute() {
        previousPage = navigationGraph.getCurrentPage();
        navigationGraph.setCurrentPage(newPage);
        if (newPage.pageName.equals("homepage")) {
            NavigationGraph.getInstance().historyInit();

        } else if (newPage.pageName.equals("logout")) {
            NavigationGraph.getInstance().deleteHistory();

        }
    }

    public void undo() {
        navigationGraph.setCurrentPage(previousPage);
    }

    @Override
    public String toString() {
        return "ChangePageCommand{" +
                ", previousPage=" + previousPage +
                ", newPage=" + newPage +
                '}';
    }
}
