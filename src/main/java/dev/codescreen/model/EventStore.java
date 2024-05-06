package dev.codescreen.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class EventStore {
    private List<Event> events;

    public EventStore() {
        this.events = new ArrayList<>();
    }

    public void storeEvent(Event event) {
        events.add(event);
    }

    public List<Event> getAllEvents() {
        return events;
    }

}
