package connection.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import connection.database.model.Collectible2;
import connection.database.model.Epsilon2;
import connection.database.model.Shot2;
import connection.database.model.enemies.miniBoss.Barricados2;
import connection.database.model.enemies.miniBoss.BlackOrb2;
import connection.database.model.enemies.normal.*;
import controller.EntityData;
import model.Collectible;
import model.Epsilon;
import model.Shot;
import model.enemies.miniBoss.Barricados;
import model.enemies.miniBoss.BlackOrb;
import model.enemies.normal.*;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

public class SendData extends Thread {
    private DatagramSocket socket;
    private DatagramPacket packet;
    private int port;
    boolean running;

    public SendData(int port) {
        this.port = port;

        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            socket.close();
        }));
    }

    @Override
    public void run() {
        super.run();
        running = true;
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] sendPacket;
        while (running) {
            waiting(100);

            try {
                sendPacket = objectMapper.writeValueAsBytes(new Epsilon2(Epsilon.getInstance().getX(), Epsilon.getInstance().getY()));
                packet = new DatagramPacket(sendPacket, sendPacket.length, InetAddress.getLocalHost(), port);
                socket.send(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            try {
                sendPacket = objectMapper.writeValueAsBytes(EntityData.getShots().size() + "");
                packet = new DatagramPacket(sendPacket, sendPacket.length, InetAddress.getLocalHost(), port);
                socket.send(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ArrayList<Shot> shots = EntityData.getShots();
            for (int i = 0; i < shots.size(); i++) {
                Shot shot = shots.get(i);
                try {
                    sendPacket = objectMapper.writeValueAsBytes(
                            new Shot2(shot.getX(),
                                    shot.getY(),
                                    shot.getDamage(),
                                    shot.getKind2(),
                                    false));
                    packet = new DatagramPacket(sendPacket, sendPacket.length, InetAddress.getLocalHost(), port);
                    socket.send(packet);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }


            try {
                sendPacket = objectMapper.writeValueAsBytes(EntityData.getSquarantines().size() + "");
                packet = new DatagramPacket(sendPacket, sendPacket.length, InetAddress.getLocalHost(), port);
                socket.send(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ArrayList<Squarantine> squarantines = EntityData.getSquarantines();
            for (int i = 0; i < squarantines.size(); i++) {
                Squarantine squarantine = squarantines.get(i);
                try {
                    sendPacket = objectMapper.writeValueAsBytes(new Squarantine2(squarantine.getX(), squarantine.getY()));
                    packet = new DatagramPacket(sendPacket, sendPacket.length, InetAddress.getLocalHost(), port);
                    socket.send(packet);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }


            try {
                sendPacket = objectMapper.writeValueAsBytes(EntityData.getTrigoraths().size() + "");
                packet = new DatagramPacket(sendPacket, sendPacket.length, InetAddress.getLocalHost(), port);
                socket.send(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ArrayList<Trigorath> trigoraths = EntityData.getTrigoraths();
            for (int i = 0; i < trigoraths.size(); i++) {
                Trigorath trigorath = trigoraths.get(i);
                try {
                    sendPacket = objectMapper.writeValueAsBytes(new Trigorath2(trigorath.getX(), trigorath.getY()));
                    packet = new DatagramPacket(sendPacket, sendPacket.length, InetAddress.getLocalHost(), port);
                    socket.send(packet);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }


            try {
                sendPacket = objectMapper.writeValueAsBytes(EntityData.getOmenocts().size() + "");
                packet = new DatagramPacket(sendPacket, sendPacket.length, InetAddress.getLocalHost(), port);
                socket.send(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ArrayList<Omenoct> omenocts = EntityData.getOmenocts();
            for (int i = 0; i < omenocts.size(); i++) {
                Omenoct omenoct = omenocts.get(i);
                try {
                    sendPacket = objectMapper.writeValueAsBytes(new Omenoct2(omenoct.getX(), omenoct.getY()));
                    packet = new DatagramPacket(sendPacket, sendPacket.length, InetAddress.getLocalHost(), port);
                    socket.send(packet);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }


            try {
                sendPacket = objectMapper.writeValueAsBytes(EntityData.getNecropicks().size() + "");
                packet = new DatagramPacket(sendPacket, sendPacket.length, InetAddress.getLocalHost(), port);
                socket.send(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ArrayList<Necropick> necropicks = EntityData.getNecropicks();
            for (int i = 0; i < necropicks.size(); i++) {
                Necropick necropick = necropicks.get(i);
                try {
                    sendPacket = objectMapper.writeValueAsBytes(new Necropick2(necropick.getX(), necropick.getY()));
                    packet = new DatagramPacket(sendPacket, sendPacket.length, InetAddress.getLocalHost(), port);
                    socket.send(packet);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }


            try {
                sendPacket = objectMapper.writeValueAsBytes(EntityData.getArchmires().size() + "");
                packet = new DatagramPacket(sendPacket, sendPacket.length, InetAddress.getLocalHost(), port);
                socket.send(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ArrayList<Archmire> archmires = EntityData.getArchmires();
            for (int i = 0; i < archmires.size(); i++) {
                Archmire archmire = archmires.get(i);
                try {
                    sendPacket = objectMapper.writeValueAsBytes(new Archmire2(archmire.getX(), archmire.getY()));
                    packet = new DatagramPacket(sendPacket, sendPacket.length, InetAddress.getLocalHost(), port);
                    socket.send(packet);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }


            try {
                sendPacket = objectMapper.writeValueAsBytes(EntityData.getWyrms().size() + "");
                packet = new DatagramPacket(sendPacket, sendPacket.length, InetAddress.getLocalHost(), port);
                socket.send(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ArrayList<Wyrm> wyrms = EntityData.getWyrms();
            for (int i = 0; i < wyrms.size(); i++) {
                Wyrm wyrm = wyrms.get(i);
                try {
                    sendPacket = objectMapper.writeValueAsBytes(new Wyrm2(wyrm.getX(), wyrm.getY()));
                    packet = new DatagramPacket(sendPacket, sendPacket.length, InetAddress.getLocalHost(), port);
                    socket.send(packet);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }


            try {
                sendPacket = objectMapper.writeValueAsBytes(EntityData.getBarricadoses().size() + "");
                packet = new DatagramPacket(sendPacket, sendPacket.length, InetAddress.getLocalHost(), port);
                socket.send(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ArrayList<Barricados> barricadoses = EntityData.getBarricadoses();
            for (int i = 0; i < barricadoses.size(); i++) {
                Barricados barricados = barricadoses.get(i);
                try {
                    sendPacket = objectMapper.writeValueAsBytes(new Barricados2(barricados.getX(), barricados.getY()));
                    packet = new DatagramPacket(sendPacket, sendPacket.length, InetAddress.getLocalHost(), port);
                    socket.send(packet);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }


            try {
                sendPacket = objectMapper.writeValueAsBytes(EntityData.getBlackOrbs().size() + "");
                packet = new DatagramPacket(sendPacket, sendPacket.length, InetAddress.getLocalHost(), port);
                socket.send(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ArrayList<BlackOrb> blackOrbs = EntityData.getBlackOrbs();
            for (int i = 0; i < blackOrbs.size(); i++) {
                BlackOrb blackOrb = blackOrbs.get(i);
                try {
                    sendPacket = objectMapper.writeValueAsBytes(new BlackOrb2(blackOrb.getX(), blackOrb.getY()));
                    packet = new DatagramPacket(sendPacket, sendPacket.length, InetAddress.getLocalHost(), port);
                    socket.send(packet);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }


            try {
                sendPacket = objectMapper.writeValueAsBytes(EntityData.getCollectibles().size() + "");
                packet = new DatagramPacket(sendPacket, sendPacket.length, InetAddress.getLocalHost(), port);
                socket.send(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ArrayList<Collectible> collectibles = EntityData.getCollectibles();
            for (int i = 0; i < collectibles.size(); i++) {
                Collectible collectible = collectibles.get(i);
                try {
                    sendPacket = objectMapper.writeValueAsBytes(new Collectible2(collectible.getX(), collectible.getY(), collectible.getValue()));
                    packet = new DatagramPacket(sendPacket, sendPacket.length, InetAddress.getLocalHost(), port);
                    socket.send(packet);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
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
