package com.epam.pdp.recognitionservice.service.report;

import com.epam.pdp.recognitionservice.exception.RecognitionException;
import com.epam.pdp.recognitionservice.exception.ThereIsNoSuchReportException;

public interface ReportService<T> {
    T createReport(Integer requestId) throws ThereIsNoSuchReportException, RecognitionException;
}
