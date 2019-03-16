public class ContaStringhe {
  private int totale;

  public ContaStringhe() {
    totale = 0;
    System.out.println("Inizializzato totale: " + totale);
  }

  public int getTotale() {
    return totale;
  }

  public synchronized int incrementa(int valore) {
    totale += valore;
    return totale;
  }
}