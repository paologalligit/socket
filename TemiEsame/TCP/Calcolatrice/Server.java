import java.net.Socket;
import java.net.ServerSocket;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;

public class Server {
  // Attributi
  private Socket client;
  private ServerSocket server;
  private byte[] buffer;
  final private int DIM_BUFFER = 255;

  // Costruttori
  public Server(int port) throws IOException {
    server = new ServerSocket(port);
    buffer = new byte[DIM_BUFFER];
  }
  public Server() throws IOException {
    this(0);
  }

  // Metodi
  public void printServerInfos() {
    System.out.println("Indirizzo: " + server.getInetAddress() + "; porta: " + server.getLocalPort());
  }
  public void printClientInfos() {
    System.out.println("Indirizzo: " + client.getInetAddress() + "; porta: " + client.getLocalPort());
  }
  public void accept() throws IOException {
    client = server.accept();
    System.out.println("Nuovo client connesso!");
    printClientInfos();
  }
  public void close() throws IOException {
    client.close();
  }
  public String listen() throws IOException {
    InputStream fromCl = client.getInputStream();
    int letti = fromCl.read(buffer);
    if (letti < 0) {
      return "Client disconnesso";
    }

    String message = new String(buffer, 0, letti);
    System.out.println("Ricevuto da " + client.getLocalPort() + ": " + message);

    return message;
  }
  public void sendMessage() throws IOException {
    InputStreamReader tastiera = new InputStreamReader(System.in);
    BufferedReader br = new BufferedReader(tastiera);

    String frase = br.readLine();

    OutputStream toCli = client.getOutputStream();
    toCli.write(frase.getBytes(), 0, frase.length());
  }
  public void sendMessage(String message) throws IOException {
    OutputStream toCli = client.getOutputStream();
    toCli.write(message.getBytes(), 0, message.length());
  }
  public static boolean isValid(String s) {
    return "+-*/".indexOf(s) >= 0;
  }
  public double eval(double a, double b, String op) {
    double res;
    
    switch(op) {
      case "+": res = a + b;
                break;
      case "-": res = a - b;
                break;
      case "*": res = a * b;
                break;
      case "/": res = a / b;
                break;
      default: throw new IllegalArgumentException("Impossibile essere qui!");
    }

    return res;
  }
  public static boolean tryParseDouble(String s) {
    try {
      Double.parseDouble(s);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  //Main
  public static void main(String[] args) {
    try {
      Server calcolatrice = new Server();
      calcolatrice.printServerInfos();

      while (true) {
        try {
          String continua = "";

          do {
             // connessione con client
            calcolatrice.accept();
  
            String symbol;
            // controllo simbolo
            do {
              symbol = calcolatrice.listen();
  
              if (isValid(symbol)) {
                calcolatrice.sendMessage("k");
              } else {
                calcolatrice.sendMessage("x");
              }
            } while (!isValid(symbol));
  
            String op1, op2;
            double n1, n2;
  
            boolean check1 = false, check2 = false;
            // op2
            do {
              // op1
              do {
                op1 = calcolatrice.listen();
  
                check1 = tryParseDouble(op1);
                if (check1) {
                  calcolatrice.sendMessage("k");
                } else {
                  calcolatrice.sendMessage("x");
                }
              } while (!check1);
  
              op2 = calcolatrice.listen();
  
              check2 = tryParseDouble(op2);
  
              if (check2) {
                calcolatrice.sendMessage("k");
              } else {
                calcolatrice.sendMessage("x");
              }
  
            } while (!check2);
  
            n1 = Double.parseDouble(op1);
            n2 = Double.parseDouble(op2);
  
            double res = calcolatrice.eval(n1, n2, symbol);
            String messageRes = Double.toString(res);
  
            calcolatrice.sendMessage(messageRes);

            continua = calcolatrice.listen();
  
          } while (!continua.equals("."));
  
          calcolatrice.close();
          System.out.println("Client disconnesso");

        } catch (Exception e) {
          System.out.println("Client disconnesso");
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
} 