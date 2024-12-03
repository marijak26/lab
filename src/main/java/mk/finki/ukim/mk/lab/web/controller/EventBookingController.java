package mk.finki.ukim.mk.lab.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import mk.finki.ukim.mk.lab.model.Event;
import mk.finki.ukim.mk.lab.model.EventBooking;
import mk.finki.ukim.mk.lab.service.EventBookingService;
import mk.finki.ukim.mk.lab.service.EventService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/eventBooking")
public class EventBookingController {

    private final EventBookingService eventBookingService;
    private final EventService eventService;

    public EventBookingController(EventBookingService eventBookingService, EventService eventService) {
        this.eventBookingService = eventBookingService;
        this.eventService = eventService;
    }

    @PostMapping
    public String placeBooking(@RequestParam Long eventId, @RequestParam String attendeeName,
                               @RequestParam long numTickets, HttpServletRequest request, Model model) {
        if(eventService.findEventById(eventId).isPresent()) {
            Event event = eventService.findEventById(eventId).get();

            String attendeeAddress = request.getRemoteAddr();

            EventBooking booking = eventBookingService.placeBooking(event.getName(), attendeeName, attendeeAddress, (int) numTickets);

            model.addAttribute("booking", booking);
            return "bookingConfirmation";
        }
        return "redirect:/events?error=EventNotFound";

    }
}