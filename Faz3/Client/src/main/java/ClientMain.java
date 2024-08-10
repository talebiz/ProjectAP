import connection.controller.ClientManager;
import connection.model.User;
import controller.GameManager;

public class ClientMain {
    public static void main(String[] args) {
        User.getInstance();
        ClientManager.getInstance().makeSocket();
        ClientManager.getInstance().start();
        GameManager.getInstance();
    }
}