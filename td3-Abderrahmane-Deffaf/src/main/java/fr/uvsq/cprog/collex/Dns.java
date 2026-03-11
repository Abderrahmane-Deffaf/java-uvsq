package fr.uvsq.cprog.collex;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Dns {
  private Map<String, String> db = new HashMap<>();

  public Dns() {

    // charger la base de donnee
    String fileName = getFileName();
    if (fileName != null) {
      try {
        BufferedReader reader;
        reader = new BufferedReader(new FileReader("src/main/resources/" + fileName));
        String line = reader.readLine();

        while (line != null) {
          String[] parts = line.split(" ");
          if (parts.length >= 2) {
            String key = parts[0];
            String value = parts[1];
            db.put(key, value);
          }
          // read next line
          line = reader.readLine();
        }
        reader.close();
      } catch (IOException e) {
        System.err.println("Error loading database: " + e.getMessage());
      }
    } else {
      System.out.println("No file name provided in config.properties");
    }

  }

  public String getFileName() {
    // get the db name
    String fileName = null;
    Properties prop = new Properties();
    InputStream input = null;
    try {
      input = new FileInputStream("config.properties");
      prop.load(input);
      fileName = prop.getProperty("file_name");
      return fileName;
    } catch (IOException ex) {
      ex.printStackTrace();
      return "";
    } finally {
      if (input != null) {
        try {
          input.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  public DnsItem getItem(String partOne) {
    String ipItem = "";
    String machineNameItem = "";

    for (Map.Entry<String, String> entry : db.entrySet()) {
      String machineName = entry.getValue();
      String ip = entry.getKey();
      if (machineName.equals(partOne)) {
        machineNameItem = machineName;
        ipItem = ip;
      } else if (ip.equals(partOne)) {
        ipItem = ip;
        machineNameItem = machineName;
      }
    }
    return new DnsItem(machineNameItem, ipItem);
  }

  public DnsItem[] getItems(String domainName) {

    List<DnsItem> items = new ArrayList<>();
    for (Map.Entry<String, String> entry : db.entrySet()) {
      String machineName = entry.getValue();
      String ip = entry.getKey();
      if (machineName.contains(domainName)) {
        items.add(new DnsItem(machineName, ip));
      }
    }
    return items.toArray(new DnsItem[0]);
  }

  public boolean exists(String query) {
    return db.containsKey(query) || db.containsValue(query);
  }

  public DnsItem addItem(String ip, String name) {
    try {
      if (exists(ip) || exists(name)) {
        throw new Exception("Item with the same name or IP already exists.");
      }
      db.put(ip, name);

      // save to db file
      String fileName = getFileName();
      if (fileName == null) {
        throw new Exception("Database file name not found.");
      }
      BufferedWriter writer =
          new BufferedWriter(new FileWriter("src/main/resources/" + fileName, true));
      writer.write("\n" + ip + " " + name);
      writer.newLine();
      writer.close();
      return new DnsItem(name, ip);
    } catch (Exception e) {
      System.err.println("Error adding item: " + e.getMessage());
      return null;
    }
  }
}
