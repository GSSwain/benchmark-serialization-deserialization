package com.gsswain.benchmark.serialization.report;

import com.gsswain.benchmark.serialization.model.pojo.SocialMediaPost;
import java.io.IOException;

public interface SerializationReportable {
    SerializationReport getSerializationReport(SocialMediaPost post, int warmUpCycles, int measurementCycles) throws IOException, ClassNotFoundException;
}