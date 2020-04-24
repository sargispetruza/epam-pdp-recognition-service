package com.epam.pdp.recognitionservice.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "results")
@Data
public class TextRecognitionResult  implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private Integer requestId;
    private String recogResut;

    public TextRecognitionResult() {
    }

    public TextRecognitionResult(Integer requestId, String recogResut) {
        this.requestId = requestId;
        this.recogResut = recogResut;
    }
}
