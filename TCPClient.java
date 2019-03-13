import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.Socket;
import java.net.InetSocketAddress;
import java.util.Arrays;

public class TCPClient {
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
        InetAddress ia;
        InetSocketAddress isa;

        client = new Socket();
        try {
            ia = InetAddress.getLocalHost();
            isa = new InetSocketAddress(ia, Integer.parseInt(args[0]));

            client.connect(isa);

            // client.bind(isa);
            print(true, "Porta allocata: " + client.getLocalPort());
            print(true, "Indirizzo: " + client.getInetAddress()
                + "; porta: " + client.getPort());

            Thread.sleep(10 * 1_000);

            client.close();
        } catch (UnknownHostException u) {
            u.printStackTrace();
        } catch (IOException i) {
            i.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}