package events;

public final class RegisterEv extends Event {

    @Override
    public void accept(final OnPageVisitor visitor, final Object dependency) {
        visitor.visit(this, dependency);
    }
}
