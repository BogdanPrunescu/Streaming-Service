package pages;

import java.util.ArrayList;

public abstract class Page {

    private String pageName;
    private ArrayList<String> events = new ArrayList<>();

    /**
     * Accept method for visitor pattern
     * @param visitor
     */
    public void accept(final ChangePageVisitor visitor) { }

    public final ArrayList<String> getEvents() {
        return events;
    }

    public final void setEvents(final ArrayList<String> events) {
        this.events = events;
    }

    /**
     * Safe to use in extended classes
     * @return
     */
    public String getPageName() {
        return pageName;
    }

    /**
     * Safe to use in extended classes
     */
    public void setPageName(final String pageName) {
        this.pageName = pageName;
    }
}
