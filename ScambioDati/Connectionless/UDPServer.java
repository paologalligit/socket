import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.io.IOException;

public class UDPServer {
  static final int DIM_BUFFER = 100;

  public static void main(String[] args) {
    DatagramSocket server = null;

    try {
      server = new DatagramSocket();
      Util.print(true, "Indirizzo: " + server.getLocalAddress() + "; porta: " + server.getLocalPort());
      
      byte[] buffer = new byte[DIM_BUFFER];
      DatagramPacket dpin = new DatagramPacket(buffer, DIM_BUFFER);
      
      server.receive(dpin);
      
      String stringa = new String(buffer, 0, dpin.getLength());
      Util.print(true, "ricevuto: " + stringa);

      InetAddress ia = dpin.getAddress();
      int porta = dpin.getPort();
      Util.print(true, "Indirizzo: " + ia.getHostAddress() + "; porta: " + porta);

      server.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}