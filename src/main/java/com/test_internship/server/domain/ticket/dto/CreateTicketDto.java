package com.test_internship.server.domain.ticket.dto;

import com.test_internship.server.domain.ticket.entities.Ticket;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTicketDto {
    @NotBlank(message = "Digite um titulo válido")
    private String title;

    @NotBlank(message = "Digite uma descrição válida")
    private String description;

    @NotBlank(message = "Digite um usuário válido")
    private String user;

    public Ticket toTicket() {
        Ticket ticket = new Ticket();
        ticket.setTitle(this.title);
        ticket.setDescription(this.description);
        return ticket;
    }
}
