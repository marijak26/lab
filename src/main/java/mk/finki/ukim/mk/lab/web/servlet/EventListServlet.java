package mk.finki.ukim.mk.lab.web.servlet;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.finki.ukim.mk.lab.model.Event;
import mk.finki.ukim.mk.lab.service.EventService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "EventListServlet", urlPatterns = "/servlet/events")
public class EventListServlet extends HttpServlet {
    private final EventService eventService;
    private final SpringTemplateEngine templateEngine;

    public EventListServlet(EventService eventService, SpringTemplateEngine templateEngine) {
        this.eventService = eventService;
        this.templateEngine = templateEngine;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String searchText = req.getParameter("searchText");
        String minRatingString = req.getParameter("minRating");
        String categoryName = req.getParameter("categoryName");
        double minRating;
        if (minRatingString != null && !minRatingString.isEmpty()) {
            minRating = Double.parseDouble(minRatingString);
        }
        else {
            minRating = 0.0;
        }
        IWebExchange webExchange = JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(req, resp);

        List<Event> events;
        if (searchText != null && !searchText.isEmpty()) {
            events = eventService.searchEvents(searchText);
            if (events.isEmpty()) {
                req.setAttribute("errorMessage", "No events found matching your criteria");
            }
        }
        else if (minRating != 0.0) {
            events = eventService.searchByRating(minRating);
            if(events.isEmpty()) {
                req.setAttribute("errorMessage", "No events found matching your criteria");
            }
        }
        else if(categoryName != null && !categoryName.isEmpty()){
            events = eventService.searchByCategory(categoryName);
            if (events.isEmpty()) {
                req.setAttribute("errorMessage", "No events found matching your criteria");
            }
        }
        else{
            events = eventService.listAll();
        }

        List<String> uniqueCategories = events.stream().map(e->e.getCategory().getName()).distinct().toList();

        WebContext context = new WebContext(webExchange);
        context.setVariable("events", events);
        context.setVariable("errorMessage", req.getAttribute("errorMessage"));
        context.setVariable("categories", uniqueCategories);

        templateEngine.process("listEvents.html", context, resp.getWriter());
    }
}
