package com.epam.pdp.recognitionservice.service;

import com.epam.pdp.recognitionservice.domain.entity.TextRecognitionFailedResult;
import com.epam.pdp.recognitionservice.domain.entity.TextRecognitionResult;
import com.epam.pdp.recognitionservice.exception.TextRecognitionException;
import com.epam.pdp.recognitionservice.exception.ThereIsNoSuchReportException;
import com.epam.pdp.recognitionservice.repository.RecognitionFailedRepository;
import com.epam.pdp.recognitionservice.repository.RecognitionRepository;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    private RecognitionRepository recognitionRepository;
    private RecognitionFailedRepository recognitionFailedRepository;

    public ReportService(RecognitionRepository recognitionRepository, RecognitionFailedRepository recognitionFailedRepository) {
        this.recognitionRepository = recognitionRepository;
        this.recognitionFailedRepository = recognitionFailedRepository;
    }

    public TextRecognitionResult createReport(String requestId) throws ThereIsNoSuchReportException, TextRecognitionException {
        TextRecognitionResult result = recognitionRepository.getByRequestId(requestId);
        if(result==null){
            TextRecognitionFailedResult failedResult = recognitionFailedRepository.getByRequestId(requestId);
            if(failedResult==null)
                throw new ThereIsNoSuchReportException();
            else throw new TextRecognitionException(failedResult.getFailReason());
        }
        return result;
    }
}
