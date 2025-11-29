package com.gsswain.benchmark.serialization.report;

import java.io.Serializable;

public class SerializationReport implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String format;
    private final int size;
    private final SummaryStatistics serializationStats;
    private final SummaryStatistics deserializationStats;

    public SerializationReport(String format, int size, SummaryStatistics serializationStats, SummaryStatistics deserializationStats) {
        this.format = format;
        this.size = size;
        this.serializationStats = serializationStats;
        this.deserializationStats = deserializationStats;
    }

    public String getFormat() {
        return format;
    }

    public int getSize() {
        return size;
    }

    public SummaryStatistics getSerializationStats() {
        return serializationStats;
    }

    public SummaryStatistics getDeserializationStats() {
        return deserializationStats;
    }
}