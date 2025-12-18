package network.chat;

public enum Command {
    JOIN("/join|", "/join\\|"),
    MESSAGE("/message|", "/message\\|"),
    CHANGE("/change|", "/change\\|"),
    USERS("/users", ""),
    EXIT("/exit", "");

    public final String command;
    public final String reg;

    Command(String command, String reg) {
        this.command = command;
        this.reg = reg;
    }
}
