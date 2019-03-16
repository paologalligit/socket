import java.io.InputStream;
import java.net.Socket;

public class ErogaServizio extends Thread {
  private Socket socket;
  final private int DIM_BUFFER = 100;

  public ErogaServizio(Socket s) {
    socket = s;
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
          return;
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}