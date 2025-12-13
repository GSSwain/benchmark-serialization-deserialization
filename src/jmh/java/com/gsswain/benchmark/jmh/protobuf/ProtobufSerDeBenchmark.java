package com.gsswain.benchmark.jmh.protobuf;

import com.gsswain.benchmark.serialization.model.proto.SocialMediaPost;
import com.gsswain.benchmark.serialization.protobuf.ProtobufMapper;
import com.gsswain.benchmark.util.DataGenerator;
import java.io.IOException;
import org.openjdk.jmh.infra.Blackhole;

public class ProtobufSerDeBenchmark {

    private static final ProtobufMapper protobufMapper = new ProtobufMapper();

    public static class BenchmarkState {
        public SocialMediaPost socialMediaPost = protobufMapper.map(DataGenerator.generateSocialMediaPost());
        public byte[] serializedSocialMediaPost;

        public BenchmarkState() {
            serializedSocialMediaPost = socialMediaPost.toByteArray();
        }
    }

    public void protobufSerialization(BenchmarkState state, Blackhole blackhole) {
        blackhole.consume(state.socialMediaPost.toByteArray());
    }

    public void protobufDeserialization(BenchmarkState state, Blackhole blackhole) throws IOException {
        blackhole.consume(SocialMediaPost.parseFrom(state.serializedSocialMediaPost));
    }
}