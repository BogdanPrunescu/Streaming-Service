package pages;

import java.util.ArrayList;
import java.util.Arrays;

public final class Login extends Page {

    {
        this.setEvents(new ArrayList<>(Arrays.asList("login")));
    }
    @Override
    public void accept(final ChangePageVisitor visitor) {
        visitor.visit(this);
    }
}
