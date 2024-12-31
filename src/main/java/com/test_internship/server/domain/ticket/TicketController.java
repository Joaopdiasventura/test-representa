package com.test_internship.server.domain.ticket;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test_internship.server.common.MessageResponse;
import com.test_internship.server.domain.ticket.dto.CreateTicketDto;
import com.test_internship.server.domain.ticket.dto.UpdateTicketDto;
import com.test_internship.server.domain.ticket.entities.Ticket;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/ticket")
@Validated
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @PostMapping()
    public MessageResponse create(@Valid @RequestBody CreateTicketDto createTicketDto) {
        return this.ticketService.create(createTicketDto);
    }

    @GetMapping("{id}")
    public Ticket findById(@PathVariable("id") String id) {
        return this.ticketService.findById(id);
    }

    @GetMapping("findTicketsByPage/{page}")
    public List<Ticket> findTicketsByPage(@PathVariable("page") int page) {
        return this.ticketService.findTicketsByPage(page < 0 ? 0 : page);
    }

    @PatchMapping("{id}")
    public MessageResponse update(@PathVariable("id") String id, @Valid @RequestBody UpdateTicketDto updateTicketDto) {
        return this.ticketService.update(id, updateTicketDto);
    }

    @DeleteMapping("{id}")
    public MessageResponse delete(@PathVariable("id") String id) {
        return this.ticketService.delete(id);
    }
}
