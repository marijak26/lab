package mk.finki.ukim.mk.lab.model;

import jakarta.persistence.*;
import lombok.*;

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

    public Event(String name, String description, double popularityScore, Category category, Location location) {
        this.name = name;
        this.description = description;
        this.popularityScore = popularityScore;
        this.category = category;
        this.location = location;
    }
}

//sudo systemctl stop postgresql - naredba za da loadnes app
//psql postgre stop - isto ko 1