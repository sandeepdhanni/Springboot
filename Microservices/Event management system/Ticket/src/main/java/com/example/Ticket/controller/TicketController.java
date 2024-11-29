package com.example.Ticket.controller;

import com.example.Ticket.entity.Ticket;
import com.example.Ticket.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/book")
    public ResponseEntity<Ticket> bookTicket(@RequestBody Ticket ticket) {
        return ResponseEntity.ok(ticketService.bookTicket(ticket));
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<Ticket>> getTicketsByEvent(@PathVariable Long eventId) {
        return ResponseEntity.ok(ticketService.getTicketsByEvent(eventId));
    }

    @PutMapping("/cancel/{ticketId}")
    public ResponseEntity<Ticket> cancelTicket(@PathVariable Long ticketId) {
        return ResponseEntity.ok(ticketService.cancelTicket(ticketId));
    }
}
