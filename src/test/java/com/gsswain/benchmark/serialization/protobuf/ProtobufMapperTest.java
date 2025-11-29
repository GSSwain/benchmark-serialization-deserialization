package com.gsswain.benchmark.serialization.protobuf;

import com.gsswain.benchmark.serialization.model.pojo.SocialMediaPost;
import com.gsswain.benchmark.util.DataGenerator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProtobufMapperTest {

    private final ProtobufMapper protobufMapper = new ProtobufMapper();

    @Test
    public void testMap() {
        SocialMediaPost pojoPost = DataGenerator.generateSocialMediaPost();
        com.gsswain.benchmark.serialization.model.proto.SocialMediaPost protoPost = protobufMapper.map(pojoPost);

        assertEquals(pojoPost.getPostId(), protoPost.getPostId());
        assertEquals(pojoPost.getAuthorId(), protoPost.getAuthorId());
        assertEquals(pojoPost.getContent(), protoPost.getContent());
        assertEquals(pojoPost.getTimestamp(), protoPost.getTimestamp());
    }
}