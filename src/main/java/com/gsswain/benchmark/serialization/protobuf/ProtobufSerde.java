package com.gsswain.benchmark.serialization.protobuf;

import com.gsswain.benchmark.serialization.model.proto.SocialMediaPost;
import com.gsswain.benchmark.serialization.report.SerializationReport;
import com.gsswain.benchmark.serialization.report.SerializationReportable;
import com.gsswain.benchmark.serialization.report.SummaryStatistics;
import com.gsswain.benchmark.util.ExecutionTimer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProtobufSerde implements SerializationReportable {

    private static final ProtobufMapper protobufMapper = new ProtobufMapper();

    @Override
    public SerializationReport getSerializationReport(com.gsswain.benchmark.serialization.model.pojo.SocialMediaPost post, int warmUpCycles, int measurementCycles) throws IOException {
        SocialMediaPost protoPost = protobufMapper.map(post);
        List<Long> serializationTimes = new ArrayList<>();
        List<Long> deserializationTimes = new ArrayList<>();
        int messageSize = 0;

        for (int i = 0; i < warmUpCycles + measurementCycles; i++) {
            try {
                ExecutionTimer.TimedResult<byte[]> serializationResult = ExecutionTimer.measure(protoPost::toByteArray);
                byte[] serializedMessage = serializationResult.getResult();

                ExecutionTimer.TimedResult<SocialMediaPost> deserializationResult =
                        ExecutionTimer.measure(() -> SocialMediaPost.parseFrom(serializedMessage));

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
                "Protobuf",
                messageSize,
                new SummaryStatistics(serializationTimes),
                new SummaryStatistics(deserializationTimes));
    }
}