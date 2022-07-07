package com.ncc.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "User")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer","handler"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String fullname;

    private String username;

    private String password;

    private String email;

    private String phone;

    private String address;

    private Date birthday;
    
    @Transient
    private int age;

    private String avatar;
    
    @Transient
    private MultipartFile multipartFile;

    private boolean enabled;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "modified_at")
    private Date modifiedAt;

    @ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id")
    )
    private Set<Role>roles;

    @OneToMany(mappedBy = "user")
    private List<UserPayment> userPayments;

    @OneToMany(mappedBy = "user")
    private List<Feedback> feedbacks;

    @OneToMany(mappedBy = "user")
    private List<OrderDetail>orderDetails;
    
    public List<SimpleGrantedAuthority> getAuthorities(){
        List<SimpleGrantedAuthority>authorities=new ArrayList<>();

        for (Role role:roles){
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }
}
