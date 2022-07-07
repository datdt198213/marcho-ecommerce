package com.ncc.controller;

import com.ncc.mapstruct.dto.CategoryDto;
import com.ncc.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getCategories(){
        List<CategoryDto> categoryDtos = categoryService.getCategories();

        return ResponseEntity.ok(categoryDtos);
    }

    @PostMapping
    public ResponseEntity<CategoryDto> save(@RequestBody CategoryDto categoryDto){
        CategoryDto dto = categoryService.save(categoryDto);

        return ResponseEntity.ok(dto);
    }

    @PutMapping
    public ResponseEntity<CategoryDto> merge(@RequestBody CategoryDto categoryDto){
        CategoryDto dto = categoryService.merge(categoryDto);

        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id){
        categoryService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
