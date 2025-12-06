package com.gsswain.benchmark.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class EnvironmentUtilTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void shouldPrintEnvironmentDetailsToConsole() {
        EnvironmentUtil.printEnvironment();
        String output = outContent.toString();
        assertTrue(output.contains("JVM:"), "Output should contain JVM version");
        assertTrue(output.contains("Available processors:"), "Output should contain processor count");
        assertTrue(output.contains("Max memory:"), "Output should contain max memory");
    }
}