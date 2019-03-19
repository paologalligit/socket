import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {
  private ServerSocket server;
  private Socket client;
  private HashMap<String, Double> table;
  final private int DIM_BUF = 255;

  public Server(int port) throws IOException {
    server = new ServerSocket(port);
    table = new HashMap<>();
    table.put("Rossi", 1000.0);
    table.put("Bianchi", 1000.0);
    table.put("Verdi", 1000.0);

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
  public void fork() throws IOException {
    Thread t = new ErogaServizio(client);
    t.start();
  }

  public static void main(String[] args) {
    try {
      Server server = new Server();

      while (true) {
        server.accept();
        server.fork();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}