package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CalculatorTest {

    private final Calculator calculator = new Calculator();

    @Test
    void testAdd() throws InvalidInputException {
        Assertions.assertEquals(5.0, calculator.add(2, 3), 0.0001);
    }

    @Test
    void testSubtract() throws InvalidInputException {
        Assertions.assertEquals(-1.0, calculator.subtract(2, 3), 0.0001);
    }

    @Test
    void testMultiply() throws InvalidInputException {
        Assertions.assertEquals(6.0, calculator.multiply(2, 3), 0.0001);
    }

    @Test
    void testDivide() throws InvalidInputException {
        Assertions.assertEquals(2.5, calculator.divide(5, 2), 0.0001);
    }

    @Test
    void testDivideByZero() {
        ArithmeticException e = Assertions.assertThrows(ArithmeticException.class,
                () -> calculator.divide(10, 0));
        Assertions.assertEquals("Ділення на нуль неможливе", e.getMessage());
    }

    @Test
    void testSqrt() throws InvalidInputException {
        Assertions.assertEquals(4.0, calculator.sqrt(16), 0.0001);
    }

    @Test
    void testSqrtOfNegativeThrowsInvalidInput() {
        Assertions.assertThrows(InvalidInputException.class, () -> calculator.sqrt(-4));
    }

    @Test
    void testNaNThrowsInvalidInput() {
        Assertions.assertThrows(InvalidInputException.class,
                () -> calculator.add(Double.NaN, 1));
    }

    @Test
    void testInfinityThrowsInvalidInput() {
        Assertions.assertThrows(InvalidInputException.class,
                () -> calculator.multiply(Double.POSITIVE_INFINITY, 2));
    }
}