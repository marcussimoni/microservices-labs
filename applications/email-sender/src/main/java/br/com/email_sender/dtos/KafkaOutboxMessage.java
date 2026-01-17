package br.com.email_sender.dtos;


public class KafkaOutboxMessage<T> {

    private T payload;

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

}
