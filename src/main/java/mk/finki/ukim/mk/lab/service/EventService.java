package mk.finki.ukim.mk.lab.service;

import mk.finki.ukim.mk.lab.model.Event;
import java.util.List;
import java.util.Optional;

public interface EventService {
    List<Event> listAll();

    List<Event> searchEvents(String text);

    List<Event> searchByRating(double minRating);

    List<Event> searchByCategory(String categoryName);

    Optional<Event> saveEvent(String eventName, String description, double popularityScore, Long categoryId, Long locationId);

    Optional<Event> findEventById(Long id);

    Optional<Event> editEvent(Long id, String eventName, String description, double popularityScore,Long categoryId, Long locationId);

    void deleteEventById(Long eventId);
    List<Event> findAllByLocation_Id(Long locationId);
    }
