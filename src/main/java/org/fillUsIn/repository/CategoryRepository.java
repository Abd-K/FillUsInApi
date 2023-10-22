package org.fillUsIn.repository;

import org.fillUsIn.entity.Category;
import org.fillUsIn.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, String> {
  Optional<Category> findByNameIgnoreCase(String name);
  @Query("SELECT DISTINCT p FROM Category c " +
          "JOIN c.subcategories sc " +
          "JOIN sc.posts p " +
          "WHERE LOWER(c.name) = LOWER(:categoryName)")
  List<Post> findPostsFromSubcategoriesInCategory(@Param("categoryName") String categoryName);

}
