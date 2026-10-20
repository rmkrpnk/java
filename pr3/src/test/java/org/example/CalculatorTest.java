package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CalculatorTest {

    @Test
    public void testOperations() throws InvalidInputException {
        Assertions.assertEquals(5.0, Calculator.add(2, 3));
        Assertions.assertEquals(-1.0, Calculator.subtract(2, 3));
        Assertions.assertEquals(6.0, Calculator.multiply(2, 3));
        Assertions.assertEquals(2.5, Calculator.divide(5, 2));
        Assertions.assertEquals(3.0, Calculator.sqrt(9));
    }

    @Test
    public void testDivisionByZero() {
        Assertions.assertThrows(ArithmeticException.class, () -> Calculator.divide(5, 0));
    }

    @Test
    public void testSqrtOfNegativeNumber() {
        Assertions.assertThrows(InvalidInputException.class, () -> Calculator.sqrt(-4));
    }

    @Test
    public void testInvalidValue() {
        Assertions.assertThrows(InvalidInputException.class, () -> Calculator.add(Double.NaN, 1));
    }

    @Test
    public void testTextInsteadOfNumber() {
        Assertions.assertThrows(NumberFormatException.class, () -> Double.parseDouble("abc"));
    }
}
