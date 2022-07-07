package com.ncc.service.product;

import com.ncc.mapstruct.dto.product.ProductDetailDto;
import com.ncc.mapstruct.dto.product.ProductDto;
import com.ncc.mapstruct.dto.product.ProductViewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    ProductDetailDto getProductById(String prodId);

    Page<ProductViewDto> getProducts(Pageable pageable);

    Page<ProductViewDto> getProductsFromCategory(String cateId, Pageable pageable);

    Page<ProductViewDto> filter(String cateId, Integer colorId, Double startPrice,
                                Double endPrice,
                                String name,
                                Pageable pageable);

    void save(ProductDto productDto);

    void merge(ProductDto productDto);

    void delete(String id);
}
