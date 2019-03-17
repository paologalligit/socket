import java.net.ServerSocket;
import java.net.Socket;

public class Server {
  public static void main(String[] args) {
    ServerSocket server;
    Socket client;
    ContaStringhe lavoro;

    try {
      server = new ServerSocket(0);
      lavoro = new ContaStringhe();

      System.out.println("Address: " + server.getInetAddress() + "; port: " + server.getLocalPort());

      while (true) {
        client = server.accept();
        System.out.println("Client address: " + client.getInetAddress() + "; porta: " + client.getPort());

        Thread t = new ErogaServizio(client, client.getPort(), lavoro);
        t.start();
      }
    } catch (Exception i) {
      i.printStackTrace();
    }
  }
}