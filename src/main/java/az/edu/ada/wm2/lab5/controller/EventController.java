package az.edu.ada.wm2.lab5.controller;

import az.edu.ada.wm2.lab5.model.Event;
import az.edu.ada.wm2.lab5.service.EventService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        try {
            Event createdEvent = eventService.createEvent(event);
            return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        return new ResponseEntity<>(eventService.getAllEvents(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable UUID id) {
        try {
            return new ResponseEntity<>(eventService.getEventById(id), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable UUID id) {
        try {
            eventService.deleteEvent(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable UUID id,
                                             @RequestBody Event event) {
        try {
            return new ResponseEntity<>(eventService.updateEvent(id, event), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Event> partialUpdateEvent(@PathVariable UUID id,
                                                    @RequestBody Event partialEvent) {
        try {
            return new ResponseEntity<>(eventService.partialUpdateEvent(id, partialEvent), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/filter/date")
    public ResponseEntity<List<Event>> filterByDate(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime start,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime end) {

        return new ResponseEntity<>(
                eventService.getEventsByDateRange(start, end),
                HttpStatus.OK
        );
    }

    @GetMapping("/filter/price")
    public ResponseEntity<List<Event>> filterByPrice(
            @RequestParam BigDecimal min,
            @RequestParam BigDecimal max) {

        return new ResponseEntity<>(
                eventService.getEventsByPriceRange(min, max),
                HttpStatus.OK
        );
    }

    @GetMapping("/filter/tag")
    public ResponseEntity<List<Event>> filterByTag(
            @RequestParam String tag) {

        return new ResponseEntity<>(
                eventService.getEventsByTag(tag),
                HttpStatus.OK
        );
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<Event>> getUpcomingEvents() {
        return new ResponseEntity<>(
                eventService.getUpcomingEvents(),
                HttpStatus.OK
        );
    }



}