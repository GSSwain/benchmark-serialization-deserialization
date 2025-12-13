package com.gsswain.benchmark.jmh.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gsswain.benchmark.serialization.model.pojo.SocialMediaPost;
import com.gsswain.benchmark.util.DataGenerator;
import java.io.IOException;
import org.openjdk.jmh.infra.Blackhole;

public class JsonSerDeBenchmark {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static class BenchmarkState {
        public SocialMediaPost socialMediaPost = DataGenerator.generateSocialMediaPost();
        public byte[] serializedSocialMediaPost;

        public BenchmarkState() {
            try {
                serializedSocialMediaPost = objectMapper.writeValueAsBytes(socialMediaPost);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void jsonSerialization(BenchmarkState state, Blackhole blackhole) throws IOException {
        blackhole.consume(objectMapper.writeValueAsBytes(state.socialMediaPost));
    }

    public void jsonDeserialization(BenchmarkState state, Blackhole blackhole) throws IOException {
        blackhole.consume(objectMapper.readValue(state.serializedSocialMediaPost, SocialMediaPost.class));
    }
}