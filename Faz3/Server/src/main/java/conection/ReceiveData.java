package conection;

import com.fasterxml.jackson.databind.ObjectMapper;
import database.EntityData;
import model.game.Collectible;
import model.game.Epsilon;
import model.game.Shot;
import model.game.enemies.miniBoss.Barricados;
import model.game.enemies.miniBoss.BlackOrb;
import model.game.enemies.normal.*;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class ReceiveData extends Thread {
    private final DatagramSocket socket;
    boolean running;
    private final EntityData data;

    public ReceiveData(EntityData data, int port) {
        this.data = data;
        try {
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        Runtime.getRuntime().addShutdownHook(new Thread(socket::close));
    }

    @Override
    public void run() {
        super.run();
        running = true;
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] receivePacket = new byte[1000];
        while (running) {
            waiting(100);
            data.clear();

            int numbers;

            DatagramPacket packet;
            try {
                packet = new DatagramPacket(receivePacket, receivePacket.length);
                socket.receive(packet);
                data.setEpsilon(objectMapper.readValue(receivePacket, Epsilon.class));
                receivePacket = new byte[1000];
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            try {
                packet = new DatagramPacket(receivePacket, receivePacket.length);
                socket.receive(packet);
                numbers = Integer.parseInt(objectMapper.readValue(receivePacket, String.class));
                receivePacket = new byte[1000];
                for (int i = 0; i < numbers; i++) {
                    packet = new DatagramPacket(receivePacket, receivePacket.length);
                    socket.receive(packet);
                    data.addShot(objectMapper.readValue(receivePacket, Shot.class));
                }
                receivePacket = new byte[1000];
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            try {
                packet = new DatagramPacket(receivePacket, receivePacket.length);
                socket.receive(packet);
                numbers = Integer.parseInt(objectMapper.readValue(receivePacket, String.class));
                receivePacket = new byte[1000];
                for (int i = 0; i < numbers; i++) {
                    packet = new DatagramPacket(receivePacket, receivePacket.length);
                    socket.receive(packet);
                    data.addSquarantine(objectMapper.readValue(receivePacket, Squarantine.class));
                }
                receivePacket = new byte[1000];
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            try {
                packet = new DatagramPacket(receivePacket, receivePacket.length);
                socket.receive(packet);
                numbers = Integer.parseInt(objectMapper.readValue(receivePacket, String.class));
                receivePacket = new byte[1000];
                for (int i = 0; i < numbers; i++) {
                    packet = new DatagramPacket(receivePacket, receivePacket.length);
                    socket.receive(packet);
                    data.addTrigorath(objectMapper.readValue(receivePacket, Trigorath.class));
                }
                receivePacket = new byte[1000];
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            try {
                packet = new DatagramPacket(receivePacket, receivePacket.length);
                socket.receive(packet);
                numbers = Integer.parseInt(objectMapper.readValue(receivePacket, String.class));
                receivePacket = new byte[1000];
                for (int i = 0; i < numbers; i++) {
                    packet = new DatagramPacket(receivePacket, receivePacket.length);
                    socket.receive(packet);
                    data.addOmenoct(objectMapper.readValue(receivePacket, Omenoct.class));
                }
                receivePacket = new byte[1000];
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            try {
                packet = new DatagramPacket(receivePacket, receivePacket.length);
                socket.receive(packet);
                numbers = Integer.parseInt(objectMapper.readValue(receivePacket, String.class));
                receivePacket = new byte[1000];
                for (int i = 0; i < numbers; i++) {
                    packet = new DatagramPacket(receivePacket, receivePacket.length);
                    socket.receive(packet);
                    data.addNecropick(objectMapper.readValue(receivePacket, Necropick.class));
                }
                receivePacket = new byte[1000];
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            try {
                packet = new DatagramPacket(receivePacket, receivePacket.length);
                socket.receive(packet);
                numbers = Integer.parseInt(objectMapper.readValue(receivePacket, String.class));
                receivePacket = new byte[1000];
                for (int i = 0; i < numbers; i++) {
                    packet = new DatagramPacket(receivePacket, receivePacket.length);
                    socket.receive(packet);
                    data.addArchmire(objectMapper.readValue(receivePacket, Archmire.class));
                }
                receivePacket = new byte[1000];
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            try {
                packet = new DatagramPacket(receivePacket, receivePacket.length);
                socket.receive(packet);
                numbers = Integer.parseInt(objectMapper.readValue(receivePacket, String.class));
                receivePacket = new byte[1000];
                for (int i = 0; i < numbers; i++) {
                    packet = new DatagramPacket(receivePacket, receivePacket.length);
                    socket.receive(packet);
                    data.addWyrm(objectMapper.readValue(receivePacket, Wyrm.class));
                }
                receivePacket = new byte[1000];
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            try {
                packet = new DatagramPacket(receivePacket, receivePacket.length);
                socket.receive(packet);
                numbers = Integer.parseInt(objectMapper.readValue(receivePacket, String.class));
                receivePacket = new byte[1000];
                for (int i = 0; i < numbers; i++) {
                    packet = new DatagramPacket(receivePacket, receivePacket.length);
                    socket.receive(packet);
                    data.addBarricados(objectMapper.readValue(receivePacket, Barricados.class));
                }
                receivePacket = new byte[1000];
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            try {
                packet = new DatagramPacket(receivePacket, receivePacket.length);
                socket.receive(packet);
                numbers = Integer.parseInt(objectMapper.readValue(receivePacket, String.class));
                receivePacket = new byte[1000];
                for (int i = 0; i < numbers; i++) {
                    packet = new DatagramPacket(receivePacket, receivePacket.length);
                    socket.receive(packet);
                    data.addBlackOrb(objectMapper.readValue(receivePacket, BlackOrb.class));
                }
                receivePacket = new byte[1000];
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            try {
                packet = new DatagramPacket(receivePacket, receivePacket.length);
                socket.receive(packet);
                numbers = Integer.parseInt(objectMapper.readValue(receivePacket, String.class));
                receivePacket = new byte[1000];
                for (int i = 0; i < numbers; i++) {
                    packet = new DatagramPacket(receivePacket, receivePacket.length);
                    socket.receive(packet);
                    data.addCollectible(objectMapper.readValue(receivePacket, Collectible.class));
                }
                receivePacket = new byte[1000];
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void waiting(int delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void stopRunning() {
        running = false;
    }
}
