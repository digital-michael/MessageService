package org.cplabs.messageservice.messaging;

import org.cplabs.messageservice.messaging.dto.RecordMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Provides RESTful API level access to messaging operations.
 */

@RestController
class MessageController {

    private final MessageManager messageManager;

    // hide this constructor so it can't be used
    private MessageController() { messageManager = null; }

    // constructor-based dependency injection
    @Autowired
    public MessageController( final MessageManager messageManager ) {
        super();
        this.messageManager = messageManager;
    }

    // RESTful endpoint calling business logic
    @GetMapping( value = "/messages/all")
    public ResponseEntity<List<RecordMessage>> getMessages() {
        return new ResponseEntity<>(messageManager.getAllMessages(), HttpStatus.OK);
    }

    // RESTful endpoint calling business logic
    @GetMapping( value = "/messages/{alias}")
    public ResponseEntity<List<RecordMessage>> getMessagesFor(@NonNull @PathVariable String alias) {
        return new ResponseEntity<>(messageManager.getMessageFor(alias), HttpStatus.OK);
    }

    // RESTful endpoint calling business logic
    @PostMapping( value = "/messages/{from}/{to}")
    public ResponseEntity createNewMessageFor(@NonNull @PathVariable String from, @NonNull @PathVariable String to, @NonNull @RequestBody String message ) {
        messageManager.recordNewMessageFor(from, to, message);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // RESTful endpoint calling business logic
    @PostMapping( value = "/messages/{channel}/{from}/{to}")
    public ResponseEntity createNewMessageFor(@NonNull @PathVariable String channel, @NonNull @PathVariable String from, @NonNull @PathVariable String to, @NonNull @RequestBody String message ) {
        messageManager.recordNewMessageFor(channel, from, to, message);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
