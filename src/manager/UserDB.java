package manager;

import fileio.UserInput;

import java.util.ArrayList;

public final class UserDB {
    private ArrayList<User> users;

    /**
     * Add element into movie database
     * @param userInput user that needs to be added
     */
    public void addElement(final UserInput userInput) {
        if (users == null) {
           users = new ArrayList<>();
        }
        User user = new User(userInput);
        if (users.contains(user)) {
            Output.printOutput("error");
        } else {
            users.add(user);
        }
    }

    /**
     * Add element into movie database
     * @param user user that needs to be added
     */
    public void addElement(final User user) {
        if (users == null) {
            users = new ArrayList<>();
        }
        users.add(user);
    }

    /**
     * Find user inside database
     * @param user
     * @return true if the user exists
     */
    public boolean findElement(final User user) {

        for (User useridx : users) {
            if (useridx.equals(user)) {
                if (useridx.getCredentials().getPassword().
                        equals(user.getCredentials().getPassword())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Get user info from database from username and password
     * @param user user that is checked
     * @return user from database or null if the user is not found
     */
    public User getElement(final User user) {
        for (User useridx : users) {
            if (useridx.equals(user)) {
                if (useridx.getCredentials().getPassword().
                        equals(user.getCredentials().getPassword())) {
                    return useridx;
                }
            }
        }
        return null;
    }
}
