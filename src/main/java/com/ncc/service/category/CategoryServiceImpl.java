package com.ncc.service.category;

import com.ncc.mapstruct.dto.CategoryDto;
import com.ncc.mapstruct.mapper.MapstructMapper;
import com.ncc.model.Category;
import com.ncc.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private MapstructMapper mapstructMapper;
    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(MapstructMapper mapstructMapper, CategoryRepository categoryRepository) {
        this.mapstructMapper = mapstructMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDto> getCategories() {
        List<CategoryDto> categoryDtos = categoryRepository.findAll()
                .stream().map(mapstructMapper::toDto)
                .collect(Collectors.toList());

        return categoryDtos;
    }

    public List<CategoryDto> getAllCategoryHaveProducts(String cateId) {
        List<CategoryDto> categoryDtos = new ArrayList<>();
        List<CategoryDto> dtos = getCategoryByParentId(cateId);

        if (cateId.isEmpty()){
            categoryDtos.addAll(getCategories());

            return categoryDtos;
        }

        for (int i = 0; i < dtos.size(); i++) {
            CategoryDto dto = dtos.get(i);
            List<CategoryDto> temp = getCategoryByParentId(dto.getId());
            if (temp.size() == 0) {
                categoryDtos.add(dto);
            } else {
                dtos.addAll(temp);
            }
        }

        return categoryDtos;
    }

    public List<CategoryDto> getCategoryByParentId(String cateId) {
        List<Category> categories = categoryRepository.getCategoryByParentId(cateId);

        return categories.stream().map(mapstructMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public CategoryDto save(CategoryDto categoryDto) {
        Category category = categoryRepository.save(
                mapstructMapper.toEntity(categoryDto)
        );

        return mapstructMapper.toDto(category);
    }

    @Override
    public CategoryDto merge(CategoryDto categoryDto) {
        Category category = categoryRepository.getById(categoryDto.getId());
        if (category != null)
            return save(categoryDto);

        return null;
    }

    @Override
    public void delete(String cateId) {
        if (categoryRepository.getById(cateId) != null)
            categoryRepository.deleteById(cateId);
    }
}
