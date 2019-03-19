import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ErogaServizio extends Thread {
  private Socket client;

  public ErogaServizio(Socket client) {
    this.client = client;
  }

  public static String listen(Socket client) throws IOException {
    InputStream fromCli = clent.getInputStream();
    byte[] buffer = new byte[255];
    int letti = fromCli.read(buffer);
    if (letti < 0) {
      return "Disconnesso";
    }

    return new String(buffer, 0, letti);
  }
  public static void sendMessage(String message, Socket client) throws IOException {
    OutputStream toCli = client.getOutputStream();
    toCli.write(message.getBytes(), 0, message.length());
  }
  public static void closeClient(Socket client) throws IOException {
    client.close();
  }

  public void run() {
    
  }
}