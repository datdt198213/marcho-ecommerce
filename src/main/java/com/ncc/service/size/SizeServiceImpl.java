package com.ncc.service.size;

import com.ncc.mapstruct.dto.size.SizeDto;
import com.ncc.mapstruct.mapper.MapstructMapper;
import com.ncc.model.Size;
import com.ncc.repository.SizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SizeServiceImpl implements SizeService{
    private MapstructMapper mapstructMapper;
    private SizeRepository sizeRepository;

    @Autowired
    public SizeServiceImpl(MapstructMapper mapstructMapper, SizeRepository sizeRepository) {
        this.mapstructMapper = mapstructMapper;
        this.sizeRepository = sizeRepository;
    }

    @Override
    public SizeDto getSizeById(int id) {
        Optional<Size> size = sizeRepository.findById(id);

        if (size.isPresent())
            return mapstructMapper.toDto(size.get());
        return null;
    }

    @Override
    public List<SizeDto> getSizes() {
        List<SizeDto>dtos = sizeRepository.findAll().stream()
                .map(mapstructMapper::toDto).collect(Collectors.toList());

        return dtos;
    }

    @Override
    public SizeDto save(SizeDto dto) {
        Size size = sizeRepository.save(mapstructMapper.toEntity(dto));

        return mapstructMapper.toDto(size);
    }

    @Override
    public SizeDto merge(SizeDto dto) {
        if (sizeRepository.existsById(dto.getId()))
            return save(dto);

        return null;
    }

    @Override
    public void delete(int id) {
        if (sizeRepository.existsById(id))
            sizeRepository.deleteById(id);
    }
}
