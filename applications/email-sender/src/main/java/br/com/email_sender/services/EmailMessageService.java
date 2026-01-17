package br.com.email_sender.services;

import br.com.email_sender.dtos.UserResponseDTO;
import br.com.email_sender.entities.EmailMessage;
import br.com.email_sender.entities.EmailTemplate;
import br.com.email_sender.repositories.EmailMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmailMessageService {

    private final EmailMessageRepository repository;
    private final SendEmailService sendEmailService;
    private final UserManagementService userManagementService;
    private final Logger log = LoggerFactory.getLogger(EmailMessageService.class);

    public EmailMessageService(EmailMessageRepository repository, SendEmailService sendEmailService, UserManagementService userManagementService) {
        this.repository = repository;
        this.sendEmailService = sendEmailService;
        this.userManagementService = userManagementService;
    }

    @Transactional
    public void save(EmailMessage emailMessage, EmailTemplate template) {

        UserResponseDTO user = userManagementService.getUserById(emailMessage.getPublicIdentifier());
        emailMessage.setEmail(user.email());
        emailMessage.setUserName(user.name());
        emailMessage.setCity(user.city());
        emailMessage.setState(user.state());

        log.info("Saving message into database");
        EmailMessage entity = repository.save(emailMessage);

        log.info("Sending email to customer");
        sendEmailService.sendEmail(entity, template);

    }

    @Transactional(readOnly = true)
    public Page<EmailMessage> findAll(Pageable page) {
        return repository.findAll(page);
    }
}