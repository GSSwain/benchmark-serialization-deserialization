package com.gsswain.benchmark.jmh;

import com.gsswain.benchmark.jmh.avro.AvroBinarySerDeBenchmark;
import com.gsswain.benchmark.jmh.avro.AvroJsonSerDeBenchmark;
import com.gsswain.benchmark.jmh.json.JsonSerDeBenchmark;
import com.gsswain.benchmark.jmh.protobuf.ProtobufSerDeBenchmark;
import com.gsswain.benchmark.util.EnvironmentUtil;
import java.io.IOException;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(java.util.concurrent.TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 10, time = 1)
@Fork(2)
public class JavaSerDeBenchmark {

    @Param({"os-name-placeholder"})
    public String osName;

    @Param({"os-version-placeholder"})
    public String osVersion;

    @Param({"os-rrchitecture-placeholder"})
    public String osArchitecture;

    @Param({"java-runtime-placeholder"})
    public String javaRuntime;

    @Param({"java-vendor-placeholder"})
    public String javaVendor;

    @Param({"1"})
    public Integer processors;

    @Param({"jvm-memory-placeholder"})
    public String jvmMemory;

    @Param({"1"})
    public Integer jmhThreads;

    @Setup(Level.Trial)
    public void setup() {
        EnvironmentUtil.printEnvironment();
    }

    @State(Scope.Benchmark)
    public static class BenchmarkState {
        final AvroBinarySerDeBenchmark.BenchmarkState avroBinaryState = new AvroBinarySerDeBenchmark.BenchmarkState();
        final AvroJsonSerDeBenchmark.BenchmarkState avroJsonState = new AvroJsonSerDeBenchmark.BenchmarkState();
        final ProtobufSerDeBenchmark.BenchmarkState protobufState = new ProtobufSerDeBenchmark.BenchmarkState();
        final JsonSerDeBenchmark.BenchmarkState jsonState = new JsonSerDeBenchmark.BenchmarkState();

        final AvroBinarySerDeBenchmark avroBinarySerDeBenchmark = new AvroBinarySerDeBenchmark();
        final AvroJsonSerDeBenchmark avroJsonSerDeBenchmark = new AvroJsonSerDeBenchmark();
        final ProtobufSerDeBenchmark protobufSerDeBenchmark = new ProtobufSerDeBenchmark();
        final JsonSerDeBenchmark jsonSerDeBenchmark = new JsonSerDeBenchmark();
    }

    @Benchmark
    public void avroBinarySerialization(BenchmarkState state, Blackhole blackhole) throws IOException {
        state.avroBinarySerDeBenchmark.binarySerialization(state.avroBinaryState, blackhole);
    }

    @Benchmark
    public void avroBinaryDeserialization(BenchmarkState state, Blackhole blackhole) throws IOException {
        state.avroBinarySerDeBenchmark.binaryDeserialization(state.avroBinaryState, blackhole);
    }

    @Benchmark
    public void avroJsonSerialization(BenchmarkState state, Blackhole blackhole) throws IOException {
        state.avroJsonSerDeBenchmark.jsonSerialization(state.avroJsonState, blackhole);
    }

    @Benchmark
    public void avroJsonDeserialization(BenchmarkState state, Blackhole blackhole) throws IOException {
        state.avroJsonSerDeBenchmark.jsonDeserialization(state.avroJsonState, blackhole);
    }

    @Benchmark
    public void protobufSerialization(BenchmarkState state, Blackhole blackhole) throws IOException {
        state.protobufSerDeBenchmark.protobufSerialization(state.protobufState, blackhole);
    }

    @Benchmark
    public void protobufDeserialization(BenchmarkState state, Blackhole blackhole) throws IOException {
        state.protobufSerDeBenchmark.protobufDeserialization(state.protobufState, blackhole);
    }

    @Benchmark
    public void jsonSerialization(BenchmarkState state, Blackhole blackhole) throws IOException {
        state.jsonSerDeBenchmark.jsonSerialization(state.jsonState, blackhole);
    }

    @Benchmark
    public void jsonDeserialization(BenchmarkState state, Blackhole blackhole) throws IOException {
        state.jsonSerDeBenchmark.jsonDeserialization(state.jsonState, blackhole);
    }
}