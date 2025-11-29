package com.gsswain.benchmark.serialization.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gsswain.benchmark.serialization.model.pojo.SocialMediaPost;
import com.gsswain.benchmark.serialization.report.SerializationReport;
import com.gsswain.benchmark.serialization.report.SerializationReportable;
import com.gsswain.benchmark.serialization.report.SummaryStatistics;
import com.gsswain.benchmark.util.ExecutionTimer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonSerde implements SerializationReportable {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public SerializationReport getSerializationReport(SocialMediaPost post, int warmUpCycles, int measurementCycles) throws IOException {
        List<Long> serializationTimes = new ArrayList<>();
        List<Long> deserializationTimes = new ArrayList<>();
        int messageSize = 0;

        for (int i = 0; i < warmUpCycles + measurementCycles; i++) {
            try {
                ExecutionTimer.TimedResult<byte[]> serializationResult = ExecutionTimer.measure(() -> objectMapper.writeValueAsBytes(post));
                byte[] serializedMessage = serializationResult.getResult();

                ExecutionTimer.TimedResult<SocialMediaPost> deserializationResult = ExecutionTimer.measure(() -> objectMapper.readValue(serializedMessage, SocialMediaPost.class));

                if (i >= warmUpCycles) {
                    serializationTimes.add(serializationResult.getDuration().toNanos());
                    deserializationTimes.add(deserializationResult.getDuration().toNanos());
                    messageSize = serializedMessage.length;
                }
            } catch (Exception e) {
                throw new RuntimeException("Error during serialization/deserialization", e);
            }
        }

        return new SerializationReport(
                "JSON",
                messageSize,
                new SummaryStatistics(serializationTimes),
                new SummaryStatistics(deserializationTimes));
    }
}