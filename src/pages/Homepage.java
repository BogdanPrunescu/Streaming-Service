package pages;

public final class Homepage extends Page {

    {
        super.setPageName("homepage");
    }
    @Override
    public void accept(final ChangePageVisitor visitor) {
        visitor.visit(this);
    }
}
