import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.HashMap; 
import java.util.List;
import java.util.ArrayList;

public class Server {
    private DatagramSocket server;
    private InetSocketAddress isa;
    final private int DIM_BUFFER = 255;
    private HashMap<String, List<String>> table;

    public Server() throws SocketException {
        server = new DatagramSocket();
        table = new HashMap<>();
    }

    public void printServerInfos() {
        System.out.println("Server address: " + server.getLocalAddress() + "; port: " + server.getLocalPort());
    }
    public String listen() throws IOException {
        // Creo pacchetto
        byte[] buffer = new byte[DIM_BUFFER];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        
        // ricevo pacchetto
        server.receive(packet);

        // mi salvo con chi sto parlando
        InetAddress currentClientAdd = packet.getAddress();
        int currentClientPort = packet.getPort();
        isa = new InetSocketAddress(currentClientAdd.getHostAddress(), currentClientPort);

        return new String(buffer, 0, packet.getLength());
    }
    public void sendMessage(String message) throws IOException {
        byte[] buffer = message.getBytes();
        // Creo pacchetto
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        // Gli dico il destinatario
        packet.setSocketAddress(isa);
        // Lo invio
        server.send(packet);
    }
    public void manageMessage(String message) throws IOException {
        String[] infos = message.split(" ");

        if (infos.length == 2) {
            List<String> value = new ArrayList<>();
            value.add(infos[1]);
            value.add(String.valueOf(isa.getPort()));
            table.put(infos[0], value);
            sendMessage("Item inserted");
        } else {
            List<String> info = table.getOrDefault(infos[0], null);

            if (info == null) {
                System.out.println("Null");
                sendMessage("");
            } else {
                System.out.println("Messaggio con " + info.get(0) + " " + info.get(1));
                sendMessage("Delay: " + info.get(0) + "; Station: " + info.get(1));
            }
        }
    }
    
    public static void main(String[] args) {
        try {
            Server server = new Server();
            server.printServerInfos();

            while (true) {
                try {
                    String message = server.listen();

                    System.out.println("The message: " + message);

                    server.manageMessage(message);
                } catch (Exception e) {
                    System.out.println("Client disconnesso!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}