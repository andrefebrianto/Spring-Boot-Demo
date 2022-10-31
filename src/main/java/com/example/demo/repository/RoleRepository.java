package com.example.demo.repository;

import com.example.demo.model.entity.Role;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends _BaseEntityWithAuditRepository<Role, Long> {

    Page<Role> findAll(Specification<Role> filter, Pageable pageable);

    Optional<Role> findByName(String name);

    Boolean existsByName(String name);
}
