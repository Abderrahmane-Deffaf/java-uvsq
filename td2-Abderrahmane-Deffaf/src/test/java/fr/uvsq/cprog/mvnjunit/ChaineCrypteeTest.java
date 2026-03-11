package fr.uvsq.cprog.mvnjunit;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tests unitaires pour la classe ChaineCryptee.
 */
public class ChaineCrypteeTest {

    @Test
    public void testCreationDeEnClair() {
        ChaineCryptee chaine = ChaineCryptee.deEnClair("HELLO", 3);
        assertEquals("HELLO", chaine.decrypte());
        assertEquals(3, chaine.getDecalage());
    }

    @Test
    public void testCreationDeCryptee() {
        ChaineCryptee chaine = ChaineCryptee.deCryptee("KHOOR", 3);
        assertEquals("HELLO", chaine.decrypte());
        assertEquals(3, chaine.getDecalage());
    }

    @Test
    public void testCryptageSimple() {
        ChaineCryptee chaine = ChaineCryptee.deEnClair("ABC", 3);
        assertEquals("DEF", chaine.crypte());
    }

    @Test
    public void testDecryptageSimple() {
        ChaineCryptee chaine = ChaineCryptee.deEnClair("DEF", 3);
        assertEquals("DEF", chaine.decrypte());
    }

    @Test
    public void testCryptageAvecBouclage() {
        ChaineCryptee chaine = ChaineCryptee.deEnClair("XYZ", 3);
        assertEquals("ABC", chaine.crypte());
    }

    

    @Test
    public void testDecalageNegatif() {
        ChaineCryptee chaine = ChaineCryptee.deEnClair("DEF", -3);
        assertEquals("ABC", chaine.crypte());
    }

    @Test
    public void testDecalageGrand() {
        // Test avec un décalage supérieur à 26
        ChaineCryptee chaine = ChaineCryptee.deEnClair("A", 29); // 29 % 26 = 3
        assertEquals("D", chaine.crypte());
    }

    // @Test
    // public void testNullInput() {
    //     ChaineCryptee chaine = ChaineCryptee.deEnClair(null, 3);
        
    // }
}