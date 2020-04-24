package com.epam.pdp.recognitionservice.listener;

import com.epam.pdp.recognitionservice.domain.dto.TextRecognitionRequestDto;
import com.epam.pdp.recognitionservice.service.recognition.RecognitionService;
import com.epam.pdp.recognitionservice.service.recognition.TextRecognitionService;
import com.epam.pdp.recognitionservice.util.Ping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ConnectException;
import java.util.concurrent.TimeUnit;

@EnableRabbit
@Component
@Slf4j
public class RabbitMqListener {

    private RecognitionService textRecognitionService;

    @Value("${google.vision.api.host}")
    private String googleApiHost;
    @Value("${google.vision.api.port}")
    private int googleApiPort;

    public RabbitMqListener(TextRecognitionService textRecognitionService) {
        this.textRecognitionService = textRecognitionService;
    }

    @RabbitListener(queues = "textRecognitionQueue", ackMode = "MANUAL")
    public void receiveMessage(final TextRecognitionRequestDto request) throws IOException {

        if(!Ping.pingHost(googleApiHost, googleApiPort)) {
            log.debug("Ping result of the "+googleApiHost+" - "+false);
            throw new ConnectException("Can't connect to the "+googleApiHost);
        }
        else {
            log.debug("Ping result of the "+googleApiHost+" - "+true);
        }

        //*********************
        spendTime(5);
        //**********************

        log.info("Received message as specific class: {}", request.toString());
        textRecognitionService.processRecognitionRequest(request);
    }

    /**
     * Emulator useful work.
     * @param seconds to spend
     */
    private void spendTime(int seconds){
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }

}