package mk.finki.ukim.mk.lab.repository;

import mk.finki.ukim.mk.lab.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaCategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAll();
    Optional<Category> findCategoryById(Long id);
}
