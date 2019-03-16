import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class UDPClient {
  public static void main(String[] args) {
    DatagramSocket client = null;

    try {
      String nomeHost = "localhost";
      int porta = 7000;

      if (args.length != 2) {
        throw new IllegalArgumentException("Numero parametri non corretto");
      }

      nomeHost = args[0];
      porta = Integer.parseInt(args[1]);

      if (porta <= 0) {
        throw new IllegalArgumentException("Porta non valida");
      }

      client = new DatagramSocket(porta);
      Util.print(true, "Indirizzo: " + client.getLocalAddress() + "; porta: " + client.getLocalPort());

      InetSocketAddress isa = new InetSocketAddress(nomeHost, porta);
      InputStreamReader tastiera = new InputStreamReader(System.in);

      BufferedReader br = new BufferedReader(tastiera);
      String frase = br.readLine();
      byte[] buffer = frase.getBytes();
      DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
      dp.setSocketAddress(isa);
      client.send(dp);

    } catch (SocketException s) {
      s.printStackTrace();
    } catch (IOException i) {
      i.printStackTrace();
    }
  }
}