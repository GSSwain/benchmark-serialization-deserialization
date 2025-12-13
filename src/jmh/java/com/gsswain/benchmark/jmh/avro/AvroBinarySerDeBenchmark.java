package com.gsswain.benchmark.jmh.avro;

import com.gsswain.benchmark.serialization.avro.AvroMapper;
import com.gsswain.benchmark.serialization.model.avro.SocialMediaPost;
import com.gsswain.benchmark.util.DataGenerator;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.openjdk.jmh.infra.Blackhole;

public class AvroBinarySerDeBenchmark {

    private static final AvroMapper avroMapper = new AvroMapper();

    public static class BenchmarkState {
        public SocialMediaPost socialMediaPost = avroMapper.map(DataGenerator.generateSocialMediaPost());
        public byte[] serializedSocialMediaPost;

        public BenchmarkState() {
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                DatumWriter<SocialMediaPost> datumWriter = new SpecificDatumWriter<>(SocialMediaPost.class);
                Encoder encoder = EncoderFactory.get().binaryEncoder(byteArrayOutputStream, null);
                datumWriter.write(socialMediaPost, encoder);
                encoder.flush();
                serializedSocialMediaPost = byteArrayOutputStream.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void binarySerialization(BenchmarkState state, Blackhole blackhole) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DatumWriter<SocialMediaPost> datumWriter = new SpecificDatumWriter<>(SocialMediaPost.class);
        Encoder encoder = EncoderFactory.get().binaryEncoder(byteArrayOutputStream, null);
        datumWriter.write(state.socialMediaPost, encoder);
        encoder.flush();
        blackhole.consume(byteArrayOutputStream.toByteArray());
    }

    public void binaryDeserialization(BenchmarkState state, Blackhole blackhole) throws IOException {
        DatumReader<SocialMediaPost> datumReader = new SpecificDatumReader<>(SocialMediaPost.class);
        Decoder decoder = DecoderFactory.get().binaryDecoder(state.serializedSocialMediaPost, null);
        blackhole.consume(datumReader.read(null, decoder));
    }
}