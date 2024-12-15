package mk.finki.ukim.mk.lab.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private double popularityScore;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Location location;

    @ManyToOne
    private User user;

    @ManyToMany
    private List<Event> events;

    public Event(String name, String description, double popularityScore, Category category, Location location) {
        this.name = name;
        this.description = description;
        this.popularityScore = popularityScore;
        this.category = category;
        this.location = location;
    }
    public Event(User user){
        this.user = user;
        this.events = new ArrayList<>();
    }
}