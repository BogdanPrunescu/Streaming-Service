package manager;

import pages.Page;

public final class ChangePageCommand {

    private final NavigationGraph navigationGraph;

    private Page previousPage;

    private Page newPage;

    public ChangePageCommand(final NavigationGraph navigationGraph,
                             final Page previousPage, final Page newPage) {
        this.navigationGraph = navigationGraph;
        this.previousPage = previousPage;
        this.newPage = newPage;
    }

    /**
     * Command Pattern execute function
     */
    public void execute() {
        previousPage = navigationGraph.getCurrentPage();
        navigationGraph.setCurrentPage(newPage);
        if (newPage.getPageName().equals("homepage")) {
            NavigationGraph.getInstance().historyInit();

        } else if (newPage.getPageName().equals("logout")) {
            NavigationGraph.getInstance().deleteHistory();

        }
    }

    /**
     * Command Pattern undo function
     */
    public void undo() {
        navigationGraph.setCurrentPage(previousPage);
    }
}
