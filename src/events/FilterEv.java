package events;

public final class FilterEv extends Event {

    @Override
    public void accept(final OnPageVisitor visitor, final Object dependency) {
        visitor.visit(this, dependency);
    }
}
