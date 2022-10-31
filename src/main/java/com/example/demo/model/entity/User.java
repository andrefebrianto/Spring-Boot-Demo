package com.example.demo.model.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "demo_user")
public class User extends _BaseEntityWithAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "demo_user_id_seq")
    @SequenceGenerator(
            name = "demo_user_id_seq",
            sequenceName = "demo_user_id_seq",
            allocationSize = 1)
    private Long id;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;
}
