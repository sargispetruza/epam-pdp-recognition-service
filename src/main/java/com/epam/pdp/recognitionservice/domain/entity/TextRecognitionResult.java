package com.epam.pdp.recognitionservice.domain.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "results")
@Data
public class TextRecognitionResult {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String requestId;
    private String recogResut;

    public TextRecognitionResult() {
    }

    public TextRecognitionResult(String requestId, String recogResut) {
        this.requestId = requestId;
        this.recogResut = recogResut;
    }
}
