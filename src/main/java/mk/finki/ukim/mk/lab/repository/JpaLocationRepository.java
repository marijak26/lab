package mk.finki.ukim.mk.lab.repository;

import mk.finki.ukim.mk.lab.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaLocationRepository extends JpaRepository<Location, Long> {
    List<Location> findAll();
    Optional<Location> findById(Long id);
}
