package fr.uvsq.cprog.collex;

public class AjouterItem implements Commande {
  private Dns dns;
  private String name;
  private String ip;

  public AjouterItem(Dns dns, String ip, String name) {
    this.dns = dns;
    this.ip = ip;
    this.name = name;
  }

  @Override
  public Object execute() {
    dns.addItem(this.ip, this.name);
    return "Item ajouté";
  }
}
