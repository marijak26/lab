package mk.finki.ukim.mk.lab.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="location")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private String capacity;
    private String locationDescription;

    @OneToMany(mappedBy = "location")
    private List<Event> events;

    public Location(String name, String address, String capacity, String locationDescription) {
        this.name = name;
        this.address = address;
        this.capacity = capacity;
        this.locationDescription = locationDescription;
        this.events = new ArrayList<>();
    }
}