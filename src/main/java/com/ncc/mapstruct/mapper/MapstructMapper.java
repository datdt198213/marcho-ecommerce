package com.ncc.mapstruct.mapper;

import com.ncc.mapstruct.dto.CategoryDto;
import com.ncc.mapstruct.dto.color.ColorDto;
import com.ncc.mapstruct.dto.color.ProductColorDto;
import com.ncc.mapstruct.dto.color.ProductColorWithoutSize;
import com.ncc.mapstruct.dto.discount.DiscountDto;
import com.ncc.mapstruct.dto.discount.ProductDiscountDto;
import com.ncc.mapstruct.dto.feedback.FeedbackDto;
import com.ncc.mapstruct.dto.inventory.InventoryDto;
import com.ncc.mapstruct.dto.inventory.InventorySaveDto;
import com.ncc.mapstruct.dto.inventory.InventoryWithSizeDto;
import com.ncc.mapstruct.dto.order.OrderDetailDto;
import com.ncc.mapstruct.dto.order.OrderItemDto;
import com.ncc.mapstruct.dto.product.ProductDetailDto;
import com.ncc.mapstruct.dto.product.ProductDto;
import com.ncc.mapstruct.dto.product.ProductViewDto;
import com.ncc.mapstruct.dto.size.SizeDto;
import com.ncc.mapstruct.dto.user.UserProfileDto;
import com.ncc.mapstruct.dto.user.UserSignUpDto;
import com.ncc.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface MapstructMapper {
    @Mapping(source = "birthday",target = "birthday",dateFormat = "dd-MM-yyyy")
    UserProfileDto toUserProfileDto(User user);

    @Mapping(source = "birthday",target = "birthday",dateFormat = "dd-MM-yyyy")
    User toEntity(UserProfileDto userProfileDto);

    UserSignUpDto toDto(User user);
    User toEntity(UserSignUpDto userSignUpDto);

    ColorDto toDto(Color color);
    Color toEntity(ColorDto colorDto);

    SizeDto toDto(Size size);
    Size toEntity(SizeDto sizeDto);

    DiscountDto toDto(Discount discount);
    Discount toEntity(DiscountDto discountDto);

    ProductDiscountDto toDto(ProductDiscount productDiscount);
    ProductDiscount toEntity(ProductDiscountDto productDiscountDto);

    @Mappings({
            @Mapping(target = "urlImages",expression = "java(productColor.toStringArray())"),
            @Mapping(target = "colorDto",source = "color")
    })
    ProductColorDto toDto(ProductColor productColor);

    @Mappings({
            @Mapping(target = "urlImage",expression = "java(productColorDto.toStringUrlImage())"),
            @Mapping(target = "color",source = "colorDto")
    })
    ProductColor toEntity(ProductColorDto productColorDto);

    @Mappings({
            @Mapping(target = "urlImage",expression = "java(productColorDto.toStringUrlImage())"),
            @Mapping(target = "color",source = "colorDto")
    })
    ProductColor toEntity(ProductColorWithoutSize productColorDto);

    @Mappings({
            @Mapping(target = "urlImages",expression = "java(productColor.toStringArray())"),
            @Mapping(target = "colorDto",source = "color")
    })
    ProductColorWithoutSize toProductColorWithoutSize(ProductColor productColor);

    @Mappings({
            @Mapping(target = "colorDto",source = "color"),
            @Mapping(target = "sizeDto",source = "size"),
    })
    InventoryDto toDto(Inventory inventory);

    @Mappings({
            @Mapping(target = "color",source = "colorDto"),
            @Mapping(target = "size",source = "sizeDto"),
    })
    Inventory toEntity(InventoryDto inventoryDto);

    @Mappings({
            @Mapping(target = "color",source = "colorDto"),
            @Mapping(target = "size",source = "sizeDto"),
            @Mapping(target = "product.id",source = "prodId"),
    })
    Inventory toEntity(InventorySaveDto inventorySaveDtoDto);

    @Mappings({
            @Mapping(target = "sizeDto",source = "size"),
    })
    InventoryWithSizeDto toInventoryWithSizeDto(Inventory inventory);

    @Mappings({
            @Mapping(target = "size",source = "sizeDto"),
    })
    Inventory toEntity(InventoryWithSizeDto inventoryWithSizeDto);

    @Mappings({
            @Mapping(target = "categoryDto",source = "category"),
            @Mapping(target = "productColorDtos",source = "productColors"),
    })
    ProductViewDto toDto(Product product);

    @Mappings({
            @Mapping(target = "category",source = "categoryDto"),
            @Mapping(target = "productColors",source = "productColorDtos")
    })
    Product toEntity(ProductViewDto productViewDto);

    @Mappings({
            @Mapping(target = "categoryDto",source = "category"),
            @Mapping(target = "productColorDtos",source = "productColors"),
    })
    ProductDetailDto toDetailDto(Product product);

    @Mappings({
            @Mapping(target = "category",source = "categoryDto"),
            @Mapping(target = "productColors",source = "productColorDtos"),
    })
    Product toEntity(ProductDetailDto productDetailDto);

    @Mappings({
            @Mapping(target = "prodId",source = "id"),
            @Mapping(target = "colorDto.id",source = "colorId"),
            @Mapping(target = "sizeDto.id",source = "sizeId"),
            @Mapping(target = "quantity",source = "quantity"),
    })
    InventorySaveDto toInventoryDto(ProductDto productDto);

    @Mapping(target = "cateId",source = "category.id")
    ProductDto toProductDto(Product product);

    @Mapping(target = "category.id",source = "cateId")
    Product toEntity(ProductDto productDto);


    CategoryDto toDto(Category category);
    Category toEntity(CategoryDto categoryDto);

    @Mappings({
            @Mapping(source = "user.email",target = "email"),
            @Mapping(source = "user.avatar",target = "avatar"),
            @Mapping(source = "user.username",target = "username"),
            @Mapping(source = "product.id",target = "prodId")
    })
    FeedbackDto toDto(Feedback feedback);

    @Mappings({
            @Mapping(target = "user.email",source = "email"),
            @Mapping(target = "user.avatar",source = "avatar"),
            @Mapping(target = "user.username",source = "username"),
            @Mapping(target = "product.id",source = "prodId"),
    })
    Feedback toEntity(FeedbackDto feedbackDto);

    @Mappings({
            @Mapping(source = "orderDetail.id",target = "orderId"),
    })
    OrderItemDto toDto(OrderItem orderItem);
    OrderItem toEntity(OrderItemDto orderItemDto);

    @Mappings({
            @Mapping(source = "user.id",target = "userId"),
            @Mapping(source = "userPayment.id",target = "paymentId"),
            @Mapping(source = "orderItems",target = "orderItemDtos"),
            @Mapping(source = "orderDetail.createdAt",target = "createdAt",dateFormat = "HH:mm:ss dd-MM-yyyy")
    })
    OrderDetailDto toDto(OrderDetail orderDetail);

    @Mappings({
            @Mapping(target = "user.id",source = "userId"),
            @Mapping(target = "userPayment.id",source = "paymentId"),
            @Mapping(target = "orderItems",source = "orderItemDtos"),
            @Mapping(target = "createdAt",source = "createdAt",dateFormat = "HH:mm:ss dd-MM-yyyy")
    })
    OrderDetail toEntity(OrderDetailDto orderDetailDto);
}


