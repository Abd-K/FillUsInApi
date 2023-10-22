package org.fillUsIn.controller;


import lombok.extern.slf4j.Slf4j;
import org.fillUsIn.entity.Category;
import org.fillUsIn.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@Slf4j
public class CategoryController {

  private final CategoryService categoryService;

  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @GetMapping("/")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public List<Category> getCategories() {
    return categoryService.getCategories();
  }


}
