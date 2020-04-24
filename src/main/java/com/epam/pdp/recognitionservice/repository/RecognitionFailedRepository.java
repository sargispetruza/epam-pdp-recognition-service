package com.epam.pdp.recognitionservice.repository;

import com.epam.pdp.recognitionservice.domain.entity.TextRecognitionFailedResult;
import org.springframework.data.repository.CrudRepository;

public interface RecognitionFailedRepository extends CrudRepository<TextRecognitionFailedResult, Integer> {
    TextRecognitionFailedResult getByRequestId(Integer requestId);
}
