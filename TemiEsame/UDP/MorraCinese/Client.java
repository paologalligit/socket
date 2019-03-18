import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.io.IOException;

public class Client {
  static final private int DIM_BUFFER = 100;
  static final private int[][] MATRIX = {
    {0, -1, 1}, {1, 0, -1}, {-1, 1, 0}
  };
  static private int score = 0;

  private static String sendMessage(DatagramSocket client, InetSocketAddress isa) throws IOException {
    InputStreamReader tastiera = new InputStreamReader(System.in);
    BufferedReader br = new BufferedReader(tastiera);

    String outputMessage = br.readLine();
    byte[] outputBuffer = outputMessage.getBytes();

    DatagramPacket outputPacket = new DatagramPacket(outputBuffer, outputBuffer.length);
    outputPacket.setSocketAddress(isa);
    client.send(outputPacket);

    return outputMessage;
  }

  private static void sendMessage(DatagramSocket client, InetSocketAddress isa, int message) throws IOException {
    String outputMessage = message < 0 ? "y" : "i";

    byte[] outputBuffer = outputMessage.getBytes();

    DatagramPacket outputPacket = new DatagramPacket(outputBuffer, outputBuffer.length);
    outputPacket.setSocketAddress(isa);
    client.send(outputPacket);
  }

  private static String listen(DatagramSocket client, byte[] inputBuffer) throws  IOException {
    DatagramPacket packetReceived = new DatagramPacket(inputBuffer, DIM_BUFFER);
    client.receive(packetReceived);

    return new String(inputBuffer, 0, packetReceived.getLength());
  }

  private static int mapCharToInt(char mossa) {
    switch (mossa) {
      case 'f': return 0;
      case 's': return 1;
      case 'c': return 2;
      default: throw new IllegalArgumentException(mossa + " is not a correct move");
    }
  }

  private static int gameResult(String miaMossa, String mossaServer) {
    int miaInt, serverInt;
    miaInt = mapCharToInt(miaMossa.charAt(0));
    serverInt = mapCharToInt(mossaServer.charAt(0));

    int result = MATRIX[miaInt][serverInt];

    if (result > 0) {
      score++;
      System.out.println("Hai vinto!");
    } else if (result < 0) {
      score--;
      System.out.println("Ha vinto il server :(");
    } else {
      System.out.println("Parità");
    }

    return result;
  }

  public static void main(String[] args) {
    DatagramSocket client;

    try {
      client = new DatagramSocket();
      System.out.println("Indirizzo: " + client.getLocalAddress() + "; porta: " + client.getLocalPort());

      String nomeHost = "localhost";
      int porta = 0;

      if (args.length != 2) {
        client.close();
        throw new IllegalArgumentException("Errore: almeno due argomenti");
      }

      nomeHost = args[0];
      porta = Integer.parseInt(args[1]);

      if (porta <= 0) {
        client.close();
        throw new IllegalArgumentException("Errore: porta non valida");
      }

      InetSocketAddress isa = new InetSocketAddress(nomeHost, porta);
      
      byte[] inputBuffer = new byte[DIM_BUFFER];

      sendMessage(client, isa);
      
      String stringa = listen(client, inputBuffer);

      if (stringa.equals("k")) {
        System.out.println("Ricevuto ok dal server");
        System.out.print("Inserisci mossa: ");

        String miaMossa = sendMessage(client, isa);

        String mossaServer = listen(client, inputBuffer);

        int result = gameResult(miaMossa, mossaServer);

        sendMessage(client, isa, result);
      } else {
        System.out.println("Ricevuto dal server: " + stringa);
      }
        
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}