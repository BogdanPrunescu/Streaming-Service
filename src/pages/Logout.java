package pages;

public final class Logout extends Page {
    @Override
    public void accept(final ChangePageVisitor visitor) {
        visitor.visit(this);
    }
}
