package com.epam.pdp.recognitionservice.service;

import com.epam.pdp.recognitionservice.domain.entity.TextRecognitionFailedResult;
import com.epam.pdp.recognitionservice.domain.entity.TextRecognitionRequest;
import com.epam.pdp.recognitionservice.domain.entity.TextRecognitionResult;
import com.epam.pdp.recognitionservice.repository.RecognitionFailedRepository;
import com.epam.pdp.recognitionservice.repository.RecognitionRepository;
import com.epam.pdp.recognitionservice.service.google.AnnotationImageProcessor;
import com.epam.pdp.recognitionservice.service.google.GoogleRecognition;
import com.epam.pdp.recognitionservice.util.Ping;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.Feature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class TextRecognitionService implements RecognitionService<TextRecognitionRequest>{

    private RecognitionRepository recognitionRepository;
    private RecognitionFailedRepository recognitionFailedRepository;
    private GoogleRecognition googleRecognition;
    private AmqpTemplate template;
    private DirectExchange directExchange;
    private AnnotationImageProcessor<String> annotationImageProcessor;

    @Value("${google.vision.api.host}")
    private String googleApiHost;
    @Value("${google.vision.api.port}")
    private int googleApiPort;

    public TextRecognitionService(RecognitionRepository recognitionRepository,
                                  RecognitionFailedRepository recognitionFailedRepository,
                                  GoogleRecognition googleRecognition,
                                  AmqpTemplate template, DirectExchange directExchange, AnnotationImageProcessor<String> annotationImageProcessor) {
        this.recognitionRepository = recognitionRepository;
        this.recognitionFailedRepository = recognitionFailedRepository;
        this.googleRecognition = googleRecognition;
        this.template = template;
        this.directExchange = directExchange;
        this.annotationImageProcessor = annotationImageProcessor;
    }

    @Override
    public void processRecognitionRequest(TextRecognitionRequest request){
        if(!Ping.pingHost(googleApiHost, googleApiPort)) {
            log.debug("Ping result of the vision.googleapis.com:443 - "+false);
            template.convertAndSend(directExchange.getName(), "textrecognition", request);
            //*********************
            spendTime(5);
            //**********************
            return;
        }
        else {
            log.debug("Ping result of the vision.googleapis.com:443 - " + true);
        }

        //*********************
        spendTime(5);
        //**********************

        String recognizedText = null;
        try {
            List<AnnotateImageResponse> annotateImageResponses = googleRecognition.detectTextURI(request.getImageLink(), Feature.Type.TEXT_DETECTION);
            recognizedText = annotationImageProcessor.process(annotateImageResponses);
        } catch (IOException e) {
            log.error("Can't process request with id="+request.getRequestId()+". Reason: "+e.getMessage());
            TextRecognitionFailedResult textRecognitionFailedResult = new TextRecognitionFailedResult(request.getRequestId(),e.getMessage());
            recognitionFailedRepository.save(textRecognitionFailedResult);
            return;
        }
        log.info("Recognized Text: {}", recognizedText);
        TextRecognitionResult textRecognitionResult = new TextRecognitionResult(request.getRequestId(),recognizedText);
        recognitionRepository.save(textRecognitionResult);
    }

    /**
     * Emulator useful work.
     * @param seconds to spend
     */
    private void spendTime(int seconds){
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
