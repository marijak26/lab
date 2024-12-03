package mk.finki.ukim.mk.lab.service.impl;

import mk.finki.ukim.mk.lab.model.Category;
import mk.finki.ukim.mk.lab.model.Event;
import mk.finki.ukim.mk.lab.model.Location;
import mk.finki.ukim.mk.lab.model.exceptions.CategoryNotFoundException;
import mk.finki.ukim.mk.lab.model.exceptions.LocationNotFoundException;
import mk.finki.ukim.mk.lab.repository.*;
import mk.finki.ukim.mk.lab.service.EventService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EventServiceImpl implements EventService {
    private final JpaEventRepository eventRepository;
    private final JpaCategoryRepository categoryRepository;
    private final JpaLocationRepository locationRepository;

    public EventServiceImpl(JpaEventRepository eventRepository, JpaCategoryRepository categoryRepository, JpaLocationRepository locationRepository) {
        this.eventRepository = eventRepository;
        this.categoryRepository = categoryRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public List<Event> listAll() {
        return this.eventRepository.findAll();
    }

    @Override
    public List<Event> searchEvents(String text) {
        return this.eventRepository.findByNameContainingIgnoreCase(text);
    }

    @Override
    public List<Event> searchByRating(double minRating) {
        return this.eventRepository.findByPopularityScoreGreaterThanEqual(minRating);
    }

    @Override
    public List<Event> searchByCategory(String categoryName) {
        return this.eventRepository.findByCategory_NameContainingIgnoreCase(categoryName);
    }

    @Override
    public Optional<Event> saveEvent(String eventName, String description, double popularityScore, Long categoryId, Long locationId) {
        Category category = this.categoryRepository.findCategoryById(categoryId).orElseThrow(()-> new CategoryNotFoundException(categoryId));
        Location location = this.locationRepository.findById(locationId).orElseThrow(()-> new LocationNotFoundException(locationId));
        Event event = new Event(eventName, description, popularityScore, category, location);
        return Optional.of(this.eventRepository.save(event));
    }

    @Override
    public Optional<Event> findEventById(Long id){
        return this.eventRepository.findById(id);
    }

    @Override
    public Optional<Event> editEvent(Long id, String eventName, String description, double popularityScore, Long locationId) {
        Event event = this.eventRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Event not found"));
        event.setName(eventName);
        event.setDescription(description);
        event.setPopularityScore(popularityScore);
        Location location = this.locationRepository.findById(locationId).orElseThrow(() -> new LocationNotFoundException(locationId));
        event.setLocation(location);
        return Optional.of(this.eventRepository.save(event));
    }

    @Override
    public void deleteEventById(Long eventId){
        this.eventRepository.deleteById(eventId);
    }

    @Override
    public List<Event> findAllByLocation_Id(Long locationId) {
        return this.eventRepository.findAllByLocation_Id(locationId);
    }
}