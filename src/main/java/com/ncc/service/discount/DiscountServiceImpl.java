package com.ncc.service.discount;

import com.ncc.mapstruct.dto.discount.DiscountDto;
import com.ncc.mapstruct.mapper.MapstructMapper;
import com.ncc.model.Discount;
import com.ncc.repository.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DiscountServiceImpl implements DiscountService {
    private DiscountRepository discountRepository;
    private MapstructMapper mapstructMapper;

    @Autowired
    public DiscountServiceImpl(DiscountRepository discountRepository, MapstructMapper mapstructMapper) {
        this.discountRepository = discountRepository;
        this.mapstructMapper = mapstructMapper;
    }

    public Page<DiscountDto> getAll(Pageable pageable) {
        Page<DiscountDto> discountDtoPage = discountRepository.findAll(pageable).map(mapstructMapper::toDto);
        return discountDtoPage;
    }

    @Override
    public DiscountDto save(DiscountDto discountDto) {
        return mapstructMapper.toDto(discountRepository.save(mapstructMapper.toEntity(discountDto)));
    }
    @Override
    public DiscountDto update(DiscountDto discountDto) {
        return mapstructMapper.toDto(discountRepository.save(mapstructMapper.toEntity(discountDto)));
    }
    @Override
    public void disableByProductId(String id) {
        Discount discount = discountRepository.findDiscountByProductId(id);
        discount.setStatus(false);

        save(mapstructMapper.toDto(discount));
    }
}
