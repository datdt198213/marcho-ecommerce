package com.ncc.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "inventory")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int quantity;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "modified_at")
    private Date modifiedAt;

    @Column(name = "deleted_at")
    private Date deletedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "color_id", referencedColumnName = "id")
    private Color color;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prod_id", referencedColumnName = "id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "size_id", referencedColumnName = "id")
    private Size size;

    @OneToMany(mappedBy = "inventory")
    private List<OrderItem> orderItems;
}
