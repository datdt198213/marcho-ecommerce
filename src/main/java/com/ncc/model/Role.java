package com.ncc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "role")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer","handler"})
public class Role {
    @Id
    private String id;

    private String name;

    private boolean status;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private Set<User>users;
}
