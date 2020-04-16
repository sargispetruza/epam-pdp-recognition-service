package com.epam.pdp.recognitionservice.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "failed_results")
@Data
public class TextRecognitionFailedResult  implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String requestId;
    private String failReason;

    public TextRecognitionFailedResult() {
    }

    public TextRecognitionFailedResult(String requestId, String failReason) {
        this.requestId = requestId;
        this.failReason = failReason;
    }
}
