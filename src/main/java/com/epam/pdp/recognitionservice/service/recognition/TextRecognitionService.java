package com.epam.pdp.recognitionservice.service.recognition;

import com.epam.pdp.recognitionservice.domain.dto.TextRecognitionRequestDto;
import com.epam.pdp.recognitionservice.domain.entity.TextRecognitionFailedResult;
import com.epam.pdp.recognitionservice.domain.entity.TextRecognitionResult;
import com.epam.pdp.recognitionservice.repository.RecognitionFailedRepository;
import com.epam.pdp.recognitionservice.repository.RecognitionRepository;
import com.epam.pdp.recognitionservice.service.google.AnnotationImageProcessor;
import com.epam.pdp.recognitionservice.service.google.GoogleRecognition;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.Feature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class TextRecognitionService implements RecognitionService<TextRecognitionRequestDto>{

    private RecognitionRepository recognitionRepository;
    private RecognitionFailedRepository recognitionFailedRepository;
    private GoogleRecognition googleRecognition;
    private AnnotationImageProcessor<String> annotationImageProcessor;

    public TextRecognitionService(RecognitionRepository recognitionRepository,
                                  RecognitionFailedRepository recognitionFailedRepository,
                                  GoogleRecognition googleRecognition,
                                  AnnotationImageProcessor<String> annotationImageProcessor) {
        this.recognitionRepository = recognitionRepository;
        this.recognitionFailedRepository = recognitionFailedRepository;
        this.googleRecognition = googleRecognition;
        this.annotationImageProcessor = annotationImageProcessor;
    }

    @Override
    public void processRecognitionRequest(TextRecognitionRequestDto request){

        String recognizedText = null;
        try {
            List<AnnotateImageResponse> annotateImageResponses = googleRecognition.detectTextURI(request.getImageLink(), Feature.Type.TEXT_DETECTION);
            recognizedText = annotationImageProcessor.process(annotateImageResponses);
        } catch (IOException e) {
            log.error("Can't process request with id="+request.getId()+". Reason: "+e.getMessage());
            TextRecognitionFailedResult textRecognitionFailedResult = new TextRecognitionFailedResult(request.getId(),e.getMessage());
            recognitionFailedRepository.save(textRecognitionFailedResult);
            return;
        }
        log.info("Recognized Text: {}", recognizedText);
        TextRecognitionResult textRecognitionResult = new TextRecognitionResult(request.getId(),recognizedText);
        recognitionRepository.save(textRecognitionResult);
    }

}
