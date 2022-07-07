package com.ncc.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "color")
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "color_name")
    private String colorName;

    @Column(name = "color_code")
    private String colorCode;

    private boolean status;

    @OneToMany(mappedBy = "color")
    private List<Inventory> inventories;

    @OneToMany(mappedBy = "color")
    private List<ProductColor> productColors;
}
