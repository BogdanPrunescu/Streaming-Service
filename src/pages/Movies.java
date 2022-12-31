package pages;

import java.util.ArrayList;
import java.util.Arrays;

public final class Movies extends Page {

    {
        super.setPageName("movies");
        setEvents(new ArrayList<>(Arrays.asList("search", "filter")));
    }
    @Override
    public void accept(final ChangePageVisitor visitor) {
        visitor.visit(this);
    }
}
