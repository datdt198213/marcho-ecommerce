package com.ncc.mapstruct.dto.product;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Getter
@Setter
@Data
public class ProductDto implements Serializable {
    private String id;

    private String name;

    private MultipartFile[] multipartFiles;

    private String brand;

    private double priceOut;

    private double priceIn;

    private int quantity;

    private String description;

    private int colorId;

    private int sizeId;

    private String cateId;
}
