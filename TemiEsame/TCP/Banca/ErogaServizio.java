import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;

public class ErogaServizio extends Thread {
  private Socket client;
  private HashMap<String, Double> table;

  public ErogaServizio(Socket client, HashMap<String, Double> table) {
    this.client = client;
    this.table = table;
  }

  public static String listen(Socket client) throws IOException {
    InputStream fromCli = client.getInputStream();
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
    String message = listen(client);

    Double amount = table.getOrDefault(message, null);
    if (amount == null) {
      sendMessage("Denied");
      client.close();
    }
  }
}