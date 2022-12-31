package manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class SubscribeManager {

    private Map<String, List<User>> subscribedUsers = new HashMap<>();

    /**
     * Observer's Pattern function that subscribes a user to a genre
     * @param user user that subscribes
     * @param genre genre that the users subscribes to
     */
    public void subscribe(final User user, final String genre) {
        List<User> users = subscribedUsers.get(genre);
        if (users == null) {
            subscribedUsers.put(genre, new ArrayList<>());
            users = subscribedUsers.get(genre);
        } else if (users.contains(user)) {
            Output.printOutput("Error");
            return;
        }
        users.add(user);
    }

    /**
     * Observer's Pattern that notifies all users when an update to the
     * movie database has happend
     * @param genre genre of the movie modified
     * @param movieName movie's name
     * @param message the message printed in user's notification feed
     */
    public void notify(final String genre, final String movieName, final String message) {
        List<User> users = subscribedUsers.get(genre);
        if (users != null) {
            for (User u : users) {
                u.update(movieName, message);
            }
        }
    }
}
