package com.example.demo.model.entity;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "demo_role")
public class Role extends _BaseEntityWithAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "demo_role_id_seq")
    @SequenceGenerator(
            name = "demo_role_id_seq",
            sequenceName = "demo_role_id_seq",
            allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String name;
}
