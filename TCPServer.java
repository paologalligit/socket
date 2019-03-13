import java.io.IOException;
// import java.net.InetAddress;
// import java.net.UnknownHostException;
import java.net.Socket;
import java.net.ServerSocket;
// import java.net.InetSocketAddress;
import java.util.Arrays;

public class TCPServer {
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

            client = server.accept();
            print(true, "Indirizzo client: " + client.getInetAddress()  
                + ", porta: " + client.getPort());

            Thread.sleep(10 * 1_000);

            client.close();
            server.close();
        } catch (IOException i) {
            i.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}