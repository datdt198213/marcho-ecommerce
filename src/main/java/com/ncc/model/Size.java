package com.ncc.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "size")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String description;

    private boolean status;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "modified_at")
    private Date modifiedAt;

    @OneToMany(mappedBy = "size")
    private List<Inventory> inventories;
}
