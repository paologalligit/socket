import java.net.ServerSocket;
import java.net.Socket;

public class Server {
  public static void main(String[] args) {
    ServerSocket server;
    Socket client;

    try {
      server = new ServerSocket(0);
      System.out.println("Address: " + server.getInetAddress() + "; port: " + server.getLocalPort());

      while (true) {
        client = server.accept();
        System.out.println("Client address: " + client.getInetAddress() + "; porta: " + client.getPort());

        Thread t = new ErogaServizio(client);
        t.start();
      }
    } catch (Exception i) {
      i.printStackTrace();
    }
  }
}