import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

public abstract class Client {
    protected DatagramSocket client;
    protected InetSocketAddress isa;
    final private int DIM_BUFFER = 255;

    public Client(String hostName, int port) throws SocketException {
        client = new DatagramSocket();
        isa = new InetSocketAddress(hostName, port);
    }

    public void printClientInfos() throws IOException {
        System.out.println("Station address: " + client.getLocalAddress() + "; port: " + client.getLocalPort());
    }
    public String listen() throws IOException {
        // Creo pacchetto
        byte[] buffer = new byte[DIM_BUFFER];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        client.receive(packet);

        return new String(buffer, 0, packet.getLength());
    }
}