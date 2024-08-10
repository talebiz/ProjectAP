import conection.ServerManager;

public class ServerMain {
    public static void main(String[] args) {
        //TODO SHOW PROBLEM WITH MULTIPLAYER
//        new Thread(() -> {
        new ServerManager();
//        }).start();
//        try {
//            Thread.sleep(15000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        new MonomachiaBattle(ServerManager.getClientHandler("ali").getUser(),
//                ServerManager.getClientHandler("parsa").getUser());
    }
}