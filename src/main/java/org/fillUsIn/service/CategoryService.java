package org.fillUsIn.service;


import lombok.extern.slf4j.Slf4j;
import org.fillUsIn.entity.Category;
import org.fillUsIn.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Slf4j
public class CategoryService {

  private final CategoryRepository categoryRepository;

  public CategoryService(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  public List<Category> getCategories() {
    return categoryRepository.findAllOrderedBySequence();
  }

  public Category getCategory(String categoryName) {
    return categoryRepository.findByNameIgnoreCase(categoryName)
            .orElseThrow(() -> new EntityNotFoundException("Parent category not found with name: " + categoryName));
  }

}
