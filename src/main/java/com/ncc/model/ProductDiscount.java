package com.ncc.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "product_discount")
public class ProductDiscount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String tittle;

    private String coupon;

    @Column(name = "start_at")
    private Date startAt;

    @Column(name = "expire_at")
    private Date expireAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discount_id", referencedColumnName = "id")
    private Discount discount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prod_id", referencedColumnName = "id")
    private Product product;
}
