package com.gsswain.benchmark.serialization.report;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class SummaryStatistics implements Serializable {
    private static final long serialVersionUID = 1L;

    private final long min;
    private final long median;
    private final double average;
    private final long count;

    public SummaryStatistics(List<Long> measurements) {
        Collections.sort(measurements);
        this.count = measurements.size();
        this.min = measurements.get(0);
        this.average = measurements.stream().mapToLong(Long::longValue).average().orElse(0.0);

        if (count % 2 == 0) {
            this.median = (measurements.get((int) (count / 2) - 1) + measurements.get((int) (count / 2))) / 2;
        } else {
            this.median = measurements.get((int) (count / 2));
        }
    }

    public long getMin() {
        return min;
    }

    public long getMedian() {
        return median;
    }

    public double getAverage() {
        return average;
    }

    public long getCount() {
        return count;
    }
}