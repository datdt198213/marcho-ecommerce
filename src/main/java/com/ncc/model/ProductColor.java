package com.ncc.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_color")
public class ProductColor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "url_image")
    private String urlImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prod_id", referencedColumnName = "id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "color_id", referencedColumnName = "id")
    private Color color;

    public String[] toStringArray() {
        return urlImage.split(",");
    }
}
