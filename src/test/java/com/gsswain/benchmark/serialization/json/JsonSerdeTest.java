package com.gsswain.benchmark.serialization.json;

import com.gsswain.benchmark.serialization.model.pojo.SocialMediaPost;
import com.gsswain.benchmark.serialization.report.SerializationReport;
import com.gsswain.benchmark.util.DataGenerator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JsonSerdeTest {

    @Test
    public void testGetSerializationReport() throws Exception {
        JsonSerde serde = new JsonSerde();
        SocialMediaPost post = DataGenerator.generateSocialMediaPost();

        SerializationReport report = serde.getSerializationReport(post, 1, 1);

        assertNotNull(report);
        assertEquals("JSON", report.getFormat());
        assertEquals(1, report.getSerializationStats().getCount());
        assertEquals(1, report.getDeserializationStats().getCount());
    }
}