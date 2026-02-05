package mk.finki.ukim.mk.lab.repository;

import mk.finki.ukim.mk.lab.model.Category;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryRepository {
    private final List<Category> categories;

    public CategoryRepository() {
        this.categories = new ArrayList<>();
    }

    public List<Category> listCategories() {
        return categories;
    }
    public Optional<Category> findCategoryById(Long id) {
        return categories.stream().filter(category -> category.getId().equals(id)).findFirst();
    }
}