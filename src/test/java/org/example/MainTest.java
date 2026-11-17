package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

class MainTest {

    @Test
    void testParseValidNumber() throws InvalidInputException {
        Assertions.assertEquals(12.5, Main.parseNumber("12.5"), 0.0001);
    }

    @Test
    void testParseNumberWithComma() throws InvalidInputException {
        Assertions.assertEquals(12.5, Main.parseNumber("12,5"), 0.0001);
    }

    @Test
    void testParseStringInsteadOfNumber() {
        Assertions.assertThrows(InvalidInputException.class, () -> Main.parseNumber("abc"));
    }

    @Test
    void testParseEmptyString() {
        Assertions.assertThrows(InvalidInputException.class, () -> Main.parseNumber(""));
    }

    @Test
    void testProcessRequestDoesNotCrashOnBadInput() {
        Scanner scanner = new Scanner("abc\n5\n+\n");
        Assertions.assertDoesNotThrow(() -> Main.processRequest(scanner, new Calculator()));
    }

    @Test
    void testProcessRequestDivisionByZeroDoesNotCrash() {
        Scanner scanner = new Scanner("10\n0\n/\n");
        Assertions.assertDoesNotThrow(() -> Main.processRequest(scanner, new Calculator()));
    }
}