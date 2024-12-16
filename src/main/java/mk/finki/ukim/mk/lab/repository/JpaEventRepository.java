package mk.finki.ukim.mk.lab.repository;

import mk.finki.ukim.mk.lab.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaEventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByLocation_Id(Long locationId);
    List<Event> findAll();
    List<Event> findByNameContainingIgnoreCase(String text);
    List<Event> findByPopularityScoreGreaterThanEqual(double minRating);
    List<Event> findByCategory_NameContainingIgnoreCase(String category);
    Optional<Event> findById(Long id);
    void deleteById(Long id);
}
