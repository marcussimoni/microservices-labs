package br.com.email_sender.controllers;

import br.com.email_sender.dtos.RabbitMQEmailMessageRequest;
import br.com.email_sender.services.EmailMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static br.com.email_sender.config.RabbitMQConfig.EMAIL_QUEUE;

@Component
public class EmailRabbitMqListener {

    private final EmailMessageService emailMessageService;
    private final Logger log = LoggerFactory.getLogger(EmailRabbitMqListener.class);

    public EmailRabbitMqListener(EmailMessageService emailMessageService) {
        this.emailMessageService = emailMessageService;
    }

    @RabbitListener(queues = EMAIL_QUEUE)
    public void receiveMessage(RabbitMQEmailMessageRequest message) {
        log.info("Receiving message to process");
        emailMessageService.save(message.toEntity(), message.template());
    }
}