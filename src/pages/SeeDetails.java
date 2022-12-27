package pages;

import java.util.ArrayList;
import java.util.Arrays;

public final class SeeDetails extends Page {

    {
        this.setEvents(new ArrayList<>(Arrays.asList("purchase",
                "watch", "like", "rate")));
    }

    @Override
    public void accept(final ChangePageVisitor visitor) {
        visitor.visit(this);
    }
}
