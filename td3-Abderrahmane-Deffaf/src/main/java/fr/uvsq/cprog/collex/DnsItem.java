package fr.uvsq.cprog.collex;

public class DnsItem {
  private String name;
  private String ip;

  public DnsItem(String name, String ip) {
    this.name = new NomMachine(name).getName();
    this.ip = new AddresseIP(ip).getIp();
  }

  public String getName() {
    return name;
  }

  public String getIp() {
    return ip;
  }

}
