package com.ncc.mapstruct.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {
    private String id;
    private String name;
    private String parentId;
    private int displayOrder;
}
