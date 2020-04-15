package com.epam.pdp.recognitionservice.controller;

import com.epam.pdp.recognitionservice.domain.entity.TextRecognitionFailedResult;
import com.epam.pdp.recognitionservice.domain.entity.TextRecognitionResult;
import com.epam.pdp.recognitionservice.exception.TextRecognitionException;
import com.epam.pdp.recognitionservice.exception.ThereIsNoSuchReportException;
import com.epam.pdp.recognitionservice.repository.RecognitionFailedRepository;
import com.epam.pdp.recognitionservice.repository.RecognitionRepository;
import com.epam.pdp.recognitionservice.service.ReportService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reports")
public class TextReportController {

    private ReportService reportService;

    public TextReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @RequestMapping("/{requestId}")
    public TextRecognitionResult getReport(@PathVariable String requestId) throws ThereIsNoSuchReportException, TextRecognitionException {
        return reportService.createReport(requestId);
    }
}
