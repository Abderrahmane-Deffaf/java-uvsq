package fr.uvsq.cprog.collex;

import java.util.Scanner; 

public class DnsTUI {
  private Dns dns;

  public DnsTUI(Dns dns) {
    this.dns = dns;
  }
  
  public void nextCommande() {
    Scanner scanner = new Scanner(System.in);
    String input = "hello";
    while (!input.equals("exit")) {
      input = scanner.nextLine().trim();
      if (input.startsWith("add")) {
        String[] parts = input.split(" ");
        if (parts.length == 3) {
          String name = parts[1];
          String ip = parts[2];
          Commande ajouterCommande = new AjouterItem(this.dns, name, ip);
          System.out.println(ajouterCommande.execute());
        } else {
          System.out.println("Usage: add <machine_name> <ip_address>");
        }
      } 

      if (input.startsWith("search")) {
        String[] parts = input.split(" ");
        if (parts.length != 2) {
          System.out.println("Usage: search <nom_machine_ou_adresse_ip>");
          continue;
        } 
        String query = parts[1];
        Commande rechercheCommande = new RechercherItem(this.dns, query);
        Object result = rechercheCommande.execute();
        System.out.println(result);
      }

      if (input.startsWith("ls -a")) {
        String[] parts = input.split(" ");
        if (parts.length != 3) {
          System.out.println("Usage: ls -a <domaine>");
          continue;
        }
        String domain = parts[2];
        Commande rechercheDomaineCommande = new RechercheDomaine(this.dns, domain);
        Object result = rechercheDomaineCommande.execute();
        if (result instanceof DnsItem[]) {
          DnsItem[] items = (DnsItem[]) result;
          for (DnsItem dnsItem : items) {
            System.out.println(dnsItem.getName() + " " + dnsItem.getIp());
          }
        } else {
          System.out.println(result);
        }
      }
    }

    scanner.close();
    
  }
}
