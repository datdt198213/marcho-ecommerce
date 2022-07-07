package com.ncc.service.discount;

import com.ncc.mapstruct.dto.discount.DiscountDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DiscountService {
    Page<DiscountDto> getAll(Pageable pageable);
    DiscountDto save(DiscountDto discountDto);
    DiscountDto update(DiscountDto discountDto);
    public void disableByProductId(String id);
}
