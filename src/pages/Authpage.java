package pages;

public final class Authpage extends Page {

    {
        super.pageName = "authpage";
    }

    @Override
    public void accept(final ChangePageVisitor visitor) {
        visitor.visit(this);
    }
}
