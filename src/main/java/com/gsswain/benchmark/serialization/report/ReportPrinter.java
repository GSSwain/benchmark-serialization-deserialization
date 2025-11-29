package com.gsswain.benchmark.serialization.report;

import java.util.List;

public class ReportPrinter {

    public static void print(List<SerializationReport> serializationReportList) {
        System.out.printf(
                "%n%n%-12s%10s%18s%18s%18s%18s%18s%20s%n",
                "Format",
                "Size",
                "Ser. Min (ns)",
                "Ser. Avg (ns)",
                "Ser. Median (ns)",
                "Deser. Min (ns)",
                "Deser. Avg (ns)",
                "Deser. Median (ns)");
        for (SerializationReport report : serializationReportList) {
            System.out.printf(
                    "%-12s%10d%18d%18.0f%18d%18d%18.0f%20d%n",
                    report.getFormat(),
                    report.getSize(),
                    report.getSerializationStats().getMin(),
                    report.getSerializationStats().getAverage(),
                    report.getSerializationStats().getMedian(),
                    report.getDeserializationStats().getMin(),
                    report.getDeserializationStats().getAverage(),
                    report.getDeserializationStats().getMedian());
        }
    }
}