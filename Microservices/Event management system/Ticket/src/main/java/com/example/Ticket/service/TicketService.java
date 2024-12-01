package com.example.Ticket.service;

import com.example.Ticket.entity.Ticket;
import com.example.Ticket.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public Ticket bookTicket(Ticket ticket) {
        ticket.setStatus("PENDING"); // Initial status
        return ticketRepository.save(ticket);
    }

    public List<Ticket> getTicketsByEvent(Long eventId) {
        return ticketRepository.findByEventId(eventId);
    }

    public Ticket cancelTicket(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id " + ticketId));
        ticket.setStatus("CANCELLED");
        return ticketRepository.save(ticket);
    }
}
