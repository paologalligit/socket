import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class Utente extends Client {
    public Utente(String hostName, int port) throws SocketException {
        super(hostName, port);
    }
    public String sendUserMessage() throws IOException {
        InputStreamReader tastiera = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(tastiera);

        String message;
        do {
            System.out.print("N° treno: ");
            message = br.readLine();
        } while (!checkUserMessage(message));

        if (message.equals(".")) {
            client.close();
            return "";
        }

        byte[] buffer = message.getBytes();

        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        packet.setSocketAddress(isa);
        client.send(packet);

        return message;
    }

    public static boolean checkUserMessage(String m) {
        if (m.equals(".")) {
            return true;
        }

        String[] args = m.split(" ");
        try {
            int nTreno = Integer.parseInt(args[0]);
            if (nTreno <= 0 || nTreno >= 10) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public static void main(String[] args) {
        try {
            if (args.length != 2) {
                throw new IllegalArgumentException("Two arguments are required!");
            }

            Utente client = new Utente(args[0], Integer.parseInt(args[1]));
            client.printClientInfos();
            
            String message;
            client.sendUserMessage();
            do {
                String response = client.listen();
                System.out.println("Server response: " + (response.equals("") ? "No infos" : response));
                message = client.sendUserMessage();
            } while (!message.equals("."));

            System.out.println("Client closed!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}