package org.cplabs.messageservice.messaging;

import org.springframework.lang.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity(name = "Messages")
class Message {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    private long messageId;

    private String dateTimeSent;
    private String from_alias;
    private String to_alias;
    private String message;

    public Message() {

    }
    public Message(@NonNull final String from, @NonNull final String to, @NonNull final String message) {
        this.dateTimeSent = new Date().toString();
        this.from_alias = from;
        this.to_alias = to;
        this.message = message;
    }


    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public String getDateTimeSent() {
        return dateTimeSent;
    }

    public void setDateTimeSent(String dateTimeSent) {
        this.dateTimeSent = dateTimeSent;
    }

    public String getFrom_alias() {
        return from_alias;
    }

    public void setFrom_alias(String from_alias) {
        this.from_alias = from_alias;
    }

    public String getTo_alias() {
        return to_alias;
    }

    public void setTo_alias(String to_alias) {
        this.to_alias = to_alias;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
