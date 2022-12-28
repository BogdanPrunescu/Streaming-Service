package subscribeaction;

import manager.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubscribeManager {

    private Map<String, List<User>> subscribedUsers = new HashMap<>();

    public void subscribe(User user, String genre) {
        List<User> users = subscribedUsers.get(genre);
        if (users == null) users = new ArrayList<>();
        users.add(user);
    }

    public void notify(String genre, String movieName, String message) {
        List<User> users = subscribedUsers.get(genre);
        if (users != null) {
            for (User u : users) {
                u.update(movieName, message);
            }
        }
    }
}
