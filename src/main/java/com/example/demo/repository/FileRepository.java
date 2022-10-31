package com.example.demo.repository;

import com.example.demo.model.entity.File;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends _BaseEntityWithAuditRepository<File, UUID> {

    Page<File> findAll(Specification<File> filter, Pageable pageable);
}
