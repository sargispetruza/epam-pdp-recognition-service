package com.epam.pdp.recognitionservice.repository;

import com.epam.pdp.recognitionservice.domain.entity.TextRecognitionResult;
import org.springframework.data.repository.CrudRepository;

public interface RecognitionRepository extends CrudRepository<TextRecognitionResult, Integer> {
    TextRecognitionResult getByRequestId(String requestId);
}
