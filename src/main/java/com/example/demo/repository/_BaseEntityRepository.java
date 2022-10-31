package com.example.demo.repository;

import com.example.demo.model.entity._BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface _BaseEntityRepository<T extends _BaseEntity, ID> extends JpaRepository<T, ID> {}
