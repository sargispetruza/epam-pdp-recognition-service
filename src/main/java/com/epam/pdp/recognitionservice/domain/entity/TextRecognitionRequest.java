package com.epam.pdp.recognitionservice.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "requests")
@Data
public class TextRecognitionRequest implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String requestId;
    private String imageLink;
    //TODO: додати дату

}
