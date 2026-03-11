package fr.uvsq.cprog.collex;

public class RechercherItem implements Commande {
  private Dns dns;
  private String query;
  public RechercherItem(Dns dns, String query) {
    this.dns = dns;
    this.query = query;
  }
  @Override
  public Object execute() {
    DnsItem item = dns.getItem(query);
    if (item != null) {
      return item.getIp() + " " + item.getName();
    }
    return "Aucun résultat trouvé pour : " + query;
  }
}
