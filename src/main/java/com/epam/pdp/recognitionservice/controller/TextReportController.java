package com.epam.pdp.recognitionservice.controller;

import com.epam.pdp.recognitionservice.domain.entity.TextRecognitionResult;
import com.epam.pdp.recognitionservice.exception.RecognitionException;
import com.epam.pdp.recognitionservice.exception.ThereIsNoSuchReportException;
import com.epam.pdp.recognitionservice.service.ReportService;
import com.epam.pdp.recognitionservice.service.TextReportService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reports")
public class TextReportController {

    private ReportService<TextRecognitionResult> textReportService;

    public TextReportController(TextReportService textReportService) {
        this.textReportService = textReportService;
    }

    @RequestMapping("/{requestId}")
    public TextRecognitionResult getReport(@PathVariable String requestId) throws ThereIsNoSuchReportException, RecognitionException {
        return textReportService.createReport(requestId);
    }
}
