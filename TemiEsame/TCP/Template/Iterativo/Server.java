import java.net.InetSocketAddress;
import java.io.OutputStream;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.io.IOException;
import java.util.Scanner;
import java.net.ServerSocket;

public class Server {
  private ServerSocket server;
  private Socket client;
  final private int DIM_BUFF = 255;

  public Server(int port) throws IOException {
    server = new ServerSocket(port);
    printServerInfos();
  }
  public Server() throws IOException {
    this(0);
  }

  public void printServerInfos() {
    System.out.println("Server address: " + server.getInetAddress() + "; port: " + server.getLocalPort());
  }
  public void printClientInfos() {
    System.out.println("Client address: " + client.getInetAddress() + "; port: " + client.getPort());
  }

  public void accept() throws IOException {
    client = server.accept();
    printClientInfos();
  }
  public void closeClient() throws IOException {
    client.close();
    System.out.println("Client disconnesso");
  }
  public String listen() throws IOException {
    InputStream fromCl = client.getInputStream();
    byte[] buffer = new byte[DIM_BUFF];
    int letti = fromCl.read(buffer);

    if (letti < 0) {
      return "Disconnected";
    }

    return new String(buffer, 0, letti);
  }
  public void sendMessage(String message) throws IOException {
    OutputStream toCli = client.getOutputStream();
    toCli.write(message.getBytes(), 0, message.length());
  }
  public void sendMessage() throws IOException {
    Scanner in = new Scanner(System.in);
    String message = in.nextLine();
    in.close();

    sendMessage(message);
  }

  public static void main(String[] args) {
    try {
      Server server = new Server();
      
      while (true) {
        server.accept();

        String message = server.listen();

        System.out.println("Messaggio ricevuto " + message);
        server.sendMessage("200");

        server.closeClient();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}