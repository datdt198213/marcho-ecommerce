package com.ncc.controller;

import com.ncc.contants.Constant;
import com.ncc.mapstruct.dto.product.ProductDetailDto;
import com.ncc.mapstruct.dto.product.ProductDto;
import com.ncc.mapstruct.dto.product.ProductViewDto;
import com.ncc.service.file.FileStorageService;
import com.ncc.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping(value = "/api")
public class ProductController {
    private ProductService productService;
    private FileStorageService fileStorageService;

    @Autowired
    public ProductController(ProductService productService,
                             FileStorageService fileStorageService) {
        this.productService = productService;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/products")
    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = RequestMethod.GET)
    public ResponseEntity<Page<ProductViewDto>> filter(Pageable pageable,
                                                       @RequestParam(value = "name", defaultValue = "") String name,
                                                       @RequestParam(value = "cateId", defaultValue = "") String cateId,
                                                       @RequestParam(value = "colorId", required = false) Integer colorId,
                                                       @RequestParam(value = "startPrice", defaultValue = "0", required = false) Double startPrice,
                                                       @RequestParam(value = "endPrice", required = false) Double endPrice) {

        Page<ProductViewDto> productDtos = productService.filter(cateId, colorId, startPrice, endPrice, name, pageable);
        return ResponseEntity.ok(productDtos);
    }

    @GetMapping("/products/category")
    public ResponseEntity<Page<ProductViewDto>> getProductsFromCategory(@RequestParam(name = "cateId") String cateId,
                                                                        Pageable pageable) {
        Page<ProductViewDto> productDtos = productService.getProductsFromCategory(cateId, pageable);
        return ResponseEntity.ok(productDtos);
    }

    @GetMapping("/products/{prodId}")
    public ResponseEntity<ProductDetailDto> getProduct(@PathVariable("prodId") String prodId) {
        ProductDetailDto dto = productService.getProductById(prodId);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/products/view/{fileName:.+}")
    public ResponseEntity<?> view(@PathVariable("fileName") String fileName, HttpServletRequest request) {
        Resource resource = fileStorageService.loadFileAsResource(Constant.PRODUCT_FOLDER, fileName);
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (contentType == null)
            contentType = "application/octet-stream";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);

    }

    @PostMapping("/products")
    public ResponseEntity<Void> save(@ModelAttribute ProductDto productDto) {

        productService.save(productDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/products")
    public ResponseEntity<ProductDto> merge(@ModelAttribute ProductDto productDto) {
        productService.merge(productDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/products/{id}")
    public void delete(@PathVariable("id") String id) {
        productService.delete(id);
    }
}
