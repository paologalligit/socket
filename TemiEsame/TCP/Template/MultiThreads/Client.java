import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
  private Socket client;
  private InetSocketAddress isa;
  final private int DIM_BUF = 255;

  public Client(int port) throws IOException {
    client = new Socket();
    InetAddress ia = InetAddress.getLocalHost();
    isa = new InetSocketAddress(ia, port);
  }

  public void connect() throws IOException {
    client.connect(isa);
  }
  public void printClientInfos() {
    System.out.println("Client address: " + client.getInetAddress() + "; port: " + client.getLocalPort());
  }
  public String listen() throws IOException {
    InputStream fromSrv = client.getInputStream();
    byte[] buffer = new byte[DIM_BUF];
    int letti = fromSrv.read(buffer);
    
    return new String(buffer, 0, letti);
  }
  public void sendMessage(String message) throws IOException {
    OutputStream toSrv = client.getOutputStream();
    toSrv.write(message.getBytes(), 0, message.length());
  }
  public void sendMessage() throws IOException {
    Scanner in = new Scanner(System.in);
    String message = in.nextLine();
    in.close();

    sendMessage(message);
  }
  public static void main(String[] args) {
    
  }
}