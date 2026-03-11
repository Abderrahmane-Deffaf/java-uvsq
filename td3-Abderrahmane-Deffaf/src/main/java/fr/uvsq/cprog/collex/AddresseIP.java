package fr.uvsq.cprog.collex;

public class AddresseIP {
  
  private String ip; 

  public AddresseIP(String ip) {
    if (isValid(ip)) {
      this.ip = ip;
      return;
    }
    throw new IllegalArgumentException("Adresse IP invalide");
  }


  public Boolean isValid(String ip) {
    if (ip == null || ip.isEmpty()) {
      return false;
    }

    // Vérifier le format avec regex
    String ipPattern = "^([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})$";
    
    if (!ip.matches(ipPattern)) {
      return false;
    }

    // Vérifier que chaque octet est entre 0 et 255
    String[] parts = ip.split("\\.");
    
    if (parts.length != 4) {
      return false;
    }

    try {
      for (String part : parts) {
        int value = Integer.parseInt(part);
        if (value < 0 || value > 255) {
          return false;
        }
      }
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  public String getIp() {
    return ip;
  }
}
