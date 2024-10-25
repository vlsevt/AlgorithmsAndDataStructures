package hwa4.src;

import java.util.Objects;

public class Lfraction implements Comparable<Lfraction> {
   private final long numerator;
   private final long denominator;

   public static void main(String[] args) {
      // Test cases
      Lfraction fraction1 = new Lfraction(3, 8);
      Lfraction fraction2 = new Lfraction(2, 4);

      System.out.println("Fraction 1: " + fraction1);
      System.out.println("Fraction 2: " + fraction2);

      Lfraction sum = fraction1.plus(fraction2);
      System.out.println("Sum: " + sum);

      Lfraction product = fraction1.times(fraction2);
      System.out.println("Product: " + product);

      Lfraction inverse = fraction1.inverse();
      System.out.println("Inverse of Fraction 1: " + inverse);

      Lfraction difference = fraction1.minus(fraction2);
      System.out.println("Difference: " + difference);

      Lfraction division = fraction1.divideBy(fraction2);
      System.out.println("Division: " + division);

      System.out.println("Fraction 1 compared to Fraction 2: " + fraction1.compareTo(fraction2));

      System.out.println("Integer part of Fraction 1: " + fraction1.integerPart());

      Lfraction fractionPart = fraction1.fractionPart();
      System.out.println("Fractional part of Fraction 1: " + fractionPart);

      System.out.println("Double value of Fraction 1: " + fraction1.toDouble());

      Lfraction fromDouble = Lfraction.toLfraction(0.75, 4);
      System.out.println("Fraction from double: " + fromDouble);

      Lfraction fromString = Lfraction.valueOf("5/3");
      System.out.println("Fraction from string: " + fromString);
   }


   public Lfraction(long a, long b) {
      if (b == 0) {
         throw new RuntimeException("Denominator cannot be zero.");
      }

      long gcd = gcd(Math.abs(a), Math.abs(b));
      if (a < 0 && b < 0) {
         gcd = -gcd;
      }
      this.numerator = a / gcd;
      this.denominator = b / gcd;
   }

   public long getNumerator() {
      return numerator;
   }

   public long getDenominator() {
      return denominator;
   }

   @Override
   public String toString() {
      return numerator + "/" + denominator;
   }

   @Override
   public boolean equals(Object m) {
      if (this == m) return true;
      if (m == null || getClass() != m.getClass()) return false;
      return compareTo((Lfraction) m) == 0;
   }

   @Override
   public int compareTo(Lfraction m) {
      long diff = numerator * m.denominator - m.numerator * denominator;
      if (diff < 0) return -1;
      else if (diff > 0) return 1;
      else return 0;
   }


   @Override
   public int hashCode() {
      return Objects.hash(numerator, denominator);
   }

   public Lfraction plus(Lfraction m) {
      long newNumerator = numerator * m.denominator + m.numerator * denominator;
      long newDenominator = denominator * m.denominator;
      return new Lfraction(newNumerator, newDenominator);
   }

   public Lfraction times(Lfraction m) {
      long newNumerator = numerator * m.numerator;
      long newDenominator = denominator * m.denominator;
      return new Lfraction(newNumerator, newDenominator);
   }

   public Lfraction inverse() {
      if (numerator == 0) {
         throw new RuntimeException("Cannot calculate the inverse of zero.");
      }
      if (numerator < 0) {
         return new Lfraction(-denominator, -numerator);
      }
      return new Lfraction(denominator, numerator);
   }






   public Lfraction opposite() {
      return new Lfraction(-numerator, denominator);
   }

   public Lfraction minus(Lfraction m) {
      return plus(m.opposite());
   }

   public Lfraction divideBy (Lfraction m) {
      if(m.numerator == 0){
         throw new RuntimeException("Can't divide by zero");
      }
      return times(m.inverse());
   }


   @Override
   public Object clone() throws CloneNotSupportedException {
      return new Lfraction(this.numerator, this.denominator);
   }

   public long integerPart() {
      return numerator / denominator;
   }

   public Lfraction fractionPart() {
      long newNumerator = numerator % denominator;
      return new Lfraction(newNumerator, denominator);
   }

   public double toDouble() {
      return (double) numerator / denominator;
   }

   public static Lfraction toLfraction(double f, long d) {
      long newNumerator = Math.round(f * d);
      return new Lfraction(newNumerator, d);
   }

   public static Lfraction valueOf(String s) {
      String[] parts = s.split("/");
      if (s.isEmpty() || s.startsWith("/") || s.endsWith("/") || parts.length != 2 || parts[1].isEmpty()) {
         throw new RuntimeException("Invalid string format for Lfraction: " + s);
      }

      try {
         long a = Long.parseLong(parts[0]);
         long b = Long.parseLong(parts[1]);

         if (b == 0) {
            throw new RuntimeException("Denominator cannot be zero in Lfraction: " + s);
         }

         return new Lfraction(a, b);
      } catch (NumberFormatException e) {
         throw new RuntimeException("Invalid numeric values in Lfraction: " + s);
      }
   }



   private static long gcd(long a, long b) {
      if (b == 0) return a;
      return gcd(b, a % b);
   }


   public Lfraction pow(long power) {
      if (power == 0) {
         return new Lfraction(1, 1);
      } else if (power < 0) {
         try {
            return new Lfraction(this.denominator, this.numerator).pow(Math.abs(power));
         } catch (RuntimeException e) {
            throw new IllegalArgumentException(this + " cannot bring to the power of " + power);
         }
      } else if (power % 2 == 0) {
         try {
            Lfraction halfPower = pow(power / 2);
            return halfPower.times(halfPower);
         } catch (RuntimeException e) {
            throw new IllegalArgumentException("Error in power calculation: " + e.getMessage());
         }
      } else {
         try {
            return this.times(pow(power - 1));
         } catch (RuntimeException e) {
            throw new IllegalArgumentException("Error in power calculation: " + e.getMessage());
         }
      }
   }

}
