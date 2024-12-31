package com.test_internship.server.domain.ticket.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.test_internship.server.domain.user.entities.User;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Document()
public class Ticket {
    @Id()
    private String id;

    private String title;
    private String description;
    private Date createdAt = new Date();
    private boolean open = true;

    @DBRef
    private User user;

    public void addUser(User user) {
        this.user = user;
    }

    public boolean getOpen() {
        return this.open;
    }
}
