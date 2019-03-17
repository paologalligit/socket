import java.lang.InterruptedException;

public class Scacchiera {
  public Scacchiera() {
    System.out.println("Inizializzazione scacchiera");
  }

  public synchronized void muovi(String player, int playerId) {
    System.out.println("Mossa: " + player + " da giocare " + playerId);
    notify();
    return;
  }

  public synchronized void turno() {
    try {
      wait();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}