package conection;

import com.fasterxml.jackson.databind.ObjectMapper;
import database.EntityData;
import model.game.Collectible;
import model.game.Shot;
import model.game.enemies.miniBoss.Barricados;
import model.game.enemies.miniBoss.BlackOrb;
import model.game.enemies.normal.*;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class SendData extends Thread {
    private final DatagramSocket socket;
    private final int port;
    boolean running;
    private final EntityData data;

    public SendData(EntityData data, int port) {
        this.data = data;
        this.port = port;
        try {
            socket = new DatagramSocket();
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
        byte[] sendPacket;
        while (running) {
            waiting(100);
            try {
                DatagramPacket packet;
                try {
                    sendPacket = objectMapper.writeValueAsBytes(data.getEpsilon());
                    packet = new DatagramPacket(sendPacket, sendPacket.length, InetAddress.getLocalHost(), port);
                    socket.send(packet);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


                try {
                    sendPacket = objectMapper.writeValueAsBytes(data.getShots().size() + "");
                    packet = new DatagramPacket(sendPacket, sendPacket.length, InetAddress.getLocalHost(), port);
                    socket.send(packet);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                ArrayList<Shot> shots = data.getShots();
                for (int i = 0; i < shots.size(); i++) {
                    Shot shot = shots.get(i);
                    try {
                        sendPacket = objectMapper.writeValueAsBytes(shot);
                        packet = new DatagramPacket(sendPacket, sendPacket.length, InetAddress.getLocalHost(), port);
                        socket.send(packet);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }


                try {
                    sendPacket = objectMapper.writeValueAsBytes(data.getSquarantines().size() + "");
                    packet = new DatagramPacket(sendPacket, sendPacket.length, InetAddress.getLocalHost(), port);
                    socket.send(packet);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                ArrayList<Squarantine> squarantines = data.getSquarantines();
                for (int i = 0; i < squarantines.size(); i++) {
                    Squarantine squarantine = squarantines.get(i);
                    try {
                        sendPacket = objectMapper.writeValueAsBytes(squarantine);
                        packet = new DatagramPacket(sendPacket, sendPacket.length, InetAddress.getLocalHost(), port);
                        socket.send(packet);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }


                try {
                    sendPacket = objectMapper.writeValueAsBytes(data.getTrigoraths().size() + "");
                    packet = new DatagramPacket(sendPacket, sendPacket.length, InetAddress.getLocalHost(), port);
                    socket.send(packet);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                ArrayList<Trigorath> trigoraths = data.getTrigoraths();
                for (int i = 0; i < trigoraths.size(); i++) {
                    Trigorath trigorath = trigoraths.get(i);
                    try {
                        sendPacket = objectMapper.writeValueAsBytes(trigorath);
                        packet = new DatagramPacket(sendPacket, sendPacket.length, InetAddress.getLocalHost(), port);
                        socket.send(packet);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }


                try {
                    sendPacket = objectMapper.writeValueAsBytes(data.getOmenocts().size() + "");
                    packet = new DatagramPacket(sendPacket, sendPacket.length, InetAddress.getLocalHost(), port);
                    socket.send(packet);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                ArrayList<Omenoct> omenocts = data.getOmenocts();
                for (int i = 0; i < omenocts.size(); i++) {
                    Omenoct omenoct = omenocts.get(i);
                    try {
                        sendPacket = objectMapper.writeValueAsBytes(omenoct);
                        packet = new DatagramPacket(sendPacket, sendPacket.length, InetAddress.getLocalHost(), port);
                        socket.send(packet);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }


                try {
                    sendPacket = objectMapper.writeValueAsBytes(data.getNecropicks().size() + "");
                    packet = new DatagramPacket(sendPacket, sendPacket.length, InetAddress.getLocalHost(), port);
                    socket.send(packet);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                ArrayList<Necropick> necropicks = data.getNecropicks();
                for (int i = 0; i < necropicks.size(); i++) {
                    Necropick necropick = necropicks.get(i);
                    try {
                        sendPacket = objectMapper.writeValueAsBytes(necropick);
                        packet = new DatagramPacket(sendPacket, sendPacket.length, InetAddress.getLocalHost(), port);
                        socket.send(packet);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }


                try {
                    sendPacket = objectMapper.writeValueAsBytes(data.getArchmires().size() + "");
                    packet = new DatagramPacket(sendPacket, sendPacket.length, InetAddress.getLocalHost(), port);
                    socket.send(packet);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                ArrayList<Archmire> archmires = data.getArchmires();
                for (int i = 0; i < archmires.size(); i++) {
                    Archmire archmire = archmires.get(i);
                    try {
                        sendPacket = objectMapper.writeValueAsBytes(archmire);
                        packet = new DatagramPacket(sendPacket, sendPacket.length, InetAddress.getLocalHost(), port);
                        socket.send(packet);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }


                try {
                    sendPacket = objectMapper.writeValueAsBytes(data.getWyrms().size() + "");
                    packet = new DatagramPacket(sendPacket, sendPacket.length, InetAddress.getLocalHost(), port);
                    socket.send(packet);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                ArrayList<Wyrm> wyrms = data.getWyrms();
                for (int i = 0; i < wyrms.size(); i++) {
                    Wyrm wyrm = wyrms.get(i);
                    try {
                        sendPacket = objectMapper.writeValueAsBytes(wyrm);
                        packet = new DatagramPacket(sendPacket, sendPacket.length, InetAddress.getLocalHost(), port);
                        socket.send(packet);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }


                try {
                    sendPacket = objectMapper.writeValueAsBytes(data.getBarricadoses().size() + "");
                    packet = new DatagramPacket(sendPacket, sendPacket.length, InetAddress.getLocalHost(), port);
                    socket.send(packet);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                ArrayList<Barricados> barricadoses = data.getBarricadoses();
                for (int i = 0; i < barricadoses.size(); i++) {
                    Barricados barricados = barricadoses.get(i);
                    try {
                        sendPacket = objectMapper.writeValueAsBytes(barricados);
                        packet = new DatagramPacket(sendPacket, sendPacket.length, InetAddress.getLocalHost(), port);
                        socket.send(packet);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }


                try {
                    sendPacket = objectMapper.writeValueAsBytes(data.getBlackOrbs().size() + "");
                    packet = new DatagramPacket(sendPacket, sendPacket.length, InetAddress.getLocalHost(), port);
                    socket.send(packet);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                ArrayList<BlackOrb> blackOrbs = data.getBlackOrbs();
                for (int i = 0; i < blackOrbs.size(); i++) {
                    BlackOrb blackOrb = blackOrbs.get(i);
                    try {
                        sendPacket = objectMapper.writeValueAsBytes(blackOrb);
                        packet = new DatagramPacket(sendPacket, sendPacket.length, InetAddress.getLocalHost(), port);
                        socket.send(packet);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }


                try {
                    sendPacket = objectMapper.writeValueAsBytes(data.getCollectibles().size() + "");
                    packet = new DatagramPacket(sendPacket, sendPacket.length, InetAddress.getLocalHost(), port);
                    socket.send(packet);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                ArrayList<Collectible> collectibles = data.getCollectibles();
                for (int i = 0; i < collectibles.size(); i++) {
                    Collectible collectible = collectibles.get(i);
                    try {
                        sendPacket = objectMapper.writeValueAsBytes(collectible);
                        packet = new DatagramPacket(sendPacket, sendPacket.length, InetAddress.getLocalHost(), port);
                        socket.send(packet);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            } catch (ConcurrentModificationException e) {
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
