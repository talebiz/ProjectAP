import conection.ServerManager;

public class ServerMain {
    public static void main(String[] args) {
        new ServerManager();

    }
}

//try {
//InetAddress ip = InetAddress.getLocalHost();
//NetworkInterface network = NetworkInterface.getByInetAddress(ip);
//byte[] mac = network.getHardwareAddress();
//
//StringBuilder sb = new StringBuilder();
//            for (int i = 0; i < mac.length; i++) {
//        sb.append(String.format("%02X%s", mac[i],""));
//        }
//        System.out.println("MAC address: " + sb);
//
//        } catch (UnknownHostException | SocketException e) {
//        e.printStackTrace();
//        }