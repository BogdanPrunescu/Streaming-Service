package pages;

public final class Authpage extends Page {

    @Override
    public void accept(final ChangePageVisitor visitor) {
        visitor.visit(this);
    }
}
