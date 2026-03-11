package fr.uvsq.cprog.collex;

public final class App {
  private App() {
  }

  public static void main(String[] args) {
    Dns dns = new Dns();

    if (args.length > 0 && "interactive".equalsIgnoreCase(args[0])) {
      new DnsApp(dns).run();
      return;
    }

    afficherPresentation(dns);

    if (args.length > 0 && "add-demo".equalsIgnoreCase(args[0])) {
      demonstrerAjout(dns);
    } else {
      System.out.println();
      System.out.println("Ajout non execute pour eviter de modifier la base par defaut.");
      System.out.println("Relancez avec l'argument 'add-demo' pour tester un ajout persistant.");
      System.out.println("Relancez avec l'argument 'interactive' pour utiliser la console DNS.");
    }
  }

  private static void afficherPresentation(Dns dns) {
    System.out.println("=== Presentation du projet collex ===");
    System.out.println("Base chargee depuis : src/main/resources/" + dns.getFileName());
    System.out.println();

    System.out.println("[1] Validation des objets metier");
    AddresseIP ip = new AddresseIP("193.51.31.90");
    NomMachine nom = new NomMachine("www.uvsq.fr");
    DnsItem item = new DnsItem(nom.getName(), ip.getIp());
    afficherItem("DnsItem valide", item);
    System.out.println();

    System.out.println("[2] Recherche d'une machine par nom");
    afficherCommande(new RechercherItem(dns, "www.uvsq.fr").execute());
    System.out.println();

    System.out.println("[3] Recherche d'une machine par adresse IP");
    afficherCommande(new RechercherItem(dns, "193.51.25.12").execute());
    System.out.println();

    System.out.println("[4] Liste des machines du domaine uvsq.fr");
    Object resultatDomaine = new RechercheDomaine(dns, "uvsq.fr").execute();
    if (resultatDomaine instanceof DnsItem[]) {
      DnsItem[] items = (DnsItem[]) resultatDomaine;
      for (DnsItem dnsItem : items) {
        afficherItem("Machine du domaine", dnsItem);
      }
    } else {
      System.out.println(resultatDomaine);
    }
  }

  private static void demonstrerAjout(Dns dns) {
    String suffixe = String.valueOf(System.currentTimeMillis());
    String nom = "demo-" + suffixe + ".uvsq.fr";
    String ip = "10.0.0." + (Math.abs(suffixe.hashCode()) % 200 + 1);

    System.out.println();
    System.out.println("[5] Ajout d'une entree DNS via le service");
    DnsItem ajoute = dns.addItem(ip, nom);
    if (ajoute == null) {
      System.out.println("Ajout impossible.");
      return;
    }

    afficherItem("Nouvelle entree", ajoute);
    afficherCommande(new RechercherItem(dns, nom).execute());
  }

  private static void afficherCommande(Object resultat) {
    System.out.println(resultat);
  }

  private static void afficherItem(String label, DnsItem item) {
    System.out.println(label + " : " + item.getName() + " -> " + item.getIp());
  }
}
