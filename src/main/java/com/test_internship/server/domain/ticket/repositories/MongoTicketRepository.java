package com.test_internship.server.domain.ticket.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.test_internship.server.domain.ticket.entities.Ticket;

public interface MongoTicketRepository extends MongoRepository<Ticket, String> {
    @Aggregation(pipeline = {
            "{ '$sort': { 'open': -1, 'createdAt': 1 } }",
            "{ '$skip': ?0 }",
            "{ '$limit': ?1 }",
            "{ '$project': { 'description': 0 } }"
    })
    List<Ticket> findTicketsForPage(int skip, int limit);
}
