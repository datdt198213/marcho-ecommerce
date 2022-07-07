package com.ncc.service.discount;

import com.ncc.mapstruct.dto.discount.ProductDiscountDto;
import com.ncc.mapstruct.mapper.MapstructMapper;
import com.ncc.model.ProductDiscount;
import com.ncc.repository.ProductDiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductDiscountServiceImp implements ProductDiscountService {
    private ProductDiscountRepository productDiscountRepository;
    private MapstructMapper mapstructMapper;

    @Autowired
    public ProductDiscountServiceImp(ProductDiscountRepository productDiscountRepository, MapstructMapper mapstructMapper) {
        this.productDiscountRepository = productDiscountRepository;
        this.mapstructMapper = mapstructMapper;
    }

    @Override
    public ProductDiscountDto getByCoupon(String coupon) {
        ProductDiscount productDiscount = productDiscountRepository.findByCouponIgnoreCase(coupon);
        return mapstructMapper.toDto(productDiscount);
    }

    @Override
    public List<ProductDiscountDto> getAll() {
        List<ProductDiscount> productDiscounts = productDiscountRepository.findAll();

        return productDiscounts.stream()
                .filter(pd -> isExpired(pd.getExpireAt()))
                .map(mapstructMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDiscountDto save(ProductDiscountDto productDiscountDto) {
        return mapstructMapper.toDto(productDiscountRepository.save(mapstructMapper.toEntity(productDiscountDto)));
    }

    @Override
    public ProductDiscountDto update(ProductDiscountDto productDiscountDto) {
        return mapstructMapper.toDto(productDiscountRepository.save(mapstructMapper.toEntity(productDiscountDto)));
    }

    public boolean isExpired(Date date) {
        LocalDate expireLocalDate = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        return LocalDate.now().isAfter(expireLocalDate);
    }
}
