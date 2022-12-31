package pages;

public final class Logout extends Page {

    {
        super.setPageName("logout");
    }
    @Override
    public void accept(final ChangePageVisitor visitor) {
        visitor.visit(this);
    }
}
