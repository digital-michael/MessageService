package org.cplabs.messageservice.messaging;


import org.springframework.lang.NonNull;

import java.util.List;

interface MessageService {
    Iterable<Message> getAllMessages();
    List<Message> getMessageFor(@NonNull final String alias );
    void save( @NonNull final Message message );
}
