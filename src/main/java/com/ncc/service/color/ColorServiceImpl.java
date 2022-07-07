package com.ncc.service.color;

import com.ncc.mapstruct.dto.color.ColorDto;
import com.ncc.mapstruct.mapper.MapstructMapper;
import com.ncc.model.Color;
import com.ncc.repository.ColorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ColorServiceImpl implements ColorService {
    private MapstructMapper mapstructMapper;
    private ColorRepository colorRepository;

    @Autowired
    public ColorServiceImpl(MapstructMapper mapstructMapper, ColorRepository colorRepository) {
        this.mapstructMapper = mapstructMapper;
        this.colorRepository = colorRepository;
    }

    @Override
    public ColorDto getByColorId(int id) {
        Optional<Color> color = colorRepository.findById(id);
        if (color.isPresent())
            return mapstructMapper.toDto(color.get());

        return null;
    }

    @Override
    public List<ColorDto> getColors() {
        List<ColorDto> dtos = colorRepository.getColorByStatusTrue().stream()
                .map(mapstructMapper::toDto).collect(Collectors.toList());
        return dtos;
    }

    @Override
    public ColorDto save(ColorDto dto) {
        Color color = mapstructMapper.toEntity(dto);
        color.setStatus(true);

        return mapstructMapper.toDto(colorRepository.save(color));
    }

    @Override
    public ColorDto merge(ColorDto dto) {
        ColorDto colorDto = null;
        if (colorRepository.existsById(dto.getId()))
            colorDto = save(dto);
        return colorDto;
    }

    @Override
    public void delete(int id) {
        if (colorRepository.existsById(id))
            colorRepository.deleteById(id);
    }
}
