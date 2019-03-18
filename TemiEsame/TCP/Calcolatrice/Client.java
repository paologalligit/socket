import java.net.InetSocketAddress;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.io.IOException;

public class Client {
  // Attributi
  private Socket client;
  private InetAddress ia;
  private InetSocketAddress isa;
  private byte[] buffer;
  final private int DIM_BUFFER = 255;

  // Costruttori
  public Client(int port) throws IOException {
    client = new Socket();
    ia = InetAddress.getLocalHost();
    isa = new InetSocketAddress(ia, port);
    buffer = new byte[DIM_BUFFER];
  }

  // Metodi
  public void printClientInfos() {
    System.out.println("Indirizzo: " + client.getInetAddress() + "; porta: " + client.getLocalPort());
  }
  public void connect() throws IOException {
    client.connect(isa);
  }
  public String sendMessage() throws IOException {
    InputStreamReader tastiera = new InputStreamReader(System.in);
    BufferedReader br = new BufferedReader(tastiera);

    String frase = br.readLine();

    OutputStream toSrv = client.getOutputStream();
    toSrv.write(frase.getBytes(), 0, frase.length());

    return frase;
  }
  public String listen() throws IOException {
    InputStream fromSrv = client.getInputStream();
    int letti = fromSrv.read(buffer);
    if (letti < 0) {
      return "Server disconnesso";
    }

    return new String(buffer, 0, letti);
  }

  // Main
  public static void main(String[] args) {
    try {
      String response;

      do {
        int port = Integer.parseInt(args[0]);
        Client user = new Client(port);

        user.connect();
        user.printClientInfos();

        do {
          System.out.print("Inserisci simbolo: ");
          user.sendMessage();

          response = user.listen();
        } while (response.equals("x"));

        // operando 2
        do {
          // operando 1
          do {
            System.out.print("Inserisci operando 1: ");
            user.sendMessage();

            response = user.listen();
          } while (response.equals("x"));
          
          System.out.print("Inserisci operando 2: ");
          user.sendMessage();

          response = user.listen();
        } while (response.equals("x"));

        response = user.listen();
        System.out.println("Risultato operazione: " + response);

        System.out.print("Inserisci . per terminare o un simbolo per operazioni: ");
        response = user.sendMessage();

      } while (!response.equals("."));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}