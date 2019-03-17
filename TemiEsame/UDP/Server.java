import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Random;

public class Server {
  static final private int DIM_BUFFER = 100;
  static final private String[] MOVE_SET = new String[] {"s", "c", "f"};
  static private int partite;
  static private int vinte;

  private static void sendMessage(DatagramSocket server, String address, int port, String message) throws IOException {
    byte[] outputBuffer = new byte[DIM_BUFFER];

    outputBuffer = message.getBytes();
    
    DatagramPacket packetSent = new DatagramPacket(outputBuffer, outputBuffer.length);

    InetSocketAddress isa = new InetSocketAddress(address, port);
    packetSent.setSocketAddress(isa);
    server.send(packetSent);
  }

  private static String listen(DatagramSocket server) throws  IOException {
    byte[] inputBuffer = new byte[DIM_BUFFER];

    DatagramPacket packetReceived = new DatagramPacket(inputBuffer, DIM_BUFFER);
    server.receive(packetReceived);

    return new String(inputBuffer, 0, packetReceived.getLength());
  }

  private static String makeMove() {
    Random r = new Random();

    String move = MOVE_SET[r.nextInt(3)];
    System.out.println("Server fa: " + move);
    
    return move;
  }

  public static void main(String[] args) {
    DatagramSocket server;

    try {
      server = new DatagramSocket();
      System.out.println("Indirizzo: " + server.getLocalAddress() + "; porta: " + server.getLocalPort());

      byte[] inputBuffer = new byte[DIM_BUFFER];

      DatagramPacket packetReceived = new DatagramPacket(inputBuffer, DIM_BUFFER);
      String inputMessage;

      while (true) {
        // connessione con un client
        server.receive(packetReceived);

        inputMessage = new String(inputBuffer, 0, packetReceived.getLength());

        // identificazione client
        InetAddress ia = packetReceived.getAddress();
        int port = packetReceived.getPort();
        System.out.println("Indirizzo: " + ia.getHostAddress() + "; porta: " + port);

        if (inputMessage.equals("p")) {
          // inizio partita
          System.out.println("Ricevuto: " + inputMessage + "; si comincia la partita!");

          // incrementa numero partite
          partite++;

          // invio messaggio ok
          sendMessage(server, ia.getHostAddress(), port, "k");

          // ricevi mossa
          String mossa = listen(server);
          System.out.println("Client fa: " + mossa);

          // elabora mossa
          sendMessage(server, ia.getHostAddress(), port, makeMove());

          // ricezione risultato
          String result = listen(server);
          if (result.equals("y")) {
            vinte++;
          }

          System.out.println("Partite giocate dal server: " + partite + "; partite vinte: " + vinte);
        } else {
          sendMessage(server, ia.getHostAddress(), port, "Messaggio non valido");
        }
      }

    } catch (IOException i) {
      i.printStackTrace();
    }
  }
}