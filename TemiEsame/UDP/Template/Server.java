import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.InetAddress;

public class Server {
  private DatagramSocket server;
  private InetSocketAddress isa;
  final private int DIM_BUFFER = 255;

  public Server() throws SocketException {
    server = new DatagramSocket();
    printServerInfos();
  }

  public void printServerInfos() {
    System.out.println("Server address: " + server.getLocalAddress() + "; port: " + server.getLocalPort());
  }
  public String listen() throws IOException {
    byte[] buffer = new byte[DIM_BUFFER];
    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
    server.receive(packet);

    buildIsa(packet);

    return new String(buffer, 0, packet.getLength());
  }
  private void buildIsa(DatagramPacket dp) throws IOException {
    InetAddress ia = dp.getAddress();
    int port = dp.getPort();
    isa = new InetSocketAddress(ia.getHostAddress(), port);
  }
  public void sendMessage(String message) throws IOException {
    byte[] buffer = message.getBytes();
    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
    packet.setSocketAddress(isa);
    server.send(packet);
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