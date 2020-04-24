package com.epam.pdp.recognitionservice.service.google;

import com.google.cloud.vision.v1.AnnotateImageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class TextAnotateImageResponseProcessor implements AnnotationImageProcessor<String>{

    @Override
    public String process(List<AnnotateImageResponse> responses) throws IOException {
        AnnotateImageResponse imageResponse;
        if(responses.size()>0) {
            imageResponse = responses.get(0);
            if (imageResponse.hasError()) {
                throw new IOException(imageResponse.getError().getMessage());
            }
            if(imageResponse.getTextAnnotationsList().size()>0){
                return imageResponse.getTextAnnotationsList().get(0).getDescription();
            }
        }
        return "";
    }
}
