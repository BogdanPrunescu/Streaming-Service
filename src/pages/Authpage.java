package pages;

public final class Authpage extends Page {

    {
        super.setPageName("authpage");
    }

    @Override
    public void accept(final ChangePageVisitor visitor) {
        visitor.visit(this);
    }
}
