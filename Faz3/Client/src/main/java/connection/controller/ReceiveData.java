package connection.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import connection.database.EntityData2;
import connection.database.model.Collectible2;
import connection.database.model.Epsilon2;
import connection.database.model.Shot2;
import connection.database.model.enemies.miniBoss.Barricados2;
import connection.database.model.enemies.miniBoss.BlackOrb2;
import connection.database.model.enemies.normal.*;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class ReceiveData extends Thread {
    private DatagramSocket socket;
    private DatagramPacket packet;
    private int port;
    boolean running;

    public ReceiveData(int port) {
        this.port = port;
        try {
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        Runtime.getRuntime().addShutdownHook(new Thread(() -> socket.close()));
    }

    @Override
    public void run() {
        super.run();
        running = true;
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] receivePacket = new byte[1000];

        while (running) {
            waiting(100);
            EntityData2.clear();
            int numbers;
            try {
                packet = new DatagramPacket(receivePacket, receivePacket.length);
                socket.receive(packet);
                EntityData2.setEpsilon(objectMapper.readValue(receivePacket, Epsilon2.class));
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
                    EntityData2.addShot(objectMapper.readValue(receivePacket, Shot2.class));
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
                    EntityData2.addSquarantine(objectMapper.readValue(receivePacket, Squarantine2.class));
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
                    EntityData2.addTrigorath(objectMapper.readValue(receivePacket, Trigorath2.class));
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
                    EntityData2.addOmenoct(objectMapper.readValue(receivePacket, Omenoct2.class));
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
                    EntityData2.addNecropick(objectMapper.readValue(receivePacket, Necropick2.class));
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
                    EntityData2.addArchmire(objectMapper.readValue(receivePacket, Archmire2.class));
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
                    EntityData2.addWyrm(objectMapper.readValue(receivePacket, Wyrm2.class));
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
                    EntityData2.addBarricados(objectMapper.readValue(receivePacket, Barricados2.class));
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
                    EntityData2.addBlackOrb(objectMapper.readValue(receivePacket, BlackOrb2.class));
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
                    EntityData2.addCollectible(objectMapper.readValue(receivePacket, Collectible2.class));
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
