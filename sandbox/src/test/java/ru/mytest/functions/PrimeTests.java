package ru.mytest.functions;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PrimeTests {

  @Test
  public void testPrime() {

    Assert.assertTrue(Primes.isPrimeFast(Integer.MAX_VALUE));
    System.out.println(Integer.MAX_VALUE);
  }

  @Test
  public void testNonPrime() {

    Assert.assertFalse(Primes.isPrime(Integer.MAX_VALUE - 2));
    System.out.println(Integer.MAX_VALUE);
  }

  @Test (enabled = false)
  public void testPrimeWhile() {

    Assert.assertTrue(Primes.isPrimeWhile(Integer.MAX_VALUE));
    System.out.println(Integer.MAX_VALUE);
  }

  @Test (enabled = false)
  public void testPrimeLong() {
    long n = Integer.MAX_VALUE;
    Assert.assertTrue(Primes.isPrime(n));
    System.out.println(n);
  }
}
