package com.epam.pdp.recognitionservice.service;

import com.epam.pdp.recognitionservice.domain.entity.TextRecognitionFailedResult;
import com.epam.pdp.recognitionservice.domain.entity.TextRecognitionRequest;
import com.epam.pdp.recognitionservice.domain.entity.TextRecognitionResult;
import com.epam.pdp.recognitionservice.repository.RecognitionFailedRepository;
import com.epam.pdp.recognitionservice.repository.RecognitionRepository;
import com.epam.pdp.recognitionservice.util.Ping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class TextRecognitionService {

    private RecognitionRepository recognitionRepository;
    private RecognitionFailedRepository recognitionFailedRepository;
    private GoogleRecognition googleRecognition;
    private AmqpTemplate template;
    private DirectExchange directExchange;

    @Value("${google.vision.api.host}")
    private String googleApiHost;
    @Value("${google.vision.api.port}")
    private int googleApiPort;

    public TextRecognitionService(RecognitionRepository recognitionRepository,
                            RecognitionFailedRepository recognitionFailedRepository,
                            GoogleRecognition googleRecognition,
                            AmqpTemplate template, DirectExchange directExchange) {
        this.recognitionRepository = recognitionRepository;
        this.recognitionFailedRepository = recognitionFailedRepository;
        this.googleRecognition = googleRecognition;
        this.template = template;
        this.directExchange = directExchange;
    }
    public void processTextRecognitionRequest(TextRecognitionRequest request){
        if(!Ping.pingHost(googleApiHost, googleApiPort)) {
            log.debug("Ping result of the vision.googleapis.com:443 - "+false);
            template.convertAndSend(directExchange.getName(), "textrecognition", request);
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                log.error(e.getMessage());
                e.printStackTrace();
            }
            return;
        }
        else
            log.debug("Ping result of the vision.googleapis.com:443 - "+true);
        String recognizedText = null;
        try {
            recognizedText = googleRecognition.detectTextURI(request.getImageLink());
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
}
