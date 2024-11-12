package com.ecommerce.sb_ecom.service.category;

import com.ecommerce.sb_ecom.dto.CategoryDTO;
import com.ecommerce.sb_ecom.dto.CategoryResponse;

public interface CategoryService {
    CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    CategoryDTO createCategory(CategoryDTO categoryDTO);
    CategoryDTO deleteCategory(Long categoryId);
    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);
}
