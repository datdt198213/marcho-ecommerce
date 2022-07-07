package com.ncc.controller;

import com.ncc.mapstruct.dto.size.SizeDto;
import com.ncc.service.size.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sizes")
public class SizeController {
    @Autowired
    private SizeService sizeService;

    @GetMapping
    public ResponseEntity<List<SizeDto>> getSizes() {
        List<SizeDto> dtos = sizeService.getSizes();

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/size/{id}")
    public ResponseEntity<SizeDto> getSizeById(@PathVariable("id") int id) {
        SizeDto dto = sizeService.getSizeById(id);

        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<SizeDto> save(@RequestBody SizeDto sizeDto) {
        SizeDto dto = sizeService.save(sizeDto);

        return ResponseEntity.ok(dto);
    }

    @PutMapping
    public ResponseEntity<SizeDto> merge(@RequestBody SizeDto sizeDto) {
        SizeDto dto = sizeService.merge(sizeDto);

        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        sizeService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
