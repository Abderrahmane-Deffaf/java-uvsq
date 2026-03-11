package fr.uvsq.cprog.collex;

public class DnsApp {
  public DnsTUI tui;

  public DnsApp(Dns dns) {
    this.tui = new DnsTUI(dns);
  }

  public void run() {
    System.out.println("Bienvenue dans l'application DNS !");
    tui.nextCommande();
  }
}
