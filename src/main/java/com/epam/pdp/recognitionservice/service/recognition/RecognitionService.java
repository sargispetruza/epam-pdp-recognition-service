package com.epam.pdp.recognitionservice.service.recognition;

public interface RecognitionService <T>{
    void processRecognitionRequest(T request);
}
