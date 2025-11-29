package com.gsswain.benchmark.serialization;

import com.gsswain.benchmark.serialization.avro.AvroBinarySerde;
import com.gsswain.benchmark.serialization.avro.AvroJsonSerde;
import com.gsswain.benchmark.serialization.json.JsonSerde;
import com.gsswain.benchmark.serialization.protobuf.ProtobufSerde;
import com.gsswain.benchmark.serialization.report.ReportPrinter;
import com.gsswain.benchmark.serialization.report.SerializationReport;
import java.util.ArrayList;
import java.util.List;

public class SerializationReportGenerator {

    public final static List<String> serdeClassNames = List.of(
            JsonSerde.class.getName(),
            ProtobufSerde.class.getName(),
            AvroJsonSerde.class.getName(),
            AvroBinarySerde.class.getName()
    );

    public static void main(String[] args) throws Exception {
        ReportPrinter.print(generateSerializationReport());
    }

    private static List<SerializationReport> generateSerializationReport() throws Exception {
        List<SerializationReport> serializationReportList = new ArrayList<>();
        for (String serdeClassName : serdeClassNames) {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "java",
                    "-cp",
                    System.getProperty("java.class.path"),
                    SerdeProcess.class.getName(),
                    serdeClassName
            );
            Process process = processBuilder.start();
            process.waitFor();
            byte[] reportBytes = process.getInputStream().readAllBytes();
            if (reportBytes.length > 0) {
                try (java.io.ObjectInputStream ois = new java.io.ObjectInputStream(new java.io.ByteArrayInputStream(reportBytes))) {
                    serializationReportList.add((SerializationReport) ois.readObject());
                }
            }
        }
        return serializationReportList;
    }
}