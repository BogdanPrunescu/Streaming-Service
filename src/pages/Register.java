package pages;

import java.util.ArrayList;
import java.util.Arrays;

public final class Register extends Page {

    {
        super.pageName = "register";
        this.setEvents(new ArrayList<>(Arrays.asList("register")));
    }
    @Override
    public void accept(final ChangePageVisitor visitor) {
        visitor.visit(this);
    }
}
