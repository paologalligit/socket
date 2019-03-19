import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket client;
    private InetSocketAddress isa;
    final private int DIM_BUF = 255;

    public Client(int port) throws IOException {
        client = new Socket();
        InetAddress ia = InetAddress.getLocalHost();
        isa = new InetSocketAddress(ia, port);
    }

    public void printClientInfos() {
        System.out.println("Client address: " + client.getInetAddress() + "; port: " + client.getLocalPort());
    }
    public String listen() throws IOException {
        byte[] buffer = new byte[DIM_BUF];
        InputStream fromSrv = client.getInputStream();
        int letti = fromSrv.read(buffer);

        return new String(buffer, 0, letti);
    }
    public void sendMessage(String message) throws IOException {
        OutputStream toSrv = client.getOutputStream();
        toSrv.write(message.getBytes(), 0, message.length());
    }
    public String sendMessage() throws IOException {
        InputStreamReader tastiera = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(tastiera);
        String message = br.readLine();

        sendMessage(message);

        return message;
    }
    public void connect() throws IOException {
        client.connect(isa);
    }
    public void closeClient() throws IOException {
        client.close();
    }
    public void quit() throws IOException {
        sendMessage("quit");
        closeClient();
    }

    public static void main(String[] args) {
        try {
            Client client = new Client(Integer.parseInt(args[0]));
            client.connect();
            client.printClientInfos();

            System.out.print("Login: ");
            client.sendMessage();

            String response = client.listen();

            if (response.equals("Denied")) {
                System.out.println("Connessione rifiutata!");
                client.closeClient();
            } else {
                System.out.println("Message from server: " + response);
                String scelta = "", request;
                do {
                    do {
                        System.out.print("Operazione: ");
                        request = client.sendMessage();
                        if (request.equals("quit")) {
                            scelta = "quit";
                            break;
                        }
                        scelta = client.listen();
                        System.out.println("rievuto dal server: " + scelta);
                    } while (scelta.equals("x"));
                } while (!scelta.equals("quit"));
            }
            
            System.out.println("Client closed");
            client.closeClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}