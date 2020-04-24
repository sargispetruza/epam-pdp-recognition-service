package com.epam.pdp.recognitionservice.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class TextRecognitionRequestDto implements Serializable {

    private Integer id;
    private String imageLink;

    public TextRecognitionRequestDto(Integer id, String imageLink) {
        this.id = id;
        this.imageLink = imageLink;
    }
}
