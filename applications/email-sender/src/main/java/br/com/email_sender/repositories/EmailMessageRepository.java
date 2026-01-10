package br.com.email_sender.repositories;

import br.com.email_sender.entities.EmailMessage;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailMessageRepository extends MongoRepository<EmailMessage, ObjectId> {
}