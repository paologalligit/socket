import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class Client {
  private DatagramSocket client;
  private InetSocketAddress isa;
  final private int DIM_BUFFER = 255;

  public Client (String hostName, int port) throws SocketException {
    client = new DatagramSocket();
    isa = new InetSocketAddress(hostName, port);
  }
  public Client(int port) throws SocketException {
    this("localhost", port);
  }

  public String listen() throws IOException {
    byte[] buf = new byte[DIM_BUFFER];
    DatagramPacket packet = new DatagramPacket(buf, buf.length);
    client.receive(packet);

    return new String(buf, 0, packet.getLength());
  }
  public void sendMessage(String message) throws IOException {
    byte[] buf = new byte[DIM_BUFFER];
    DatagramPacket packet = new DatagramPacket(buf, buf.length);
    packet.setSocketAddress(isa);
    client.send(packet);
  }
  public void sendMessage() throws IOException {
    InputStreamReader tastiera = new InputStreamReader(System.in);
    BufferedReader br = new BufferedReader(tastiera);

    String message = br.readLine();
    sendMessage(message);
  }
  public static void main(String[] args) {
    
  }
}