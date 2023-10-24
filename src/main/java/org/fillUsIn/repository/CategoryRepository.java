package org.fillUsIn.repository;

import org.fillUsIn.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, String> {
  @Query("SELECT c FROM Category c ORDER BY c.sequence ASC")
  List<Category> findAllOrderedBySequence();

  Optional<Category> findByNameIgnoreCase(String name);

}
