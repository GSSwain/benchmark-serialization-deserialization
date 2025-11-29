package com.gsswain.benchmark.jmh.avro;

import com.gsswain.benchmark.serialization.avro.AvroMapper;
import com.gsswain.benchmark.serialization.model.avro.SocialMediaPost;
import com.gsswain.benchmark.util.DataGenerator;
import com.gsswain.benchmark.util.EnvironmentUtil;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
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
public class AvroJsonSerDeBenchmark {

    private static final AvroMapper avroMapper = new AvroMapper();

    @Setup(Level.Trial)
    public void setup() {
        EnvironmentUtil.printEnvironment();
    }

    @State(Scope.Thread)
    public static class BenchmarkState {
        SocialMediaPost socialMediaPost = avroMapper.map(DataGenerator.generateSocialMediaPost());
        byte[] serializedSocialMediaPost;

        public BenchmarkState() {
            try {
                ByteArrayOutputStream byteArrayOutputStreamJson = new ByteArrayOutputStream();
                DatumWriter<SocialMediaPost> datumWriter = new SpecificDatumWriter<>(SocialMediaPost.class);
                Encoder encoderJson = EncoderFactory.get().jsonEncoder(SocialMediaPost.getClassSchema(), byteArrayOutputStreamJson);
                datumWriter.write(socialMediaPost, encoderJson);
                encoderJson.flush();
                serializedSocialMediaPost = byteArrayOutputStreamJson.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Benchmark
    public void jsonSerialization(BenchmarkState state, Blackhole blackhole) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DatumWriter<SocialMediaPost> datumWriter = new SpecificDatumWriter<>(SocialMediaPost.class);
        Encoder encoder = EncoderFactory.get().jsonEncoder(SocialMediaPost.getClassSchema(), byteArrayOutputStream);
        datumWriter.write(state.socialMediaPost, encoder);
        encoder.flush();
        blackhole.consume(byteArrayOutputStream.toByteArray());
    }

    @Benchmark
    public void jsonDeserialization(BenchmarkState state, Blackhole blackhole) throws IOException {
        DatumReader<SocialMediaPost> datumReader = new SpecificDatumReader<>(SocialMediaPost.class);
        Decoder decoder = DecoderFactory.get().jsonDecoder(SocialMediaPost.getClassSchema(), new String(state.serializedSocialMediaPost));
        blackhole.consume(datumReader.read(null, decoder));
    }
}