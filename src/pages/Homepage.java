package pages;

public final class Homepage extends Page {

    {
        super.pageName = "homepage";
    }
    @Override
    public void accept(final ChangePageVisitor visitor) {
        visitor.visit(this);
    }
}
