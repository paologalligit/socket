import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.InputStream;

public class Server {
    public static void print(Boolean flag, Object... s) {
        for (Object o : s) {
            System.out.print(o);
        }

        if (flag)
            System.out.println();
        else
            System.out.print("");
    }
    public static void main(String[] args) {
        Socket client;
        ServerSocket server;

        try {
            server = new ServerSocket(0);  
            print(true, "Indirizzo server: " + server.getInetAddress()  
                + ", porta: " + server.getLocalPort());

            int dimBuffer = 100;
            byte[] buffer = new byte[dimBuffer];

            client = server.accept();
            print(true, "Indirizzo client: " + client.getInetAddress()  
                + ", porta: " + client.getPort());

            InputStream fromCl = client.getInputStream();
            int letti = fromCl.read(buffer);
            String stampa = new String(buffer, 0, letti);
            print(true, "Ricevuta stringa: " + stampa + " di " + letti + " bytes");

            Thread.sleep(1 * 1_000);
        } catch (IOException i) {
            i.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}