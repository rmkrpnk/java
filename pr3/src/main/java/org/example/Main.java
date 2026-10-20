package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Введіть перше число: ");
            double a = Double.parseDouble(scanner.nextLine().trim());
            System.out.print("Введіть друге число: ");
            double b = Double.parseDouble(scanner.nextLine().trim());
            System.out.print("Оберіть операцію (+, -, *, /, sqrt): ");
            String operation = scanner.nextLine().trim();

            double result = switch (operation) {
                case "+" -> Calculator.add(a, b);
                case "-" -> Calculator.subtract(a, b);
                case "*" -> Calculator.multiply(a, b);
                case "/" -> Calculator.divide(a, b);
                case "sqrt" -> Calculator.sqrt(a);
                default -> throw new InvalidInputException("невідома операція " + operation);
            };

            System.out.println("Результат: " + result);
        } catch (NumberFormatException e) {
            System.out.println("Помилка: потрібно ввести число, а не текст.");
        } catch (ArithmeticException e) {
            System.out.println("Помилка: " + e.getMessage() + ".");
        } catch (InvalidInputException e) {
            System.out.println("Помилка вхідних даних: " + e.getMessage() + ".");
        } finally {
            System.out.println("Обробку запиту завершено.");
            scanner.close();
        }
    }
}
