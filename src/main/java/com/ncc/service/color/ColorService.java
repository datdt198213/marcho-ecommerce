package com.ncc.service.color;

import com.ncc.mapstruct.dto.color.ColorDto;

import java.util.List;

public interface ColorService {
    ColorDto getByColorId(int id);
    List<ColorDto> getColors();
    ColorDto save(ColorDto dto);
    ColorDto merge(ColorDto dto);
    void delete(int id);
}
