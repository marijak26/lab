package mk.finki.ukim.mk.lab.repository;

import mk.finki.ukim.mk.lab.model.Category;
import mk.finki.ukim.mk.lab.model.Event;
import mk.finki.ukim.mk.lab.model.Location;
import mk.finki.ukim.mk.lab.model.exceptions.CategoryNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class EventRepository {
    private final List<Event> events;

    public EventRepository(CategoryRepository categoryRepository, LocationRepository locationRepository) {
        this.events = new ArrayList<>(10);
    }

    public List<Event> findAll(){
        return events;
    }

    public List<Event> searchEvents(String text){
        return events.stream()
                .filter(event -> (event.getName().toLowerCase().contains(text.toLowerCase()) || event.getDescription().toLowerCase().contains(text.toLowerCase())))
                .collect(Collectors.toList());
    }

    public List<Event> searchByRating(double minRating){
        return events.stream().filter(event -> event.getPopularityScore() >= minRating).collect(Collectors.toList());
    }

    public List<Event> searchCategory(String category){
        return events.stream().filter(event -> (event.getCategory().getName().toLowerCase().contains(category.toLowerCase())))
                .collect(Collectors.toList());
    }

    public Optional<Event> findEventById(Long id){
        return events.stream().filter(event -> Objects.equals(event.getId(), id)).findFirst();
    }

    public Optional<Event> editEvent(Long eventId, String eventName, String description, double popularityScore, Long locationId){
        if(findEventById(eventId).isPresent()) {
            Event event = findEventById(eventId).get();
            event.setName(eventName);
            event.setDescription(description);
            event.setPopularityScore(popularityScore);
            Location location = new Location();
            location.setId(locationId);
            event.setLocation(location);
            return Optional.of(event);
        }
        return Optional.empty();
    }

    public Optional<Event> saveEvent(String eventName, String description,double popularityScore, Category category, Location location){
        if(category == null || location == null){
            throw new IllegalArgumentException("Category and location cannot be null");
        }
        Event event = new Event(eventName, description, popularityScore, category, location);
        events.removeIf(e->e.getId().equals(event.getId()) || Objects.equals(e.getName(), eventName) || Objects.equals(e.getDescription(), description)
        || Objects.equals(e.getPopularityScore(), popularityScore) || Objects.equals(e.getCategory(), category) || Objects.equals(e.getLocation(), location));
        events.add(event);
        return Optional.of(event);
    }

    public void deleteEventById(Long id){
        events.removeIf(e -> Objects.equals(e.getId(), id));
    }

}
