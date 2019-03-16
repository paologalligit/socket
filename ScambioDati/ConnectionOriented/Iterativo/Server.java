import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
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
        Socket client = null;
        ServerSocket server = null;

        try {
            server = new ServerSocket(0);
            print(true, "Indirizzo server: " + server.getInetAddress() + ", porta: " + server.getLocalPort());

            while (true) {
                int dimBuffer = 100;
                byte[] buffer = new byte[dimBuffer];

                String stampa = null;
                
                client = server.accept();
                print(true, "Indirizzo client: " + client.getInetAddress() + ", porta: " + client.getPort());
                
                do {
                    InputStream fromCl = client.getInputStream();
                    int letti = fromCl.read(buffer);
                    if (letti < 0) {
                        break;
                    }
                    stampa = new String(buffer, 0, letti);
                    print(true, "Ricevuta stringa: " + stampa + " di " + letti + " bytes");
                } while (!stampa.equals("0"));

                print(true, "Il client si è disconnesso, la pagani è contenta. Indirizzo client " 
                    + client.getInetAddress() + " porta: " + client.getPort());

		client.close();
            }
        } catch (Exception i) {
            i.printStackTrace();
        }
    }
}