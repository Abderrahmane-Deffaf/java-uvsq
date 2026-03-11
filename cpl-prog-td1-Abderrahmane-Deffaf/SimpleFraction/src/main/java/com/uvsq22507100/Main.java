package com.uvsq22507100;

public class Main {
    public static void main(String[] args) {
        showConstructors();
        showConstants();
        showAccessors();
        showNumericConversions();
        showAddition();
        showEquality();
        showComparison();
        showStringRepresentation();
        showInvalidConstruction();
    }

    private static void showConstructors() {
        printSection("Constructors");

        Fraction defaultFraction = new Fraction();
        Fraction integerFraction = new Fraction(5);
        Fraction customFraction = new Fraction(3, 4);

        System.out.println("new Fraction() creates the default value: " + defaultFraction);
        System.out.println("new Fraction(5) creates an integer fraction: " + integerFraction);
        System.out.println("new Fraction(3, 4) creates a custom fraction: " + customFraction);
    }

    private static void showConstants() {
        printSection("Constants");

        System.out.println("Fraction.ZERO = " + Fraction.ZERO + " (represents zero)");
        System.out.println("Fraction.UN = " + Fraction.UN + " (represents one)");
    }

    private static void showAccessors() {
        printSection("Accessors");

        Fraction fraction = new Fraction(7, 9);

        System.out.println("Fraction used for getters: " + fraction);
        System.out.println("getNum() returns the numerator: " + fraction.getNum());
        System.out.println("getDen() returns the denominator: " + fraction.getDen());
    }

    private static void showNumericConversions() {
        printSection("Numeric Conversions");

        Fraction fraction = new Fraction(7, 3);

        System.out.println("Fraction used for conversion methods: " + fraction);
        System.out.println("doubleValue() = " + fraction.doubleValue());
        System.out.println("floatValue() = " + fraction.floatValue());
        System.out.println("intValue() = " + fraction.intValue());
        System.out.println("longValue() = " + fraction.longValue());
    }

    private static void showAddition() {
        printSection("Addition");

        Fraction first = new Fraction(1, 2);
        Fraction second = new Fraction(1, 3);
        Fraction result = first.add(second);

        System.out.println("First fraction: " + first);
        System.out.println("Second fraction: " + second);
        System.out.println("add(...) returns their sum: " + result);
        System.out.println("Numeric value of the result: " + result.doubleValue());
    }

    private static void showEquality() {
        printSection("Equality");

        Fraction a = new Fraction(1, 2);
        Fraction b = new Fraction(2, 4);
        Fraction c = new Fraction(3, 4);

        System.out.println(a + ".equals(" + b + ") = " + a.equals(b)
                + " because both represent the same reduced value");
        System.out.println(a + ".equals(" + c + ") = " + a.equals(c)
                + " because they represent different values");
    }

    private static void showComparison() {
        printSection("Comparison");

        Fraction smaller = new Fraction(1, 3);
        Fraction larger = new Fraction(3, 5);
        Fraction equivalent = new Fraction(2, 6);

        System.out.println(smaller + ".compareTo(" + larger + ") = " + smaller.compareTo(larger)
                + " (negative means smaller)");
        System.out.println(larger + ".compareTo(" + smaller + ") = " + larger.compareTo(smaller)
                + " (positive means larger)");
        System.out.println(smaller + ".compareTo(" + equivalent + ") = " + smaller.compareTo(equivalent)
                + " (zero means equal value)");
    }

    private static void showStringRepresentation() {
        printSection("String Representation");

        Fraction fraction = new Fraction(11, 8);
        System.out.println("toString() displays the fraction as numerator/denominator: " + fraction);
    }

    private static void showInvalidConstruction() {
        printSection("Error Handling");

        try {
            new Fraction(4, 0);
        } catch (IllegalArgumentException exception) {
            System.out.println("Creating a fraction with denominator 0 throws IllegalArgumentException");
            System.out.println("Message: " + exception.getMessage());
        }
    }

    private static void printSection(String title) {
        System.out.println();
        System.out.println("=== " + title + " ===");
    }
}
