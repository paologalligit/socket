import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ErogaServizio extends Thread {
  private Socket client;
  final static private int DIM_BUF = 255;

  public ErogaServizio(Socket client) {
    this.client = client;
  }

  public static String listen(Socket client) throws IOException {
    byte[] buf = new byte[DIM_BUF];
    InputStream fromCli = client.getInputStream();
    int letti = fromCli.read(buf);
    if (letti < 0) {
      return "Disconnesso";
    }

    return new String(buf, 0, letti);
  }
  public static void sendMessage(String message, Socket client) throws IOException {
    OutputStream toCli = client.getOutputStream();
    toCli.write(message.getBytes(), 0, message.length());
  }

  public void run() {
    try {
      String message = listen(client);
      System.out.println("Messaggio ricevuto da " + client.getPort() + ": " + message);

      sendMessage("200", client);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}