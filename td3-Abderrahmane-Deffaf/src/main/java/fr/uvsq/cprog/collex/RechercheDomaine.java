package fr.uvsq.cprog.collex;

public class RechercheDomaine implements Commande {
  private Dns dns;
  private String query;

  public RechercheDomaine(Dns dns, String query) {
    this.dns = dns;
    this.query = query;
  }

  @Override
  public Object execute() {
    DnsItem[] items = dns.getItems(query);
    if (items != null) {
      return items;
    }
    return "Aucun résultat trouvé pour : " + query;
  }
}
