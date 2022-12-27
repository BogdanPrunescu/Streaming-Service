package pages;

import java.util.ArrayList;

public abstract class Page {

    public String pageName;
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
}
