package com.epam.pdp.recognitionservice.listener;

import com.epam.pdp.recognitionservice.domain.entity.TextRecognitionRequest;
import com.epam.pdp.recognitionservice.service.RecognitionService;
import com.epam.pdp.recognitionservice.service.TextRecognitionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@EnableRabbit
@Component
@Slf4j
public class RabbitMqListener {

    private RecognitionService textRecognitionService;

    public RabbitMqListener(TextRecognitionService textRecognitionService) {
        this.textRecognitionService = textRecognitionService;
    }

    @RabbitListener(queues = "textRecognitionQueue")
    public void receiveMessage(final TextRecognitionRequest request) {
        log.info("Received message as specific class: {}", request.toString());
        textRecognitionService.processRecognitionRequest(request);
    }
}