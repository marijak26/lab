package mk.finki.ukim.mk.lab.repository;

import mk.finki.ukim.mk.lab.model.Location;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LocationRepository {
    private final List<Location> locations;

    public LocationRepository() {
        this.locations = new ArrayList<>();
    }

    public List<Location> findAll() {
        return locations;
    }

    public Optional<Location> findById(Long id) {
        return locations.stream().filter(location -> location.getId().equals(id)).findFirst();
    }
}