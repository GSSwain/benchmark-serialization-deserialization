package com.gsswain.benchmark.serialization.report;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReportPrinterTest {

    @Test
    public void testPrint() {
        List<SerializationReport> reports = new ArrayList<>();
        List<Long> measurements = new ArrayList<>(List.of(100L, 200L, 300L));
        reports.add(new SerializationReport("Test Format", 123, new SummaryStatistics(measurements), new SummaryStatistics(measurements)));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream old = System.out;
        System.setOut(ps);

        ReportPrinter.print(reports);

        System.out.flush();
        System.setOut(old);

        String output = baos.toString();
        assertTrue(output.contains("Test Format"));
        assertTrue(output.contains("123"));
        assertTrue(output.contains("100"));
        assertTrue(output.contains("200"));
    }
}