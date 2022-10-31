package com.example.demo.model.entity;

import java.time.LocalDateTime;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Setter
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class _BaseEntityWithAudit extends _BaseEntity {

    @CreatedBy protected Long createdBy;

    @CreatedDate protected LocalDateTime createdAt;

    @LastModifiedBy protected Long updatedBy;

    @LastModifiedDate protected LocalDateTime updatedAt;

    protected Boolean isDeleted = Boolean.FALSE;
}
