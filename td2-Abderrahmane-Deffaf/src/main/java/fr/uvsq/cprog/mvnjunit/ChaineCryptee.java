package fr.uvsq.cprog.mvnjunit;


public class ChaineCryptee {
    private final String chaineEnClair;
    private final int decalage;
    
    private ChaineCryptee(String chaineEnClair, int decalage) {
        this.chaineEnClair = chaineEnClair;
        this.decalage = decalage;
    }
    
    public static ChaineCryptee deEnClair(String chaineEnClair, int decalage) {
        if (chaineEnClair == null) {
            return new ChaineCryptee("", decalage);
        }
        return new ChaineCryptee(chaineEnClair, decalage);
    }
    
    public static ChaineCryptee deCryptee(String chaineCryptee, int decalage) {
        StringBuilder chaineEnClair = new StringBuilder();
        for (int i = 0; i < chaineCryptee.length(); i++) {
            char c = chaineCryptee.charAt(i);
            chaineEnClair.append(decaleCaractere(c, -decalage));
        }
        
        return new ChaineCryptee(chaineEnClair.toString(), decalage);
    }
    
    public String decrypte() {
        return chaineEnClair;
    }
    
    public String crypte() {
        StringBuilder chaineCryptee = new StringBuilder();
        for (int i = 0; i < chaineEnClair.length(); i++) {
            char c = chaineEnClair.charAt(i);
            chaineCryptee.append(decaleCaractere(c, decalage));
        }
        return chaineCryptee.toString();
    }
    
    static char decaleCaractere(char c, int decalage) {
      if (c < 'A' || c > 'Z') {
          return c; // Ne pas décaler les espaces
      }
      return (c < 'A' || c > 'Z')? c : (char)(((c - 'A' + decalage) % 26) + 'A');
    }
    
    public int getDecalage() {
        return decalage;
    }
}