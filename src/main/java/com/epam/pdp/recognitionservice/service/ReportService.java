package com.epam.pdp.recognitionservice.service;

import com.epam.pdp.recognitionservice.exception.RecognitionException;
import com.epam.pdp.recognitionservice.exception.ThereIsNoSuchReportException;

import java.io.IOException;

public interface ReportService<T> {
    T createReport(String requestId) throws ThereIsNoSuchReportException, RecognitionException;
}
