package network.chat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryUserRepository implements UserRepository {
    private final HashMap<String ,User> users = new HashMap<>();

    @Override
    public User create(String name) {
        User user = new User(name);
        users.put(name, user);
        return user;
    }

    @Override
    public User update(User user, String name) {
        return user.updateName(name);
    }

    @Override
    public void delete(User user) {
        users.remove(user.getName());
    }

    @Override
    public List<String> getUserList() {
        return new ArrayList<>(users.keySet());
    }
}
