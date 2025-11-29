package com.gsswain.benchmark.serialization.protobuf;

import com.gsswain.benchmark.serialization.model.pojo.SocialMediaPost;
import com.gsswain.benchmark.serialization.report.SerializationReport;
import com.gsswain.benchmark.util.DataGenerator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProtobufSerdeTest {

    @Test
    public void testGetSerializationReport() throws Exception {
        ProtobufSerde serde = new ProtobufSerde();
        SocialMediaPost post = DataGenerator.generateSocialMediaPost();

        SerializationReport report = serde.getSerializationReport(post, 1, 1);

        assertNotNull(report);
        assertEquals("Protobuf", report.getFormat());
        assertEquals(1, report.getSerializationStats().getCount());
        assertEquals(1, report.getDeserializationStats().getCount());
    }
}