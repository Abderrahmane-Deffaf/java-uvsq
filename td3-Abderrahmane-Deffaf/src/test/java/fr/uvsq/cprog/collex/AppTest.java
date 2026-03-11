package fr.uvsq.cprog.collex;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void testAddresseIPValid() {
        AddresseIP ip = new AddresseIP("192.168.1.1");
        assertEquals("192.168.1.1", ip.getIp());
        try {
            new AddresseIP("999.999.999.999");
            fail("Expected IllegalArgumentException for invalid IP");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    @Test
    public void testMachineNameValid() {
        NomMachine name = new NomMachine("www.uvsq.fr");
        assertEquals("www.uvsq.fr", name.getName());
        try {
            new NomMachine("invalid_name@.com");
            fail("Expected IllegalArgumentException for invalid machine name");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    @Test
    public void testDnsItemValid() {
        DnsItem item = new DnsItem("www.uvsq.fr", "192.168.1.1");
        assertEquals("www.uvsq.fr", item.getName());
        assertEquals("192.168.1.1", item.getIp());
    }

    @Test
    public void testDnsAddGetItem() {
        Dns dns = new Dns();
        DnsItem newItem = new DnsItem("hello.uvsq.fr", "192.168.33.1");
        dns.addItem(newItem.getIp(), newItem.getName());

        assertEquals(newItem.getIp(), dns.getItem("hello.uvsq.fr").getIp());
    }

}
