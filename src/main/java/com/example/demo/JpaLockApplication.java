package com.example.demo;

import com.example.demo.entity.Flight;
import com.example.demo.entity.Ticket;
import com.example.demo.repository.FlightRepository;
import com.example.demo.repository.TicketRepository;
import com.example.demo.service.DbService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.function.FailableRunnable;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class JpaLockApplication implements CommandLineRunner {

    @Resource
    private DbService dbService;

    @Resource
    private FlightRepository flightRepository;

    @Resource
    private TicketRepository ticketRepository;

    public static void main(String[] args) {
        SpringApplication.run(JpaLockApplication.class, args);
    }
    @PostConstruct
    public void postConstruct() {
        Flight flight1 = Flight.builder()
                .number("FLT123")
                .departureTime(LocalDateTime.parse("2022-04-01T09:00:00"))
                .capacity(2)
                .build();

        Flight flight2 = Flight.builder()
                .number("FLT234")
                .departureTime(LocalDateTime.parse("2022-04-10T10:30:00"))
                .capacity(50)
                .build();

        flightRepository.saveAll(List.of(flight1, flight2));

        // Initialize tickets
        Ticket ticket1 = Ticket.builder()
                .flight(flight1)
                .firstName("Paul")
                .lastName("Lee")
                .build();

        // Add more tickets as needed

        ticketRepository.saveAll(List.of(ticket1));
    }

    @Override
    public void run(String... args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(safeRunnable(dbService::changeFlight1));
        executor.execute(safeRunnable(dbService::changeFlight2));
        executor.shutdown();
    }

    private Runnable safeRunnable(FailableRunnable<Exception> runnable) {
        return () -> {
            try {
                runnable.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }
}