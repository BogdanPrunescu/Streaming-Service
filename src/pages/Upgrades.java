package pages;

import java.util.ArrayList;
import java.util.Arrays;

public final class Upgrades extends Page {

    {
        super.setPageName("upgrades");
        this.setEvents(new ArrayList<>(Arrays.asList("buy tokens", "buy premium account")));
    }
    @Override
    public void accept(final ChangePageVisitor visitor) {
        visitor.visit(this);
    }
}
