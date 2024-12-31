package com.test_internship.server.domain.ticket.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTicketDto {
    private boolean open;

    public boolean getOpen() {
        return this.open;
    }
}
