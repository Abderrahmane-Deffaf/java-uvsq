package fr.uvsq.cprog.collex;

public class NomMachine {
  private String name;

  public NomMachine(String name) {
    if (isValid(name)) {
      this.name = name;
      return;
    }
    throw new IllegalArgumentException("Nom de machine invalide");
  }


  public boolean isValid(String name) {
    if (name == null || name.isEmpty()) {
      return false;
    }

    // Vérifier la longueur totale
    if (name.length() > 253) {
      return false;
    }

    // Pattern pour un nom de domaine valide
    // Chaque label: commence et finit par alphanumérique, peut contenir tirets au milieu
    String domainPattern = "^([a-zA-Z0-9]([a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,}$";

    if (!name.matches(domainPattern)) {
      return false;
    }

    // Vérifier chaque label individuellement
    String[] labels = name.split("\\.");

    // Doit avoir au moins 2 labels (domain.tld)
    if (labels.length < 2) {
      return false;
    }

    for (String label : labels) {
      // Chaque label doit avoir entre 1 et 63 caractères
      if (label.length() < 1 || label.length() > 63) {
        return false;
      }

      // Ne peut pas commencer ou finir par un tiret
      if (label.startsWith("-") || label.endsWith("-")) {
        return false;
      }
    }

    return true;
  }


  public String getName() {
    return name;
  }


}
