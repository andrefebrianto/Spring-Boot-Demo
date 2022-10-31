package com.example.demo.repository;

import com.example.demo.model.entity.User;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends _BaseEntityWithAuditRepository<User, Long> {

    Page<User> findAll(Specification<User> filter, Pageable pageable);

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);
}
