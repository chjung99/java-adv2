package network.chat;

public class User {
    private String name;

    public User(String name) {
        this.name = name;
    }

    public User updateName(String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }
}
