package org.cplabs.messageservice.messaging.dao;


import org.cplabs.messageservice.messaging.dao.Message;
import org.springframework.lang.NonNull;

import java.util.Collection;
import java.util.List;

public interface MessageService {
    Iterable<Message> getAllMessages();
    List<Message> getMessageFor(@NonNull final String alias );
    void save( @NonNull final Message message );

    List<Message> getMessageForChannel(@NonNull final String channel);
}
