package network.chat;

import java.util.List;

public interface UserRepository {
    User create(String name);

    User update(User user, String name);

    void delete(User user);

    List<String> getUserList();
}
