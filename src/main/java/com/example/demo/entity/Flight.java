package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "flights")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;

    private LocalDateTime departureTime;

    private Integer capacity;

    @OneToMany(mappedBy = "flight")
    private Set<Ticket> tickets;

    public void addTicket(Ticket ticket) {
        ticket.setFlight(this);
        getTickets().add(ticket);
    }

}