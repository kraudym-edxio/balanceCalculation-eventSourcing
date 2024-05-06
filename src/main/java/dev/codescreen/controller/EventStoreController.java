package dev.codescreen.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import dev.codescreen.model.Event;
import dev.codescreen.model.EventStore;

@RestController
@RequestMapping("/event-store")
public class EventStoreController {

    private final EventStore eventStore;

    public EventStoreController(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    @GetMapping("/events")
    public List<Event> getAllEvents() {
        return eventStore.getAllEvents();
    }
}
