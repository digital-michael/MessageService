package org.cplabs.messageservice.messaging.dto;

import org.cplabs.messageservice.messaging.dao.Message;
import org.springframework.lang.Nullable;
import java.util.Date;

/**
 * Defines an immutable instance of data for the presentation layer (IE RESTful calls)
 * Also contains two factor methods for RecordMessage and Message (DB entity)
 *
 * @param date when this message was created (server time)
 * @param from label or name of who this message is from
 * @param to label or name of who this message is to
 * @param message message content
 */
public record RecordMessage(@Nullable String date, String channel, @Nullable String from, @Nullable String to,  @Nullable String message ) {

    // Factory method: to automatically manage the Date field when message record type is created
    // Note: probably not thread safe without synchronization
    synchronized static public RecordMessage createNewMessageFor(@Nullable String from, @Nullable String to, @Nullable String message) {
        return new RecordMessage( new Date().toString(), null, from, to, message );
    }

    synchronized static public RecordMessage createNewMessageFor(@Nullable String channel, @Nullable String from, @Nullable String to, @Nullable String message) {
        return new RecordMessage( new Date().toString(), channel, from, to, message );
    }

    // Factory method: to transfer data from RecordMessage (presentation layer) to Message (repository layer)
    // Notes: type Record not directly supported by JPA
    // Note: probably not thread safe without synchronization
    synchronized static public Message messageFrom (@Nullable Message message, @Nullable RecordMessage recordMessage) {
        message.setDateTimeSent( recordMessage.date().toString() );
        message.setFrom_alias( recordMessage.from() );
        message.setTo_alias( recordMessage.to() );
        message.setMessage(recordMessage.message);
        return message;
    }

    // Factory method: to transfer data from Message (repository layer) to RecordMessage (presentation layer)
    // Notes: type Record not directly supported by JPA
    // Note: probably not thread safe without synchronization
    synchronized static public RecordMessage messageFrom (@Nullable Message message) {
        return new RecordMessage( message.getDateTimeSent(), message.getTo_channel(), message.getFrom_alias(), message.getTo_alias(), message.getMessage() );
    }
}
