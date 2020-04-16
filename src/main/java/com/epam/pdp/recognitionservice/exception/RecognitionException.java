package com.epam.pdp.recognitionservice.exception;

import java.io.IOException;

public class RecognitionException extends IOException{
    public RecognitionException(String message) {
        super(message);
    }
}
