package com.gsswain.benchmark.serialization.avro;

import com.gsswain.benchmark.serialization.model.pojo.SocialMediaPost;
import com.gsswain.benchmark.serialization.report.SerializationReport;
import com.gsswain.benchmark.util.DataGenerator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AvroJsonSerdeTest {

    @Test
    public void testGetSerializationReport() throws Exception {
        AvroJsonSerde serde = new AvroJsonSerde();
        SocialMediaPost post = DataGenerator.generateSocialMediaPost();

        SerializationReport report = serde.getSerializationReport(post, 1, 1);

        assertNotNull(report);
        assertEquals("Avro JSON", report.getFormat());
        assertEquals(1, report.getSerializationStats().getCount());
        assertEquals(1, report.getDeserializationStats().getCount());
    }
}