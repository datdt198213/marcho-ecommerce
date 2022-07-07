package com.ncc.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "discount")
public class Discount {
    @Id
    @Column(nullable = false)
    private int id;

    private String name;

    @Column(name = "discount_percent")
    private int discountPercent;

    private boolean status;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "modified_at")
    private Date modifiedAt;

    @Column(name = "deleted_at")
    private Date deletedAt;

    @OneToMany(mappedBy = "discount")
    private List<ProductDiscount> productDiscounts;
}
