package org.fillUsIn.service;

import lombok.extern.slf4j.Slf4j;
import org.fillUsIn.entity.Subcategory;
import org.fillUsIn.repository.SubCategoryRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@Slf4j
public class SubCategoryService {
  private final SubCategoryRepository subCategoryRepository;

  public SubCategoryService(SubCategoryRepository subCategoryRepository) {
    this.subCategoryRepository = subCategoryRepository;
  }

  public Subcategory getSubcategory(String subCategoryName) {
   return subCategoryRepository.findByNameIgnoreCase(subCategoryName)
            .orElseThrow(() -> new EntityNotFoundException("Subcategory not found with id: " + subCategoryName));
  }

}
