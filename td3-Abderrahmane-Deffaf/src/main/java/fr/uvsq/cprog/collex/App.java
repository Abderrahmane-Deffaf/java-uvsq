package fr.uvsq.cprog.collex;

public class App 
{
    public static void main( String[] args )
    {
        Dns dns = new Dns();
        // DnsItem item = dns.getItem("www.ecampus.uvsq.fr");

        // DnsItem[] items = dns.getItems("uvsq.fr");
        // System.out.println("All items for domain 'uvsq.fr':");
        // for (DnsItem dnsItem : items) {
        // System.out.println(
        // "Machine Name: " + dnsItem.getName() + ", IP Address: " + dnsItem.getIp());
        // }
        // try {
        // DnsItem newItem = dns.addItem("www.newsite.uvsq.fr", "192.168.1.100");
        // if (newItem != null) {
        // System.out.println("Added new item: " + "Machine Name: " + newItem.getName()
        // + ", IP Address: " + newItem.getIp());
        // }
        // } catch (Exception e) {
        // System.err.println("Failed to add new item: " + e.getMessage());
        // }
        DnsApp app = new DnsApp(dns);
        app.run();
    }
}
