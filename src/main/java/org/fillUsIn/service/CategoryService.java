package org.fillUsIn.service;


import lombok.extern.slf4j.Slf4j;
import org.fillUsIn.entity.Category;
import org.fillUsIn.repository.CategoryRepository;
import org.fillUsIn.repository.SubCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CategoryService {

  private final CategoryRepository categoryRepository;
  private final SubCategoryRepository subCategoryRepository;

  public CategoryService(CategoryRepository categoryRepository, SubCategoryRepository subCategoryRepository) {
    this.categoryRepository = categoryRepository;
    this.subCategoryRepository = subCategoryRepository;
  }

  public List<Category> getCategories() {
    return categoryRepository.findAll();
  }

}
