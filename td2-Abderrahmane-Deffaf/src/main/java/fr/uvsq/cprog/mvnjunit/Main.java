package fr.uvsq.cprog.mvnjunit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Classe principale pour démontrer les fonctionnalités de ChaineCryptee.
 */
public class Main {
    
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    
    public static void main(String[] args) {
        logger.info("=== Démonstration de la classe ChaineCryptee ===");
        
        // Test avec une chaîne simple
        logger.info("1. Test avec 'HELLO' et décalage de 3:");
        ChaineCryptee chaine1 = ChaineCryptee.deEnClair("HELLO", 3);
        logger.info("Chaîne en clair: {}", chaine1.decrypte());
        logger.info("Chaîne cryptée: {}", chaine1.crypte());
        
        // Test avec l'exemple du sujet
        logger.info("2. Test avec 'A' et décalage de 3 (exemple du sujet):");
        ChaineCryptee chaine2 = ChaineCryptee.deEnClair("A", 3);
        logger.info("Chaîne en clair: {}", chaine2.decrypte());
        logger.info("Chaîne cryptée: {} (doit afficher 'D')", chaine2.crypte());
        
        // Test avec une phrase contenant des espaces
        logger.info("3. Test avec 'HELLO WORLD' et décalage de 5:");
        ChaineCryptee chaine3 = ChaineCryptee.deEnClair("HELLO WORLD", 5);
        logger.info("Chaîne en clair: {}", chaine3.decrypte());
        logger.info("Chaîne cryptée: {}", chaine3.crypte());
        
        // Test de création à partir d'une chaîne cryptée
        logger.info("4. Test de création à partir d'une chaîne cryptée:");
        String chaineCryptee = chaine3.crypte();
        ChaineCryptee chaine4 = ChaineCryptee.deCryptee(chaineCryptee, 5);
        logger.info("Chaîne cryptée: {}", chaineCryptee);
        logger.info("Chaîne décryptée: {}", chaine4.decrypte());
        
        // Test avec bouclage (Z -> C avec décalage 3)
        logger.info("5. Test avec bouclage 'XYZ' et décalage de 3:");
        ChaineCryptee chaine5 = ChaineCryptee.deEnClair("XYZ", 3);
        logger.info("Chaîne en clair: {}", chaine5.decrypte());
        logger.info("Chaîne cryptée: {} (doit afficher 'ABC')", chaine5.crypte());
        
        logger.info("=== Fin de la démonstration ===");
    }
}
