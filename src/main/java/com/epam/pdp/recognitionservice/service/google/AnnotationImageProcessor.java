package com.epam.pdp.recognitionservice.service.google;

import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;

import java.io.IOException;
import java.util.List;

public interface AnnotationImageProcessor<T> {
    T process(List<AnnotateImageResponse> responses) throws IOException;
}
