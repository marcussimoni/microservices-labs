package br.com.email_sender.controllers;

import br.com.email_sender.dtos.EmailMessageRequest;
import br.com.email_sender.services.EmailMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static br.com.email_sender.config.RabbitMQConfig.EMAIL_QUEUE;

@Component
public class EmailListener {

    private final EmailMessageService emailMessageService;
    private final Logger log = LoggerFactory.getLogger(EmailListener.class);

    public EmailListener(EmailMessageService emailMessageService) {
        this.emailMessageService = emailMessageService;
    }

    @RabbitListener(queues = EMAIL_QUEUE)
    public void receiveMessage(EmailMessageRequest message) {
        log.info("Receiving message to process");
        emailMessageService.save(message, message.template());
    }
}