import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.io.IOException;
import java.net.SocketException;

public class Stazione extends Client {
    public Stazione(String hostName, int port) throws SocketException {
        super(hostName, port);
    }

    public String sendTrainMessage() throws IOException {
        InputStreamReader tastiera = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(tastiera);

        String message;
        do {
            System.out.print("N° treno e ritardo in min: ");
            message = br.readLine();
        } while (!checkTrainMessage(message));

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
    public static boolean checkTrainMessage(String m) {
        if (m.equals(".")) {
            return true;
        }

        String[] args = m.split(" ");
        try {
            int nTreno = Integer.parseInt(args[0]);
            if (nTreno <= 0 || nTreno >= 10) {
                return false;
            }

            Integer.parseInt(args[1]);
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

            Stazione client = new Stazione(args[0], Integer.parseInt(args[1]));
            client.printClientInfos();
            
            String message;
            client.sendTrainMessage();
            do {
                String response = client.listen();
                System.out.println("Server response: " + response);
                message = client.sendTrainMessage();
            } while (!message.equals("."));

            System.out.println("Client closed!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}