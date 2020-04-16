package com.epam.pdp.recognitionservice.service;

import com.epam.pdp.recognitionservice.domain.entity.TextRecognitionFailedResult;
import com.epam.pdp.recognitionservice.domain.entity.TextRecognitionResult;
import com.epam.pdp.recognitionservice.exception.RecognitionException;
import com.epam.pdp.recognitionservice.exception.ThereIsNoSuchReportException;
import com.epam.pdp.recognitionservice.repository.RecognitionFailedRepository;
import com.epam.pdp.recognitionservice.repository.RecognitionRepository;
import org.springframework.stereotype.Service;

@Service
public class TextReportService implements ReportService<TextRecognitionResult>{

    private RecognitionRepository recognitionRepository;
    private RecognitionFailedRepository recognitionFailedRepository;

    public TextReportService(RecognitionRepository recognitionRepository, RecognitionFailedRepository recognitionFailedRepository) {
        this.recognitionRepository = recognitionRepository;
        this.recognitionFailedRepository = recognitionFailedRepository;
    }

    public TextRecognitionResult createReport(String requestId) throws ThereIsNoSuchReportException, RecognitionException {
        TextRecognitionResult result = recognitionRepository.getByRequestId(requestId);
        if(result==null){
            TextRecognitionFailedResult failedResult = recognitionFailedRepository.getByRequestId(requestId);
            if(failedResult==null)
                throw new ThereIsNoSuchReportException();
            else throw new RecognitionException(failedResult.getFailReason());
        }
        return result;
    }
}
