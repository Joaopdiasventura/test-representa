package com.test_internship.server.domain.ticket;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.test_internship.server.common.MessageResponse;
import com.test_internship.server.domain.ticket.dto.CreateTicketDto;
import com.test_internship.server.domain.ticket.dto.UpdateTicketDto;
import com.test_internship.server.domain.ticket.entities.Ticket;
import com.test_internship.server.domain.ticket.repositories.MongoTicketRepository;
import com.test_internship.server.domain.user.UserService;
import com.test_internship.server.domain.user.entities.User;

@Service
public class TicketService {
    @Autowired
    private MongoTicketRepository ticketRepository;

    @Autowired
    private UserService userService;

    public MessageResponse create(CreateTicketDto createTicketDto) {
        User user = this.findUser(createTicketDto.getUser());
        Ticket ticket = createTicketDto.toTicket();
        ticket.addUser(user);
        this.ticketRepository.save(ticket);
        return new MessageResponse("Chamado criado com sucesso");
    }

    public Ticket findById(String id) {
        Optional<Ticket> optionalTicket = this.ticketRepository.findById(id);
        if (optionalTicket.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Chamado não encontrado");
        }
        Ticket ticket = optionalTicket.get();
        ticket.getUser().removePassword();
        return ticket;
    }

    public List<Ticket> findTicketsByPage(int page) {
        List<Ticket> tickets = this.ticketRepository.findTicketsForPage(page, 10);

        for (Ticket ticket : tickets)
            ticket.getUser().removePassword();

        return tickets;
    }

    public MessageResponse update(String id, UpdateTicketDto updateTicketDto) {
        Ticket ticket = this.findById(id);

        if (ticket.getOpen() != updateTicketDto.getOpen()) {
            ticket.setOpen(updateTicketDto.getOpen());
        }

        this.ticketRepository.save(ticket);
        return new MessageResponse("Chamado alterado com sucesso");
    }

    public MessageResponse delete(String id) {
        this.findById(id);
        this.ticketRepository.deleteById(id);
        return new MessageResponse("Chamado deletado com sucesso");
    }

    private User findUser(String user) {
        return this.userService.findById(user);
    }
}
