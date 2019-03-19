import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;

public class ErogaServizio extends Thread {
  private Socket client;
  private HashMap<String, Double> table;

  public ErogaServizio(Socket client, HashMap<String, Double> table) {
    this.client = client;
    this.table = table;
  }

  public static String listen(Socket client) throws IOException {
    InputStream fromCli = client.getInputStream();
    byte[] buffer = new byte[255];
    int letti = fromCli.read(buffer);
    if (letti < 0) {
      return "Disconnesso";
    }

    return new String(buffer, 0, letti);
  }
  public static void sendMessage(String message, Socket client) throws IOException {
    OutputStream toCli = client.getOutputStream();
    toCli.write(message.getBytes(), 0, message.length());
  }
  public static void closeClient(Socket client) throws IOException {
    client.close();
  }
  public static synchronized String manageQuery(String query, String user, HashMap<String, Double> table) {
    String[] args = query.split(" ");
    Double amount;
    switch (args[0]) {
      case "bilancio": return String.valueOf(table.get(user));
      case "bonifico": if (args[1].equals(user)) {
                        return "x";
                       }
                       amount = table.getOrDefault(args[1], null);
                       if (amount == null) {
                         return "x";
                       }
                       Double userAmount = table.get(user);
                       Double sum = Double.parseDouble(args[2]);
                       if (Double.compare(userAmount, sum) < 0) {
                         return "x";
                       }
                       table.put(user, userAmount - sum);
                       table.put(args[1], amount + sum);
                       return "k";
      case "missiva": amount  = table.get(user);
                      if (amount < 5.0) {
                        return "x";
                      }
                      table.put(user, amount - 5.0);
                      return "k";
      case "quit": return "quit";
      default: return "x";
    }
  }
  public static boolean checkQuery(String q) {
    String[] args = q.split(" ");

    System.out.println("in checkquery: " + Arrays.toString(args));

    switch (args[0]) {
      case "bilancio": return args.length == 1;
      case "bonifico": return args.length == 3;
      case "missiva": return args.length == 1;
      case "quit": return args.length == 1;
      default: return false;
    }
  }

  public void run() {
    try {
      String user = listen(client);

      Double amount = table.getOrDefault(user, null);
      if (amount == null) {
        sendMessage("Denied", client);
        System.out.println("Chiudo client " + client.getInetAddress() + " on " + client.getPort());
        client.close();
      } else {
        sendMessage("k", client);
        System.out.println("Mando k a client");

        boolean check;
        String query, result;
        do {
          do {
            query = listen(client);
            check = checkQuery(query);
  
            System.out.println("Query: " + query);
            if (!check) {
              sendMessage("x", client);
              System.out.println("Mando messaggio k al client");
            } 
          } while (!check);
    
          result = manageQuery(query, user, table);
          System.out.println("Mando a client " + result);
          sendMessage(result, client);
        } while (!result.equals("quit"));  
      }
      
    } catch (Exception e) {
      System.out.println("Client disconnesso");
    }
  }
}