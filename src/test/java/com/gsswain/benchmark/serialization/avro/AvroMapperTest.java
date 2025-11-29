package com.gsswain.benchmark.serialization.avro;

import com.gsswain.benchmark.serialization.model.pojo.SocialMediaPost;
import com.gsswain.benchmark.util.DataGenerator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AvroMapperTest {

    private final AvroMapper avroMapper = new AvroMapper();

    @Test
    public void testMap() {
        SocialMediaPost pojoPost = DataGenerator.generateSocialMediaPost();
        com.gsswain.benchmark.serialization.model.avro.SocialMediaPost avroPost = avroMapper.map(pojoPost);

        assertEquals(pojoPost.getPostId(), avroPost.getPostId().toString());
        assertEquals(pojoPost.getAuthorId(), avroPost.getAuthorId().toString());
        assertEquals(pojoPost.getContent(), avroPost.getContent().toString());
        assertEquals(pojoPost.getTimestamp(), avroPost.getTimestamp());
    }
}