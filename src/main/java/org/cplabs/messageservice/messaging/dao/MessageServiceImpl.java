package org.cplabs.messageservice.messaging.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
class MessageServiceImpl implements MessageService {

    private EntityManager em;
    private final MessageRepository messageRepository;

    private MessageServiceImpl() {messageRepository = null;}

    @Autowired
    MessageServiceImpl( final MessageRepository messageRepository, final EntityManager em ) {
        this.messageRepository = messageRepository;
        this.em = em;
    }

    // Repository logic
    public Iterable<Message> getAllMessages() { return messageRepository.findAll(); }


    // Repository logic
    public List<Message> getMessageFor( @NonNull final String alias ) {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Message> cq = cb.createQuery(Message.class);

        final Root<Message> message = cq.from( Message.class );
        final List<Predicate> predicates = new ArrayList<>();
        predicates.add( cb.equal(message.get("to_alias"), alias));
        cq.where(predicates.toArray(new Predicate[0]));

        return em.createQuery(cq).getResultList();
    }

    public List<Message> getMessageForChannel(@NonNull final String channel) {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Message> cq = cb.createQuery(Message.class);

        final Root<Message> message = cq.from( Message.class );
        final List<Predicate> predicates = new ArrayList<>();
        predicates.add( cb.equal(message.get("to_channel"), channel));
        cq.where(predicates.toArray(new Predicate[0]));

        return em.createQuery(cq).getResultList();
    }

    // Repository logic
    public void save( @NonNull final Message message ) {
        messageRepository.save( message );
    }
}
