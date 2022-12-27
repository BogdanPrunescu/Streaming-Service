package events;

public abstract class Event {

    /**
     * Accept method for visitor
     * @param visitor is a class of a specific page event
     * @param dependency what the event may need to execute
     */
    public void accept(final OnPageVisitor visitor, final Object dependency) { }
}
