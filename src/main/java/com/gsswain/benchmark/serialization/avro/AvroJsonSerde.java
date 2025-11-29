package com.gsswain.benchmark.serialization.avro;

import com.gsswain.benchmark.serialization.model.avro.SocialMediaPost;
import com.gsswain.benchmark.serialization.report.SerializationReport;
import com.gsswain.benchmark.serialization.report.SerializationReportable;
import com.gsswain.benchmark.serialization.report.SummaryStatistics;
import com.gsswain.benchmark.util.ExecutionTimer;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

public class AvroJsonSerde implements SerializationReportable {

    private static final AvroMapper avroMapper = new AvroMapper();

    @Override
    public SerializationReport getSerializationReport(com.gsswain.benchmark.serialization.model.pojo.SocialMediaPost post, int warmUpCycles, int measurementCycles) throws IOException {
        SocialMediaPost avroPost = avroMapper.map(post);
        List<Long> serializationTimes = new ArrayList<>();
        List<Long> deserializationTimes = new ArrayList<>();
        int messageSize = 0;

        for (int i = 0; i < warmUpCycles + measurementCycles; i++) {
            try {
                ExecutionTimer.TimedResult<byte[]> serializationResult = ExecutionTimer.measure(() -> {
                    try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
                        DatumWriter<SocialMediaPost> datumWriter = new SpecificDatumWriter<>(SocialMediaPost.class);
                        Encoder encoder = EncoderFactory.get().jsonEncoder(SocialMediaPost.getClassSchema(), byteArrayOutputStream);
                        datumWriter.write(avroPost, encoder);
                        encoder.flush();
                        return byteArrayOutputStream.toByteArray();
                    }
                });
                byte[] serializedMessage = serializationResult.getResult();

                ExecutionTimer.TimedResult<SocialMediaPost> deserializationResult = ExecutionTimer.measure(() -> {
                    DatumReader<SocialMediaPost> datumReader = new SpecificDatumReader<>(SocialMediaPost.class);
                    Decoder decoder = DecoderFactory.get().jsonDecoder(SocialMediaPost.getClassSchema(), new String(serializedMessage));
                    return datumReader.read(null, decoder);
                });

                if (i >= warmUpCycles) {
                    serializationTimes.add(serializationResult.getDuration().toNanos());
                    deserializationTimes.add(deserializationResult.getDuration().toNanos());
                    messageSize = serializedMessage.length;
                }
            } catch (Exception e) {
                if (e instanceof IOException) {
                    throw (IOException) e;
                } else {
                    throw new RuntimeException("Error during serialization/deserialization", e);
                }
            }
        }

        return new SerializationReport(
                "Avro JSON",
                messageSize,
                new SummaryStatistics(serializationTimes),
                new SummaryStatistics(deserializationTimes));
    }
}