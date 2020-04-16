package com.epam.pdp.recognitionservice.service;

import com.epam.pdp.recognitionservice.domain.entity.TextRecognitionRequest;

public interface RecognitionService <T>{
    void processRecognitionRequest(T request);
}
