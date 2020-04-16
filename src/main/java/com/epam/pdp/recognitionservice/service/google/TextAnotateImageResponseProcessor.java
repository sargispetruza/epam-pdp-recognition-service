package com.epam.pdp.recognitionservice.service.google;

import com.epam.pdp.recognitionservice.domain.entity.TextRecognitionResult;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class TextAnotateImageResponseProcessor implements AnnotationImageProcessor<String>{
    @Override
    public String process(List<AnnotateImageResponse> responses) throws IOException {
        for (AnnotateImageResponse res : responses) {
            if (res.hasError()) {
                throw new IOException(res.getError().getMessage());
            }

            // For full list of available annotations, see http://g.co/cloud/vision/docs
            for (EntityAnnotation annotation : res.getTextAnnotationsList()) {
                return annotation.getDescription();
            }
        }
        return "";
    }
}
