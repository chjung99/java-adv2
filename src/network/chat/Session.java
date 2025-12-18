package network.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

import static network.tcp.SocketCloseUtil.closeAll;
import static util.MyLogger.log;

public class Session implements Runnable {
    private final Socket socket;
    private final DataInputStream input;
    private final DataOutputStream output;
    private final SessionManager sessionManager;
    private final UserRepository userRepository;
    private User connectedUser = null;
    private boolean closed = false;

    public Session(Socket socket, SessionManager sessionManager, UserRepository userRepository) throws IOException {
        this.socket = socket;
        this.input = new DataInputStream(socket.getInputStream());
        this.output = new DataOutputStream(socket.getOutputStream());
        this.sessionManager = sessionManager;
        this.sessionManager.add(this);
        this.userRepository = userRepository;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String received = input.readUTF();
                log("client -> server: " + received);

                if (received.equals(Command.EXIT.command)) {
                    break;
                }
                executeCommand(received);

            }
        } catch (IOException e) {
            log(e);
        } finally {
            if (connectedUser != null) {
                userRepository.delete(connectedUser);
                connectedUser = null;
            }
            sessionManager.remove(this);
            close();
        }
    }

    public void executeCommand(String command) throws IOException {
        if (command.startsWith(Command.JOIN.command)) {
            String[] tokens = command.split(Command.JOIN.reg);
            joinChat(tokens[1]);
        } else if (command.startsWith(Command.MESSAGE.command)) {
            String[] tokens = command.split(Command.MESSAGE.reg);
            deliveryMessage(tokens[1]);
        } else if (command.startsWith(Command.CHANGE.command)) {
            String[] tokens = command.split(Command.CHANGE.reg);
            changeUserName(tokens[1]);
        } else if (command.startsWith(Command.USERS.command)) {
            printAllUsers();
        }
    }

    public synchronized void close() {
        if (closed) {
            return;
        }
        closeAll(socket, input, output);
        closed = true;
        log("연결 종료: " + socket);
    }

    private void printAllUsers() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("=====UserList=====\n");
        List<String> userList = userRepository.getUserList();
        for (int i = 0; i < userList.size(); i++) {
            stringBuilder.append(i + ". " + userList.get(i)+"\n");
        }
        stringBuilder.append("==================\n");
        output.writeUTF(stringBuilder.toString());
    }

    private void changeUserName(String name) throws IOException {
        output.writeUTF("update name: " + name);
        connectedUser = userRepository.update(connectedUser, name);
    }

    private void deliveryMessage(String message) throws IOException {
        sessionManager.broadCastMessage(message);
    }

    private void joinChat(String name) throws IOException {
        output.writeUTF("joinChat: "+ name);
        connectedUser = userRepository.create(name);
    }

    public void uniCastMessage(String message) throws IOException {
        output.writeUTF(message);
        output.flush();
        log(this + ", client <- server: " + message);
    }
}
