package com.ncc.controller;

import com.ncc.mapstruct.dto.discount.DiscountDto;
import com.ncc.service.discount.DiscountService;
import com.ncc.service.discount.ProductDiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
public class DiscountController {

    @Autowired
    private DiscountService discountService;
    @Autowired
    private ProductDiscountService productDiscountService;

    @GetMapping(value = "/discounts")
    public ResponseEntity<Page<DiscountDto>> getDiscounts(Pageable pageable){
        Page<DiscountDto> discountDtoList = discountService.getAll(pageable);
        return ResponseEntity.ok(discountDtoList);
    }

    @PostMapping(value = "/discounts")
    public ResponseEntity<DiscountDto> save(DiscountDto discountDto) {
        DiscountDto response = discountService.save(discountDto);
        return ResponseEntity.ok(response);
    }
    @PutMapping(value = "/discounts")
    public ResponseEntity<DiscountDto> update(DiscountDto discountDto) {
        DiscountDto response = discountService.update(discountDto);
        return ResponseEntity.ok(response);
    }

    /*fixme: tại sao lại phải disable theo productId ???*/
    @DeleteMapping("/discounts/{id}")
    public ResponseEntity<?> disableByProductId(@PathVariable("id") String id) {
        discountService.disableByProductId(id);
        return ResponseEntity.ok("Remove discount successfully!\n");
    }
}
