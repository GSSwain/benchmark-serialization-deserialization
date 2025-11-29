package com.gsswain.benchmark.jmh.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gsswain.benchmark.serialization.model.pojo.SocialMediaPost;
import com.gsswain.benchmark.util.DataGenerator;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

@BenchmarkMode({Mode.AverageTime, Mode.Throughput})
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 10, time = 1)
@Fork(2)
public class JsonSerDeBenchmark {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @State(Scope.Thread)
    public static class BenchmarkState {
        SocialMediaPost socialMediaPost = DataGenerator.generateSocialMediaPost();
        byte[] serializedSocialMediaPost;

        public BenchmarkState() {
            try {
                serializedSocialMediaPost = objectMapper.writeValueAsBytes(socialMediaPost);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Benchmark
    public void jsonSerialization(BenchmarkState state, Blackhole blackhole) throws IOException {
        blackhole.consume(objectMapper.writeValueAsBytes(state.socialMediaPost));
    }

    @Benchmark
    public void jsonDeserialization(BenchmarkState state, Blackhole blackhole) throws IOException {
        blackhole.consume(objectMapper.readValue(state.serializedSocialMediaPost, SocialMediaPost.class));
    }
}