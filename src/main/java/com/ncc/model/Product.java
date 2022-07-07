package com.ncc.model;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "product")
public class Product {
    @Id
    private String id;

    private String name;

    @Transient
    private MultipartFile[] multipartFile;

    private String brand;

    @Column(name = "total_quantity")
    private int totalQuantity;

    @Column(name = "price_in")
    private double priceIn;

    @Column(name = "price_out")
    private double priceOut;

    private String description;

    @Column(name = "count_view")
    private int countView;

    @Column(name = "count_buy")
    private int countBuy;

    private boolean status;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "modified_at")
    private Date modifiedAt;

    @Column(name = "deleted_at")
    private Date deletedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cate_id", referencedColumnName = "id")
    private Category category;

    @OneToMany(mappedBy = "product",cascade = CascadeType.REMOVE)
    private List<ProductDiscount> productDiscounts;

    @OneToMany(mappedBy = "product")
    private List<Feedback> feedbacks;

    @OneToMany(mappedBy = "product",cascade = CascadeType.REMOVE)
    private List<Inventory> inventories;

    @OneToMany(mappedBy = "product",cascade = CascadeType.REMOVE)
    private List<ProductColor> productColors;
}
