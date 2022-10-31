package com.example.demo.model.entity;

import java.time.LocalDateTime;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

@Setter
@Getter
@MappedSuperclass
public class _BaseEntityWithAudit extends _BaseEntity {

    @CreatedBy protected Long createdBy;

    @CreatedDate protected LocalDateTime createdAt;
}
