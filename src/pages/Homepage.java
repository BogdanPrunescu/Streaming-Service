package pages;

public final class Homepage extends Page {
    @Override
    public void accept(final ChangePageVisitor visitor) {
        visitor.visit(this);
    }
}
