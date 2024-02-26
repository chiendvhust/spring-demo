package com.example.demo.service;

import com.example.demo.entity.Flight;
import com.example.demo.entity.Ticket;
import com.example.demo.repository.FlightRepository;
import com.example.demo.repository.TicketRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class DbService {

    private final FlightRepository flightRepository;

    private final TicketRepository ticketRepository;

    public DbService(FlightRepository flightRepository, TicketRepository ticketRepository) {
        this.flightRepository = flightRepository;
        this.ticketRepository = ticketRepository;
    }

    private void saveNewTicket(String firstName, String lastName, Flight flight) throws Exception {
        if (flight.getCapacity() <= flight.getTickets().size()) {
            throw new RuntimeException("Exceeded Capacity");
        }
        var ticket = new Ticket();
        ticket.setFirstName(firstName);
        ticket.setLastName(lastName);
        flight.addTicket(ticket);
        ticketRepository.save(ticket);
    }

    @Transactional
    public void changeFlight1() throws Exception {
        var flight = flightRepository.findById(1L).get();
        saveNewTicket("Robert", "Smith", flight);
        Thread.sleep(1_000);
    }

    @Transactional
    public void changeFlight2() throws Exception {
        var flight = flightRepository.findById(1L).get();
        saveNewTicket("Kate", "Brown", flight);
        Thread.sleep(1_000);
    }

}
