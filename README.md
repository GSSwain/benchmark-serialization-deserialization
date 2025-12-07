# Serialization Comparison Benchmark

This project provides a comprehensive benchmark for comparing the performance of different serialization frameworks using Java. It measures the serialization and deserialization speed of the following formats:

*   **JSON (Jackson):** The popular JSON format, using the Jackson library.
*   **Protocol Buffers (Protobuf):** Google's binary serialization format.
*   **Avro (JSON and Binary):** Apache Avro, in both its JSON and binary encodings.

## Project Structure

The project follows a standard Gradle Java project structure, with some specific directories for schema definitions and benchmarks:

```
.
├── build.gradle                  // Gradle build file
├── settings.gradle               // Gradle settings file
├── README.md                     // Project README
└── src
    ├── main
    │   ├── avro                  // Avro schema definitions
    │   │   └── SocialMediaPost.avsc
    │   ├── java                  // Main Java source code
    │   │   └── com/gsswain/benchmark/serialization
    │   │       ├── avro          // Avro Serde and Mapper
    │   │       ├── json          // JSON Serde
    │   │       ├── model         // POJO model definitions
    │   │       ├── protobuf      // Protobuf Serde and Mapper
    │   │       ├── report        // Reporting classes
    │   │       └── util          // Utility classes (e.g., DataGenerator)
    │   └── proto                 // Protobuf schema definitions
    │       └── SocialMediaPost.proto
    ├── jmh                       // JMH benchmarks
    │   └── java
    │       └── com/gsswain/benchmark/jmh
    │           ├── avro          // Avro JMH benchmarks
    │           ├── json          // JSON JMH benchmarks
    │           └── protobuf      // Protobuf JMH benchmarks
    └── test                      // Unit tests
        └── java
```

## Sample Object

The benchmarks use a `SocialMediaPost` object, which has the following structure:

```json
{
  "postId": "some-random-string",
  "authorId": "some-random-string",
  "content": "some-random-string",
  "timestamp": 1678886400000,
  "mediaAttachments": [
    {
      "type": "IMAGE",
      "url": "http://example.com/some-random-image.jpg"
    },
    {
      "type": "VIDEO",
      "url": "http://example.com/some-random-video.mp4"
    }
  ],
  "likes": [
    "some-random-string",
    "some-random-string",
    "some-random-string",
    "some-random-string",
    "some-random-string",
    "some-random-string",
    "some-random-string",
    "some-random-string",
    "some-random-string",
    "some-random-string"
  ],
  "comments": [
    {
      "commentId": "some-random-string",
      "commenterId": "some-random-string",
      "commentText": "some-random-string",
      "timestamp": 1678886400000
    },
    {
      "commentId": "some-random-string",
      "commenterId": "some-random-string",
      "commentText": "some-random-string",
      "timestamp": 1678886400000
    },
    {
      "commentId": "some-random-string",
      "commenterId": "some-random-string",
      "commentText": "some-random-string",
      "timestamp": 1678886400000
    },
    {
      "commentId": "some-random-string",
      "commenterId": "some-random-string",
      "commentText": "some-random-string",
      "timestamp": 1678886400000
    },
    {
      "commentId": "some-random-string",
      "commenterId": "some-random-string",
      "commentText": "some-random-string",
      "timestamp": 1678886400000
    }
  ]
}
```

## Building the Project

To build the project, run the following command:

```bash
./gradlew clean build
```

This will compile the code, run the unit tests, and generate the code coverage report.

## CI/CD

This project uses GitHub Actions to automatically run the JMH benchmarks on every push to the `main` branch. The workflow is defined in `.github/workflows/benchmark.yml` and runs the benchmarks on JDK 17, 21 and 25.

## Live JMH Report

This project uses `jmh.morethan.io`, a free online tool for visualizing JMH benchmark results. It reads data directly from a GitHub Gist, allowing for easy sharing and interactive analysis of benchmark data.

The GitHub Actions workflow for this project is configured to automatically update a public Gist with the latest benchmark results upon every push to the `main` branch. The workflow runs the benchmarks across various configurations (different JDK versions) and uploads each result to the Gist.

You can view the live, interactive report of the latest benchmark run here:

[View Live JMH Report](https://jmh.morethan.io/?gist=c724d78f175ebf8daa62761a138ff9ff)

## Running the Benchmarks

### Performance Benchmarks

The project uses the Java Microbenchmark Harness (JMH) to provide accurate and reliable performance measurements. To run the benchmarks, execute the following command:

```bash
./gradlew jmh -PjavaVersion=25 -Pjmh.threads=1
```

The results will be printed to the console, showing the average time for each serialization and deserialization operation.

### JMH Benchmark Results

Here are the results from running the JMH benchmarks on JDK Zulu 25 on Apple M3 Pro with 18GB RAM

```
Benchmark                                                         Mode  Cnt     Score    Error   Units
c.g.b.j.avro.AvroBinarySerDeBenchmark.binaryDeserialization      thrpt   20     0.001 ±  0.001  ops/ns
c.g.b.j.avro.AvroBinarySerDeBenchmark.binarySerialization        thrpt   20     0.002 ±  0.001  ops/ns
c.g.b.j.avro.AvroJsonSerDeBenchmark.jsonDeserialization          thrpt   20    ≈ 10⁻⁴           ops/ns
c.g.b.j.avro.AvroJsonSerDeBenchmark.jsonSerialization            thrpt   20    ≈ 10⁻⁴           ops/ns
c.g.b.j.json.JsonSerDeBenchmark.jsonDeserialization              thrpt   20     0.001 ±  0.001  ops/ns
c.g.b.j.json.JsonSerDeBenchmark.jsonSerialization                thrpt   20     0.001 ±  0.001  ops/ns
c.g.b.j.protobuf.ProtobufSerDeBenchmark.protobufDeserialization  thrpt   20     0.001 ±  0.001  ops/ns
c.g.b.j.protobuf.ProtobufSerDeBenchmark.protobufSerialization    thrpt   20     0.002 ±  0.001  ops/ns
c.g.b.j.avro.AvroBinarySerDeBenchmark.binaryDeserialization       avgt   20  1495.334 ±  7.127   ns/op
c.g.b.j.avro.AvroBinarySerDeBenchmark.binarySerialization         avgt   20   586.868 ±  4.986   ns/op
c.g.b.j.avro.AvroJsonSerDeBenchmark.jsonDeserialization           avgt   20  5915.887 ± 14.912   ns/op
c.g.b.j.avro.AvroJsonSerDeBenchmark.jsonSerialization             avgt   20  4358.061 ±  8.229   ns/op
c.g.b.j.json.JsonSerDeBenchmark.jsonDeserialization               avgt   20  1658.018 ± 20.257   ns/op
c.g.b.j.json.JsonSerDeBenchmark.jsonSerialization                 avgt   20  1148.056 ±  5.189   ns/op
c.g.b.j.protobuf.ProtobufSerDeBenchmark.protobufDeserialization   avgt   20   714.819 ±  7.636   ns/op
c.g.b.j.protobuf.ProtobufSerDeBenchmark.protobufSerialization     avgt   20   438.998 ±  1.370   ns/op
```

### JMH Report

The `jmhReport` task generates a detailed HTML report of the JMH benchmark results. To generate this report, run:

```bash
./gradlew jmhReport
```

You can find the generated report at `build/reports/jmh/index.html`. This report provides a more interactive and detailed view of the benchmark results, including charts and statistical analysis.

### Serialization Report with Payload Size (Naive Approach without using JMH)

To generate a report that includes the size of the serialized payload and the median performance, run the main application:

```bash
./gradlew run
```

Here is a sample output from the serialization report on JDK Zulu 25 on Apple M3 Pro with 18GB RAM:

```
Format            Size     Ser. Min (ns)     Ser. Avg (ns)  Ser. Median (ns)   Deser. Min (ns)   Deser. Avg (ns)  Deser. Median (ns)
JSON              1223              1000              1299              1250              1375              1734                1625
Protobuf           764               375               494               459               583               825                 709
Avro JSON         1223              3250              4479              4250              4541              5576                5375
Avro Binary        721               458               717               583              1125              1435                1333
```

This will execute each serialization test in a separate process and print a consolidated report to the console.

## Interpreting the Results

The serialization report provides the following metrics:

*   **Format:** The serialization format being tested.
*   **Size:** The size of the serialized payload in bytes.
*   **Ser. Min (ns):** The minimum time taken to serialize the object, in nanoseconds.
*   **Ser. Avg (ns):** The average time taken to serialize the object, in nanoseconds.
*   **Ser. Median (ns):** The median time taken to serialize the object, in nanoseconds.
*   **Deser. Min (ns):** The minimum time taken to deserialize the object, in nanoseconds.
*   **Deser. Avg (ns):** The average time taken to deserialize the object, in nanoseconds.
*   **Deser. Median (ns):** The median time taken to deserialize the object, in nanoseconds.
