package com.gsswain.benchmark.serialization;

import com.gsswain.benchmark.serialization.model.pojo.SocialMediaPost;
import com.gsswain.benchmark.serialization.report.SerializationReport;
import com.gsswain.benchmark.serialization.report.SerializationReportable;
import com.gsswain.benchmark.util.DataGenerator;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

public class SerdeProcess {

    private static final int WARM_UP_CYCLES = 50_000;
    private static final int MEASUREMENT_CYCLES = 500_000;

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.err.println("Usage: SerdeProcess <serdeClassName>");
            System.exit(1);
        }

        String serdeClassName = args[0];
        SocialMediaPost post = DataGenerator.generateSocialMediaPost();
        SerializationReportable serde = (SerializationReportable) Class.forName(serdeClassName).getDeclaredConstructor().newInstance();
        SerializationReport report = serde.getSerializationReport(post, WARM_UP_CYCLES, MEASUREMENT_CYCLES);

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(report);
            System.out.write(baos.toByteArray());
        }
    }
}