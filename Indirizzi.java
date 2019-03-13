import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.Socket;
import java.net.InetSocketAddress;
import java.util.Arrays;

public class Indirizzi {
    public static void print(Boolean flag, Object... s) {
        for (Object o : s) {
            System.out.print(o);
        }

        if (flag)
            System.out.println();
        else
            System.out.print("");
    }

    public static void getByName(String name) throws UnknownHostException {
        InetAddress ia = InetAddress.getByName(name);
        byte[] ndp = ia.getAddress();
        print(false, "Indirizzo: ");
        for (byte b : ndp) {
            print(false, b & 0xff, ".");
        }
        print(true);
    }

    public static void getAllByName(String name) throws UnknownHostException {
        InetAddress[] iaa = InetAddress.getAllByName(name);

        for (InetAddress ia : iaa) {
            print(true, "Indirizzo: " + ia.getHostName() + " -> " + ia.getHostAddress());
        }
    }

    public static void main(String[] args) {
        String name = args[0];

        try {
            // getByName(name);
            getAllByName(name);
        } catch (UnknownHostException u) {
            u.printStackTrace();
        }
    }
}