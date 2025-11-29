package com.gsswain.benchmark.jmh.protobuf;

import com.gsswain.benchmark.serialization.model.proto.SocialMediaPost;
import com.gsswain.benchmark.serialization.protobuf.ProtobufMapper;
import com.gsswain.benchmark.util.DataGenerator;
import com.gsswain.benchmark.util.EnvironmentUtil;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

@BenchmarkMode({Mode.AverageTime, Mode.Throughput})
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 10, time = 1)
@Fork(2)
public class ProtobufSerDeBenchmark {

    private static final ProtobufMapper protobufMapper = new ProtobufMapper();

    @Setup(Level.Trial)
    public void setup() {
        EnvironmentUtil.printEnvironment();
    }

    @State(Scope.Thread)
    public static class BenchmarkState {
        SocialMediaPost socialMediaPost = protobufMapper.map(DataGenerator.generateSocialMediaPost());
        byte[] serializedSocialMediaPost;

        public BenchmarkState() {
            serializedSocialMediaPost = socialMediaPost.toByteArray();
        }
    }

    @Benchmark
    public void protobufSerialization(BenchmarkState state, Blackhole blackhole) {
        blackhole.consume(state.socialMediaPost.toByteArray());
    }

    @Benchmark
    public void protobufDeserialization(BenchmarkState state, Blackhole blackhole) throws IOException {
        blackhole.consume(SocialMediaPost.parseFrom(state.serializedSocialMediaPost));
    }
}