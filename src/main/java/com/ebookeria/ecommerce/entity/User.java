package com.ebookeria.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name="user")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@ToString(exclude = {"ebooks", "roles"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="registration_date")
    private LocalDate registrationDate;

    @Column(name="is_enabled")
    private boolean enabled;;

    @OneToMany(mappedBy = "user")
    private List<Ebook> ebooks;

    @ManyToMany
    @JoinTable(
            name="users_roles",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id")
    )
    private List<Role> roles;


    public void addRole(Role role) {
        if (roles == null) {
            roles = new ArrayList<>();
        }
        roles.add(role);
    }
}
