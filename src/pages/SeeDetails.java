package pages;

import java.util.ArrayList;
import java.util.Arrays;

public final class SeeDetails extends Page {

    {
        super.setPageName("see details");
        this.setEvents(new ArrayList<>(Arrays.asList("purchase",
                "watch", "like", "rate", "subscribe")));
    }

    @Override
    public void accept(final ChangePageVisitor visitor) {
        visitor.visit(this);
    }
}
