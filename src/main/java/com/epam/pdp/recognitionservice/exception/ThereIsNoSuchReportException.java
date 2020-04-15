package com.epam.pdp.recognitionservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "The report does not exist or is not yet ready.")
public class ThereIsNoSuchReportException extends IOException {
}
