package com.uvsq22507100;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }
    public void testFractionConstructors() {
        Fraction f1 = new Fraction(3, 4);
        assertEquals("3/4", f1.toString());

        Fraction f2 = new Fraction(5);
        assertEquals("5/1", f2.toString());

        Fraction f3 = new Fraction();
        assertEquals("0/1", f3.toString());
    }
    public void testDoubleValue() {
        Fraction f4 = new Fraction(1, 2);
        assertEquals(0.5, f4.doubleValue(), 1e-9);
    }

    public void testAdd() {
        Fraction f5 = new Fraction(1, 2);
        Fraction f6 = new Fraction(1, 3);
        Fraction sum = f5.add(f6);
        assertEquals("5/6", sum.toString());
    }

    public void testEquals() {
        Fraction f7 = new Fraction(2, 4);
        Fraction f8 = new Fraction(1, 2);
        assertTrue(f7.equals(f8));
    }

    public void testCompareTo() {
        Fraction f9 = new Fraction(1, 3);
        Fraction f10 = new Fraction(1, 2);
        assertTrue(f9.compareTo(f10) < 0);
        assertTrue(f10.compareTo(f9) > 0);
        assertTrue(f9.compareTo(new Fraction(2, 6)) == 0);
    }
    
    public void testNumber() {
        Number aNumber = java.math.BigDecimal.ONE;
        Number anotherNumber = new Fraction(1, 2);
        assert java.lang.Math.abs(aNumber.doubleValue() + anotherNumber.doubleValue() - 1.5) < 1E-8 ; 
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
}
