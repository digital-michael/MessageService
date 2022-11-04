package org.cplabs.messageservice.messaging.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

@Repository
interface MessageRepository extends PagingAndSortingRepository<Message, Long>, QueryByExampleExecutor<Message> {

}