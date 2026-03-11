package com.uvsq22507100;

public class Fraction extends Number implements Comparable<Fraction> {

  private int num;
  private int den;

  public Fraction(int num, int den) {
    if (den == 0) {
        throw new IllegalArgumentException("Le dénominateur ne peut pas être zéro");
    }

    this.num = num;
    this.den = den;
  }

  public Fraction(int num) {
    this(num, 1);
  }

  public Fraction() {
    this(0, 1);
  }

  public static final Fraction ZERO = new Fraction(0, 1);
  public static final Fraction UN = new Fraction(1, 1);
  
  public int getNum() {
    return num;
  }

  public int getDen() {
    return den;
  }
  public double doubleValue() {
    return (double) num / den;
  }

  public Fraction add(Fraction other) {
    int newNum = this.num * other.den + other.num * this.den;
    int newDen = this.den * other.den;
    return new Fraction(newNum, newDen);
  }

  @Override
  public boolean equals(Object obj) {
      if (this == obj) return true;
      
      Fraction fraction = (Fraction) obj;
      
      // Réduire les fractions pour comparer
      Fraction thisReduite = this.reduire();
      Fraction autreReduite = fraction.reduire();
      
      return thisReduite.num == autreReduite.num && 
            thisReduite.den == autreReduite.den;
  }

  private Fraction reduire() {
      int pgcd = pgcd(Math.abs(num), Math.abs(den));
      return new Fraction(num / pgcd, den / pgcd);
  }

  private int pgcd(int a, int b) {
      while (b != 0) {
          int temp = b;
          b = a % b;
          a = temp;
      }
      return a;
  }

  @Override
  public int compareTo(Fraction autre) {
      // Comparer en utilisant la multiplication croisée
      int diff = this.num * autre.den - autre.num * this.den;
      return Integer.compare(diff, 0);
  }

  @Override
  public int intValue() {
      return (int) doubleValue();
  }
    
  @Override
  public long longValue() {
      return (long) doubleValue();
  }
  
  @Override
  public float floatValue() {
      return (float) doubleValue();
  }

  @Override
  public String toString() {
    return num + "/" + den;
  }
}
