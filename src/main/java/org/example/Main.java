package org.example;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Calculator calculator = new Calculator();
        String again;
        do {
            processRequest(scanner, calculator);
            System.out.print("Продовжити? (т/н): ");
            again = scanner.nextLine().trim().toLowerCase();
        } while (again.equals("т") || again.equals("y") || again.equals("так"));
        System.out.println("Роботу завершено.");
    }

    static void processRequest(Scanner scanner, Calculator calculator) {
        try {
            System.out.print("Введіть перше число: ");
            double a = parseNumber(scanner.nextLine());

            System.out.print("Введіть друге число: ");
            double b = parseNumber(scanner.nextLine());

            System.out.print("Введіть операцію (+, -, *, /, sqrt — корінь з першого числа): ");
            String operation = scanner.nextLine().trim();

            double result;
            switch (operation) {
                case "+" -> result = calculator.add(a, b);
                case "-" -> result = calculator.subtract(a, b);
                case "*" -> result = calculator.multiply(a, b);
                case "/" -> result = calculator.divide(a, b);
                case "sqrt" -> result = calculator.sqrt(a);
                default -> throw new InvalidInputException("Невідома операція: " + operation);
            }
            System.out.println("Результат: " + result);

        } catch (InvalidInputException e) {
            System.out.println("Помилка введення: " + e.getMessage());
        } catch (ArithmeticException e) {
            System.out.println("Помилка обчислення: " + e.getMessage());
        } finally {
            System.out.println("Обробку запиту завершено.");
        }
    }

    public static double parseNumber(String text) throws InvalidInputException {
        try {
            return Double.parseDouble(text.trim().replace(',', '.'));
        } catch (NumberFormatException e) {
            throw new InvalidInputException("\"" + text + "\" — це не число");
        }
    }
}