package org.example;

public abstract class Calculator {

    public static  double add(double a, double b) throws InvalidInputException {
        validate(a);
        validate(b);
        return a + b;
    }

    public static  double subtract(double a, double b) throws InvalidInputException {
        validate(a);
        validate(b);
        return a - b;
    }

    public static double multiply(double a, double b) throws InvalidInputException {
        validate(a);
        validate(b);
        return a * b;
    }

    public static double divide(double a, double b) throws InvalidInputException {
        validate(a);
        validate(b);
        if (b == 0) {
            throw new ArithmeticException("ділення на нуль неможливе");
        }
        return a / b;
    }

    public static double sqrt(double a) throws InvalidInputException {
        validate(a);
        if (a < 0) {
            throw new InvalidInputException("квадратний корінь з від'ємного числа не існує");
        }
        return Math.sqrt(a);
    }

    private static void validate(double value) throws InvalidInputException {
        if (Double.isNaN(value) || Double.isInfinite(value)) {
            throw new InvalidInputException("некоректне значення " + value);
        }
    }
}
