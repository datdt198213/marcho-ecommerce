package com.ncc.service.size;

import com.ncc.mapstruct.dto.size.SizeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SizeService {
    SizeDto getSizeById(int id);
    List<SizeDto> getSizes();
    SizeDto save(SizeDto dto);
    SizeDto merge(SizeDto dto);
    void delete(int id);
}
