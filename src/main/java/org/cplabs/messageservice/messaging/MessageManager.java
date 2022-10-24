package org.cplabs.messageservice.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Provides business logic for all messages. IE all Message related actions are managed there.
 *
 * Scope:
 *  - RESTful Message controller only talks to this Message Manager and no other elements except perhaps general utility methods
 *  - Message manager contains all business logic for Messages.
 *  - Message manager orchestrates all external calls including other @Services, @Repository, @Component and other Manager class interactions.
 */
@Service
public class MessageManager {

    private final MessageService messageService;

    // TODO delete
//    // temporary message storage
//    private final Map<String, List<RecordMessage>> aliasRecordMessage = new HashMap<>();
//    private final List<RecordMessage> messages = new ArrayList<>();

    private MessageManager() { messageService = null;}

    @Autowired
    public MessageManager(final MessageService messageService ) {
        this.messageService = messageService;
    }

    //
    // PACKAGE AVAILABLE APIs for business logic
    //

    // Business logic
    List<RecordMessage> getAllMessages() {
        final List<RecordMessage> results = new ArrayList<>();
        messageService.getAllMessages()
                .forEach( m -> results.add( RecordMessage.messageFrom(m)));
        return results;
    }

    // Business logic
    List<RecordMessage> getMessageFor( final String alias ) {
       return messageService.getMessageFor(alias)
               .stream().map( m -> RecordMessage.messageFrom(m))
               .collect(Collectors.toList());
    }

    // Business logic
    void recordNewMessageFor(@NonNull final String from, @NonNull final String to, @NonNull final String message ) {
        messageService.save( new Message( from, to, message ) );
    }
}
