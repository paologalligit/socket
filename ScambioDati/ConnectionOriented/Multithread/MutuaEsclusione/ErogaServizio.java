import java.io.InputStream;
import java.net.Socket;

public class ErogaServizio extends Thread {
  private Socket socket;
  private ContaStringhe lavoro;
  private int id;
  final private int DIM_BUFFER = 100;

  public ErogaServizio(Socket s, int numThread, ContaStringhe c) {
    socket = s;
    lavoro = c;
    id = numThread;
  }

  public void run() {
    byte[] buffer = new byte[DIM_BUFFER];

    while (true) {
      try {
        InputStream client = socket.getInputStream();
        int letti = client.read(buffer);
        if (letti > 0) {
          String stampa = new String(buffer, 0, letti);
          System.out.println("Ricevuta stringa: " + stampa + " di " + letti + " bytes  da " + socket.getInetAddress()
            + "; port: " + socket.getPort());
        } else {
          socket.close();
          System.out.println("----------------Return nel catch-------------------");
          return;
        }
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        try {
          System.out.println("Thread: " + id + " termina; scambiate " + lavoro.getTotale());
          System.out.println("Nuovo totale: " + lavoro.incrementa(1));
        } catch (Exception e) {
          System.err.println("Server thread error");
          e.printStackTrace();
        }
      }
    }
  }
}