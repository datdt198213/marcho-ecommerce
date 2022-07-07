package com.ncc.repository;

import com.ncc.model.Category;
import com.ncc.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,String> {
    List<Category> getCategoryByParentId(String parentId);
}
