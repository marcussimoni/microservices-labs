package br.com.email_sender.controllers;

import br.com.email_sender.dtos.EmailMessageResponse;
import br.com.email_sender.entities.EmailMessage;
import br.com.email_sender.services.EmailMessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("emails")
@Tag(name = "Email Messages", description = "Endpoints for retrieving sent emails")
public class EmailController {

    private final EmailMessageService service;

    public EmailController(EmailMessageService service) {
        this.service = service;
    }

    @Operation(
            summary = "Retrieve sent emails",
            description = "Returns a paginated list of emails that were sent by the system."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of sent emails successfully retrieved",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmailMessageResponse.class))
            )
    })
    @GetMapping
    public Page<EmailMessageResponse> getEmailsSent(
            @ParameterObject Pageable pageable
    ) {
        Page<EmailMessage> page = service.findAll(pageable);
        return page.map(EmailMessageResponse::fromEntity);
    }
}