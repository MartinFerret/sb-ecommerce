package com.ecommerce.sb_ecom.controller;

import com.ecommerce.sb_ecom.config.AppConstant;
import com.ecommerce.sb_ecom.dto.CategoryDTO;
import com.ecommerce.sb_ecom.dto.CategoryResponse;
import com.ecommerce.sb_ecom.service.category.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping(value = "/public/categories", method = RequestMethod.GET)
    public ResponseEntity<CategoryResponse> getAllCategories(
            @RequestParam(name = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstant.SORT_CATEGORIES_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstant.SORT_ORDER, required = false) String sortOrder) {
        CategoryResponse category = categoryService.getAllCategories(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(category, HttpStatus.OK) ;
    }

    @RequestMapping(value = "/public/categories", method = RequestMethod.POST)
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO savedCategoryDTO = categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(savedCategoryDTO, HttpStatus.CREATED) ;
    }

    @RequestMapping(value = "/admin/categories/{categoryId}", method = RequestMethod.DELETE)
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long categoryId) {
            return new ResponseEntity<>(categoryService.deleteCategory(categoryId), HttpStatus.OK);
    }

    @RequestMapping(value = "/public/categories/{categoryId}", method = RequestMethod.PUT)
    public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO categoryDTO, @PathVariable Long categoryId) {
            CategoryDTO updatedCategory = categoryService.updateCategory(categoryDTO, categoryId);
            return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

}
