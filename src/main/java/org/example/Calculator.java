package org.example;

public class Calculator {

    public double add(double a, double b) throws InvalidInputException {
        checkInput(a, b);
        return a + b;
    }

    public double subtract(double a, double b) throws InvalidInputException {
        checkInput(a, b);
        return a - b;
    }

    public double multiply(double a, double b) throws InvalidInputException {
        checkInput(a, b);
        return a * b;
    }

    public double divide(double a, double b) throws InvalidInputException {
        checkInput(a, b);
        if (b == 0) {
            throw new ArithmeticException("Ділення на нуль неможливе");
        }
        return a / b;
    }

    public double sqrt(double a) throws InvalidInputException {
        checkInput(a);
        if (a < 0) {
            throw new InvalidInputException(
                    "Квадратний корінь не можна обчислити з від'ємного числа: " + a);
        }
        return Math.sqrt(a);
    }

    private static void checkInput(double... values) throws InvalidInputException {
        for (double value : values) {
            if (Double.isNaN(value) || Double.isInfinite(value)) {
                throw new InvalidInputException("Некоректне числове значення: " + value);
            }
        }
    }
}