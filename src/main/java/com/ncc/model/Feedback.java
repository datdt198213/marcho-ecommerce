package com.ncc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name = "feedback")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String message;

    @Column(name = "created_at")
    private Date createdAt;

    private int vote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prod_id")
    @JsonIgnore
    private Product product;
}
