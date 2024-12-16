package mk.finki.ukim.mk.lab.web.controller;

import mk.finki.ukim.mk.lab.model.Category;
import mk.finki.ukim.mk.lab.model.Event;
import mk.finki.ukim.mk.lab.model.Location;
import mk.finki.ukim.mk.lab.service.CategoryService;
import mk.finki.ukim.mk.lab.service.EventService;
import mk.finki.ukim.mk.lab.service.LocationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class EventController {
    private final EventService eventService;
    private final LocationService locationService;
    private final CategoryService categoryService;

    public EventController(EventService eventService, LocationService locationService, CategoryService categoryService) {
        this.eventService = eventService;
        this.locationService = locationService;
        this.categoryService = categoryService;
    }

    @GetMapping("/events")
    public String getEventsPage(
            @RequestParam(required = false) String searchText,
            @RequestParam(required = false) String minRatingString,
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) Long locationId,
            @RequestParam(required = false) String error,
            Model model) {

        double minRating = 0.0;
        if (minRatingString != null && !minRatingString.isEmpty()) {
            try {
                minRating = Double.parseDouble(minRatingString);
            } catch (NumberFormatException e) {
                model.addAttribute("errorMessage", "Invalid rating value!");
            }
        }

        List<Event> events;

        if (searchText != null && !searchText.isEmpty()) {
            events = this.eventService.searchEvents(searchText);
        }
        else if (minRating > 0.0) {
            events = this.eventService.searchByRating(minRating);
        }
        else if (categoryName != null && !categoryName.isEmpty()) {
            events = this.eventService.searchByCategory(categoryName);
        }
        else if (locationId != null) {
            events = this.eventService.findAllByLocation_Id(locationId);
        }
        else {
            events = this.eventService.listAll();
        }

        if (events.isEmpty()) {
            model.addAttribute("errorMessage", "No events found matching your criteria!");
        }

        List<Category> categories = this.categoryService.listAll();
        List<Location> locations = this.locationService.findAll();
        model.addAttribute("events", events);
        model.addAttribute("categories", categories);
        model.addAttribute("bodyContent", "events");

        return "listEvents";
    }

    @PostMapping("/events/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String saveEvent(@RequestParam(required = false) Long id,@RequestParam String eventName, @RequestParam String description, @RequestParam double popularityScore, @RequestParam Long categoryId, @RequestParam Long locationId) {
        if(id != null) {
            if (locationId != null) {
                this.eventService.editEvent(id, eventName, description, popularityScore, locationId);
            }
        }
        else{
            this.eventService.saveEvent(eventName, description, popularityScore, categoryId, locationId);
        }
        return "redirect:/events";
    }



    @GetMapping("/events/edit-form/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getEditEventForm(@PathVariable Long id, Model model) {
        if (this.eventService.findEventById(id).isPresent()) {
            Event event = this.eventService.findEventById(id).get();
            List<Location> locations = this.locationService.findAll();
            List<Category> categories = this.categoryService.listAll();
            model.addAttribute("locations", locations);
            model.addAttribute("categories", categories);
            model.addAttribute("event", event);
            return "add-event";
        }
        return "redirect:/events?error=EventNotFound";
    }

    @GetMapping("events/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteEvent(@PathVariable Long id) {
        this.eventService.deleteEventById(id);
        return "redirect:/events";
    }

    @GetMapping("/events/add-form")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getAddEventPage(Model model) {
        List<Category> categories = this.categoryService.listAll();
        List<Location> locations = this.locationService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("locations", locations);
        return "add-event";
    }
}
