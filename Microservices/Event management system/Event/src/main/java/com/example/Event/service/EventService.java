package com.example.Event.service;

import com.example.Event.entity.Event;
import com.example.Event.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    public Event updateEvent(Long id, Event eventDetails) {
        Event existingEvent = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found with id " + id));

        existingEvent.setName(eventDetails.getName());
        existingEvent.setLocation(eventDetails.getLocation());
        existingEvent.setDate(eventDetails.getDate());
        existingEvent.setOrganizer(eventDetails.getOrganizer());
        existingEvent.setCapacity(eventDetails.getCapacity());
        existingEvent.setDescription(eventDetails.getDescription());

        return eventRepository.save(existingEvent);
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }
}