package com.ncc.controller;

import com.ncc.mapstruct.dto.color.ColorDto;
import com.ncc.service.color.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/colors")
public class ColorController {
    @Autowired
    private ColorService colorService;

    @GetMapping
    public ResponseEntity<List<ColorDto>> getColors() {
        List<ColorDto> dtos = colorService.getColors();

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/color/{id}")
    public ResponseEntity<ColorDto> getByColorId(@PathVariable("id") int id) {
        ColorDto dto = colorService.getByColorId(id);

        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<ColorDto> save(@RequestBody ColorDto colorDto) {
        ColorDto dto = colorService.save(colorDto);

        return ResponseEntity.ok(dto);
    }

    @PutMapping
    public ResponseEntity<ColorDto> merge(@RequestBody ColorDto colorDto) {
        ColorDto dto = colorService.merge(colorDto);

        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        colorService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
