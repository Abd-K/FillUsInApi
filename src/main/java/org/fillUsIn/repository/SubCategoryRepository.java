package org.fillUsIn.repository;

import org.fillUsIn.entity.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubCategoryRepository extends JpaRepository<Subcategory, String> {
  Optional<Subcategory> findByNameIgnoreCase(String name);

}
