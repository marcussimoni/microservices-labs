package br.com.email_sender.entities;


import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "email_message")
public class EmailMessage {

    @Id
    private ObjectId id;

    @Field(name = "email")
    private String email;

    @Field(name = "user_name")
    private String userName;

    private String book;

    private String shippingId;

    private String city;

    private String state;

    private EmailTemplate template;

    private String status;

    private String publicIdentifier;

    public EmailMessage() {
    }

    public EmailMessage(String book, String publicIdentifier) {
        this.book = book;
        this.publicIdentifier = publicIdentifier;
        this.status = "WAITING_PAYMENT";
    }

    public EmailMessage(String book, String publicIdentifier, String status) {
        this.book = book;
        this.publicIdentifier = publicIdentifier;
        this.status = status;
    }

    public String getPublicIdentifier() {
        return publicIdentifier;
    }

    public void setPublicIdentifier(String publicIdentifier) {
        this.publicIdentifier = publicIdentifier;
    }

    public ObjectId getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public EmailTemplate getTemplate() {
        return template;
    }

    public void setTemplate(EmailTemplate template) {
        this.template = template;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getShippingId() {
        return shippingId;
    }

    public void setShippingId(String shippingId) {
        this.shippingId = shippingId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}