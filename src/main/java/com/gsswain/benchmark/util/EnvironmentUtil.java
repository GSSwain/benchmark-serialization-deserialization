package com.gsswain.benchmark.util;

public class EnvironmentUtil {

    public static void printEnvironment() {
        System.out.println("JVM: " + System.getProperty("java.version"));
        System.out.println("Available processors: " + Runtime.getRuntime().availableProcessors());
        System.out.println("Max memory: " + Runtime.getRuntime().maxMemory() / 1024 / 1024 + "MB");
    }
}