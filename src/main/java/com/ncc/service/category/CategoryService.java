package com.ncc.service.category;

import com.ncc.mapstruct.dto.CategoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getCategories();

    List<CategoryDto> getAllCategoryHaveProducts(String cateId);

    CategoryDto save(CategoryDto categoryDto);

    CategoryDto merge(CategoryDto categoryDto);

    void delete(String cateId);
}
