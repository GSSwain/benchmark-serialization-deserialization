package com.gsswain.benchmark.util;

import java.time.Duration;
import java.util.concurrent.Callable;

public final class ExecutionTimer {

    private ExecutionTimer() {
    }

    public static class TimedResult<T> {
        private final T result;
        private final Duration duration;

        public TimedResult(T result, Duration duration) {
            this.result = result;
            this.duration = duration;
        }

        public T getResult() {
            return result;
        }

        public Duration getDuration() {
            return duration;
        }
    }

    public static <T> TimedResult<T> measure(Callable<T> callable) throws Exception {
        long startTime = System.nanoTime();
        T result = callable.call();
        long endTime = System.nanoTime();
        return new TimedResult<>(result, Duration.ofNanos(endTime - startTime));
    }
}
