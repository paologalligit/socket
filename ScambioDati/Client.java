import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.Socket;
import java.net.InetSocketAddress;
import java.io.InputStreamReader;

public class Client {
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

            InputStreamReader tastiera = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(tastiera);

            client.connect(isa);

            // client.bind(isa);
            print(true, "Porta allocata: " + client.getLocalPort());
            print(true, "Indirizzo: " + client.getInetAddress()
                + "; porta: " + client.getPort());

            String frase = br.readLine();

            OutputStream toSrv = client.getOutputStream();
            toSrv.write(frase.getBytes(), 0, frase.length());                

            Thread.sleep(1 * 1_000);
        } catch (UnknownHostException u) {
            u.printStackTrace();
        } catch (IOException i) {
            i.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}