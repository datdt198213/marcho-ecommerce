package com.ncc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "category")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class Category {
    @Id
    private String id;

    @Column(name = "parent_id")
    private String parentId;

    @Column(name = "display_order")
    private int displayOrder; // thứ tự hiển thị của danh mục

    private String name;

    private String description;

    private boolean status;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "modified_at")
    private Date modifiedAt;

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<Product> products;
}
